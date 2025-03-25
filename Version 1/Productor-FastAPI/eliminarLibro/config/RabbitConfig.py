import os
import aio_pika

# Configuración de RabbitMQ
RABBITMQ_URL = os.environ.get("RABBITMQ_URL")
EXCHANGE_NAME = "libroExchange"
COMMON_ROUTING_KEY = "deletebookcommon.key"
SOCORRO_ROUTING_KEY = "deletebooksocorro.key"
MALAGA_ROUTING_KEY = "deletebookmalaga.key"

async def setup_rabbitmq():
        
    # Configurar Conexion con rabbit 
    connection = await aio_pika.connect_robust(RABBITMQ_URL)
    channel = await connection.channel()
    exchange = await channel.declare_exchange(EXCHANGE_NAME, aio_pika.ExchangeType.DIRECT, durable=True)
    return connection,exchange

async def send_mensage(libro_delete,key):
    mensaje = libro_delete.json()
    connection, exchange = await setup_rabbitmq()
    await exchange.publish(
        aio_pika.Message(body=mensaje.encode("utf-8")),
        routing_key=key
    )
    await connection.close()
    if( key == MALAGA_ROUTING_KEY):
        sede="Malaga"
    elif(key == SOCORRO_ROUTING_KEY):
        sede="Socorro"
    else:
        sede="Todas"

    return {"Eliminar Libro": libro_delete.titulo , "Sede": sede}

