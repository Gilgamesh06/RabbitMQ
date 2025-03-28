# a una imagen base con Python, por ejemplo, 3.11.2:
FROM python:3.11.2
# Establece un directorio temporal para copiar las dependencias
WORKDIR /app
# Copia el archivo de requerimientos e instala las dependencias
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
# Copia el script wait-for-it.sh y dale permisos de ejecución
# Copia el script y cambia los permisos
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
# Copia todo el proyecto al contenedor
COPY . .
# Establece el directorio de trabajo al directorio donde está main.py
WORKDIR /app/bibliotecaSocorro
# (Opcional) Asegura que el directorio raíz esté en PYTHONPATH
ENV PYTHONPATH=/app
# Expone el puerto en el que se ejecutará la app
EXPOSE 8000
# Espera a que RabbitMQ (hostname 'rabbitmq') esté disponible antes de iniciar uvicorn
CMD ["/app/wait-for-it.sh", "rabbitmq:5672", "-t", "60", "--", "uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]

