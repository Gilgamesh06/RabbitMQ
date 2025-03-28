from fastapi import FastAPI
from routes.eliminarLibro import router as libro_router

app = FastAPI()

app.include_router(libro_router, prefix="/api/v1/productor", tags=["Delete Book"])


