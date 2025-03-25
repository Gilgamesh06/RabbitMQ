# Importar librerias desde SQLAlchemy para la conexion de la base de datos y la creacion del modelo a odejotes de esta
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm import declarative_base
import os
import time
from sqlalchemy.exc import OperationalError

# Obtener la URL de la base de datos desde el entorno
DATABASE_URL = os.environ.get("DATABASE_URL")

# Crea el motor de la base de datos el cual gestionar las conexiones y consultas a la base de datos

engine = create_engine(DATABASE_URL)

# Es creador de sesiones y desactiva los commit automaticos y consulta del motor de base de datos que creamos engine

SessionLocal = sessionmaker(autocommit=False,autoflush=False,bind=engine)

# Crea un objeto de la base de datos la cual se usa como parametro en los modelos 

# Crear las tablas
def init_db(retries=30, delay=15):
    while retries:
        try:
            Base.metadata.create_all(bind=engine)
            print("Tablas creadas exitosamente.")
            return
        except OperationalError as e:
            print(f"Error al conectar con la base de datos: {e}. Reintentando en {delay} segundos...")
            time.sleep(delay)
            retries -= 1
    raise Exception("No se pudo conectar a la base de datos después de varios intentos.")

Base = declarative_base()

# En esta funcion nos aseguramos de crear una sesion y de cuando hagan llamado a esta puedan usar la base de datos
# Al  fianlizar ciere la base de datos evitando sobrecarga 

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()