from pydantic import BaseModel, Field
from datetime import date
from typing import Optional

class LibroResponseDTO(BaseModel):
    id: int  # Agregar el campo id
    titulo: str
    autor: str
    descripcion: Optional[str] = None  # Este campo es opcional
    fecha_publicacion: Optional[date] = None  # Este campo es opcional

    class Config:
        orm_mode = True  # Habilita el modo ORM para permitir la conversión desde objetos ORM

class LibroAddDTO(BaseModel):
    titulo: str
    autor: str
    descripcion: Optional[str] = None  # Este campo es opcional
    fecha_publicacion: date = Field(alias="fechaPublicacion")

class LibroUpdateDTO(BaseModel):
    titulo: str = Field(description="TItulo del libro actualizar")
    autor: Optional[str] = None
    descripcion: Optional[str] = None
    fecha_publicacion: Optional[date] = None

class LibroDeleteDTO(BaseModel):
    titulo: str = Field(description="Titulo del libro a eliminar")

    class Config:
        all_population_by_field_name = True
        from_attributes = True

    
