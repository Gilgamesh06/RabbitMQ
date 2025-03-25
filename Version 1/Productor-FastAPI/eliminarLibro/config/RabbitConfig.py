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

async def send_mensage(mensaje,consumidor):
    connection, exchange = setup_rabbitmq()
    await exchange.publish(
        aio_pika.Message(body=mensaje.encode("utf-8")),
        routing_key=consumidor
    )
    await connection.close()
    return {"message": "Mensaje enviado", "routing_key": consumidor}