from fastapi import FastAPI
from routes.libro import router as libro_router

app = FastAPI()

app.include_router(libro_router, prefix="/api/delete", tags=["Libros"])


