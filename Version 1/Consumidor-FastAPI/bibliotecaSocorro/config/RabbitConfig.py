import asyncio
import json
from contextlib import asynccontextmanager
from fastapi import FastAPI
import aio_pika
from config.db import init_db

# Importa tu sesión de base de datos y DTO
from config.db import SessionLocal  # Asegúrate de que SessionLocal devuelva una instancia de Session
from schemas.dto import LibroAddDTO
from service.LibroService import add_book  # El servicio asíncrono que agrega el libro
import os

# Configuración de RabbitMQ
RABBITMQ_URL = os.environ.get("RABBITMQ_URL")
EXCHANGE_NAME = "libroExchange"
QUEUE_NAME = "libroQueueFast"
COMMON_ROUTING_KEY = "common.key"
FAST_API_ROUTING_KEY = "fast.key"  

async def setup_rabbitmq():
    connection = await aio_pika.connect_robust(RABBITMQ_URL, timeout=180)
    channel = await connection.channel()
    # Declarar un exchange de tipo DIRECT
    exchange = await channel.declare_exchange(EXCHANGE_NAME, aio_pika.ExchangeType.DIRECT, durable=True)
    # Declarar la cola que usará este consumidor
    queue = await channel.declare_queue(QUEUE_NAME, durable=False)
    # Vincular la cola al exchange con dos routing keys: común e individual
    await queue.bind(exchange, routing_key=COMMON_ROUTING_KEY)
    await queue.bind(exchange, routing_key=FAST_API_ROUTING_KEY)
    return connection, channel, exchange, queue

async def on_message(message: aio_pika.IncomingMessage):
    async with message.process():
        try:
            # Decodificar el mensaje (suponiendo formato JSON)
            data = json.loads(message.body.decode("utf-8"))
            print("Mensaje recibido:", data)
            # Convertir el JSON en un objeto LibroAddDTO
            libro_dto = LibroAddDTO(**data)
            # Crear una sesión de base de datos (sin dependencia de FastAPI)
            db = SessionLocal()
            try:
                # Llamar al servicio asíncrono que agrega el libro
                result = await add_book(libro_dto, db)
                print("Libro agregado:", result)
            finally:
                db.close()
        except Exception as e:
            print("Error procesando mensaje:", e)

@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup: crear las tablas en la base de datos si no existen
    init_db()  # Llama a la función para crear las tablas

    # Configurar RabbitMQ al inicio de la aplicación
    connection, channel, exchange, queue = await setup_rabbitmq()
    consume_tag = await queue.consume(on_message)
    print("Consumidor de RabbitMQ iniciado.")
    
    try:
        yield  # Permite que la aplicación funcione
    finally:
        # Cerrar recursos al finalizar la aplicación
        await channel.close()
        await connection.close()
