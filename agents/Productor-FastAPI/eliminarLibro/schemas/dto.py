from pydantic import BaseModel, Field

class LibroDeleteDTO(BaseModel):
    titulo: str = Field(description="TItulo del libro a eliminar")

    class Config:
        all_population_by_field_name = True
        from_attributes = True

    
        