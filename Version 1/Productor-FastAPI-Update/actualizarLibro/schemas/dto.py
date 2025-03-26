from pydantic import BaseModel, Field
from datetime import date
from typing import Optional

class LibroUpdateDTO(BaseModel):
    titulo: str = Field(description="TItulo del libro actualizar")
    autor: Optional[str] = None
    descripcion: Optional[str] = None
    fecha_publicacion: Optional[date] = None

    class Config:
        all_population_by_field_name = True
        from_attributes = True

    