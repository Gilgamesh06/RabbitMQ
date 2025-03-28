from config.db import Base
from sqlalchemy import Column, Integer, String, Float, Date

class Libro(Base):
    __tablename__ = 'libro'

    id = Column(Integer, primary_key=True, autoincrement=True)
    titulo = Column(String(200), nullable=False, unique=True)
    autor = Column(String(150), nullable=False)
    descripcion = Column(String(255))
    fecha_publicacion = Column(Date, nullable=False)