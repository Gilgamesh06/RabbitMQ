# a una imagen base con Python, por ejemplo, 3.11.2: 
FROM python:3.11.2
# Establece un directorio temporal para copiar las dependencias 
WORKDIR /app
# Copia el archivo de requerimientos e instala las dependencias
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
# Establece el directorio de trabajo al directorio donde está main.py
WORKDIR /app/actualizarLibro
# (Opcional) Asegura que el directorio raíz esté en PYTHONPATH
ENV PYTHONPATH=/app
# Expone el puerto en el que se ejecutará la app
EXPOSE 8000
# Espera a que RabbitMQ (hostname 'rabbitmq') esté disponible antes de iniciar uvicorn
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]

