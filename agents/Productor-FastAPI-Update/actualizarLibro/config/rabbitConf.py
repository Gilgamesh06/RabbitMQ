import os
import aio_pika

# Configuración de RabbitMQ
RABBITMQ_URL = os.environ.get("RABBITMQ_URL")
EXCHANGE_NAME = "libroExchange"
COMMON_ROUTING_KEY = "updatebookcommon.key"
SOCORRO_ROUTING_KEY = "updatebooksocorro.key"
MALAGA_ROUTING_KEY = "updatebookmalaga.key"
BARBOSA_ROUTING_KEY = "updatebookbarbosa.key"

async def setup_rabbitmq():
        
    # Configurar Conexion con rabbit 
    connection = await aio_pika.connect_robust(RABBITMQ_URL)
    channel = await connection.channel()
    exchange = await channel.declare_exchange(EXCHANGE_NAME, aio_pika.ExchangeType.DIRECT, durable=True)
    return connection,exchange

async def send_mensage(libro_update,key):
    mensaje = libro_update.json()
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
    elif(key == BARBOSA_ROUTING_KEY):
        sede="Barbosa"
    else:
        sede="Todas"

    return {"Libro Enviado a Actualizar": libro_update.titulo , "Sede": sede}

