from fastapi import Depends, APIRouter, HTTPException
from config.db import get_db
from schemas.dto import LibroAddDTO, LibroResponseDTO
from models.Libro import Libro
from sqlalchemy.orm import Session
from service.LibroService import search_book
router = APIRouter()

# Ruta para listar los libros
@router.get("/listar-libros", response_model=list[LibroResponseDTO])
async def get_all_books(db: Session = Depends(get_db)):
    libros = db.query(Libro).all()  # Obtén todos los libros de la base de datos
    return [LibroResponseDTO(**libro.__dict__) for libro in libros]  # Mapea a DTO

# Ruta para buscar un libro por título
@router.get("/buscar-libro/{titulo}", response_model=LibroResponseDTO)
async def get_book(titulo: str, db: Session = Depends(get_db)):
    libro = search_book(titulo, db) # Busca el libro
    if libro is None:
        raise HTTPException(status_code=404, detail="Libro no encontrado")
    return LibroResponseDTO(**libro.__dict__)  # Mapea a DTO
