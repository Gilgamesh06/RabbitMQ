from fastapi import FastAPI
from config.RabbitConfig import lifespan
from routes.LibroRouter import router as libro_router
from config.db import init_db

app = FastAPI(lifespan=lifespan)

app.include_router(libro_router , prefix="/api/v1/biblioteca-socorro", tags=["Libros"])

@app.get("/")
def read_root():
    return {"message": "API FastAPI corriendo y escuchando mensajes de RabbitMQ"}
