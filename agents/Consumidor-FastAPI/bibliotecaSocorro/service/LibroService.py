from fastapi import Depends, HTTPException
from models.Libro import Libro
from config.db import get_db
from sqlalchemy.orm import Session

def search_book(titulo, db):
     return db.query(Libro).filter(Libro.titulo == titulo).first() 

async def add_book(libro_dto, db: Session = Depends(get_db)):
    
    libro = search_book(libro_dto.titulo,db)
    if libro is None:
        db_libro = Libro(
            titulo=libro_dto.titulo,
            autor=libro_dto.autor,
            descripcion=libro_dto.descripcion,
            fecha_publicacion=libro_dto.fecha_publicacion
        )
        db.add(db_libro)
        db.commit()
        db.refresh(db_libro)
        return db_libro

    else:
        raise HTTPException(status_code=404, detail="Libro ya esta registrado")

async def update_book(libro_dto, db:Session = Depends(get_db)):
    libro = search_book(libro_dto.titulo,db)
    if libro is None:
        raise HTTPException(status_code=404, detail="Libro No encontrado")
    else:
        if libro_dto.autor is not None and libro_dto.autor.strip() != "":
            libro.autor= libro_dto.autor
        if libro_dto.descripcion is not None and libro_dto.descripcion.strip() != "":
            libro.descripcion=libro_dto.descripcion
        if libro_dto.fecha_publicacion is not None:
            libro.fecha_publicacion=libro_dto.fecha_publicacion

        db.commit()
        db.refresh(libro)
        return libro

async def delete_book(libro_dto, db:Session = Depends(get_db)):
    libro = search_book(libro_dto.titulo,db)
    if libro is None:
        raise HTTPException(status_code=404, detail="El Libro no esta registrado")
    else:
        db.delete(libro)
        db.commit()
        return libro
    
    