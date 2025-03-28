import os
import json
import aio_pika
from contextlib import asynccontextmanager
from fastapi import FastAPI
from config.db import init_db, SessionLocal
from schemas.dto import LibroAddDTO, LibroDeleteDTO, LibroUpdateDTO
from service.LibroService import add_book, delete_book, update_book

# Configuración de RabbitMQ a partir de la variable de entorno
RABBITMQ_URL = os.environ.get("RABBITMQ_URL")
EXCHANGE_NAME = "libroExchange"

# Definir nombres de colas y routing keys para la biblioteca de Socorro
ADD_QUEUE_NAME = "libroQueueSocorroAdd"
DELETE_QUEUE_NAME = "libroQueueSocorroDelete"
UPDATE_QUEUE_NAME = "libroQueueSocorroUpdate"

# Claves para agregar libro
ADD_ROUTING_KEY = "addbooksocorro.key"
ADD_COMMON_ROUTING_KEY = "addbookcommon.key"

# Claves para eliminar libro
DELETE_ROUTING_KEY = "deletebooksocorro.key" 
DELETE_COMMON_ROUTING_KEY = "deletebookcommon.key"


# Claves para update libro
UPDATE_ROUTING_KEY = "updatebooksocorro.key" 
UPDATE_COMMON_ROUTING_KEY = "updatebookcommon.key"


async def setup_rabbitmq():
    connection = await aio_pika.connect_robust(RABBITMQ_URL, timeout=180)
    channel = await connection.channel()
    # Declarar el exchange de tipo DIRECT, asegurando durable=True
    exchange = await channel.declare_exchange(EXCHANGE_NAME, aio_pika.ExchangeType.DIRECT, durable=True)
    # Declarar la cola para agregar libros
    add_queue = await channel.declare_queue(ADD_QUEUE_NAME, durable=False)
    # Declarar la cola para eliminar libros
    delete_queue = await channel.declare_queue(DELETE_QUEUE_NAME, durable=False)
    # Declarar la cola para actualizar libros
    update_queue = await channel.declare_queue(UPDATE_QUEUE_NAME, durable=False)
    # Vincular las colas al exchange con las routing keys correspondientes
    await add_queue.bind(exchange, routing_key=ADD_ROUTING_KEY)
    await delete_queue.bind(exchange, routing_key=DELETE_ROUTING_KEY)
    await update_queue.bind(exchange, routing_key=UPDATE_ROUTING_KEY)
    # Vicular las colas al exchange con las claves comunes
    await add_queue.bind(exchange, routing_key=ADD_COMMON_ROUTING_KEY)
    await delete_queue.bind(exchange, routing_key=DELETE_COMMON_ROUTING_KEY)
    await update_queue.bind(exchange, routing_key=UPDATE_COMMON_ROUTING_KEY)

    return connection, channel, exchange, add_queue, delete_queue , update_queue

async def on_message(message: aio_pika.IncomingMessage):
    async with message.process():
        try:
            data = json.loads(message.body.decode("utf-8"))
            print("Mensaje recibido:", data, "Routing key:", message.routing_key)
            db = SessionLocal()
            try:
                # Diferenciar la operación en función de la routing key
                if message.routing_key in [ADD_ROUTING_KEY, ADD_COMMON_ROUTING_KEY]:
                    libro_dto = LibroAddDTO(**data)
                    result = await add_book(libro_dto, db)
                    print("Libro agregado:", result)
                elif message.routing_key in [DELETE_ROUTING_KEY,DELETE_COMMON_ROUTING_KEY]:
                    libro_dto = LibroDeleteDTO(**data)
                    result = await delete_book(libro_dto, db)
                    print("Libro eliminado:", result)
                elif message.routing_key in [UPDATE_ROUTING_KEY,UPDATE_COMMON_ROUTING_KEY]:
                    libro_dto = LibroUpdateDTO(**data)
                    result = await update_book(libro_dto, db)
                    print("Libro Actualizado:", result)
                else:
                    print("Routing key desconocida:", message.routing_key)
            finally:
                db.close()
        except Exception as e:
            print("Error procesando mensaje:", e)

@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup: crear las tablas en la base de datos si no existen
    init_db()
    connection, channel, exchange, add_queue, delete_queue , update_queue = await setup_rabbitmq()
    # Inicia el consumo en ambas colas
    await add_queue.consume(on_message)
    await delete_queue.consume(on_message)
    await update_queue.consume(on_message)
    print("Consumidor de RabbitMQ iniciado en las tres colas.")
    try:
        yield
    finally:
        await channel.close()
        await connection.close()
