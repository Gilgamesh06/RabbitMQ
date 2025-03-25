from pydantic import BaseModel, Field
from datetime import date
from typing import Optional

class LibroAddDTO(BaseModel):
    titulo: str
    autor: str
    descripcion: Optional[str] = None  # Este campo es opcional
    fecha_publicacion: date = Field(alias="fechaPublicacion")

class LibroResponseDTO(BaseModel):
    id: int  # Agregar el campo id
    titulo: str
    autor: str
    descripcion: Optional[str] = None  # Este campo es opcional
    fecha_publicacion: Optional[date] = None  # Este campo es opcional

    class Config:
        orm_mode = True  # Habilita el modo ORM para permitir la conversión desde objetos ORM
