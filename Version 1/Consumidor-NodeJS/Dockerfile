# Usa la imagen oficial de Node.js
FROM node:18

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia los archivos de la aplicación (excluyendo los especificados en .dockerignore)
COPY bibliotecaBarbosa/package.json bibliotecaBarbosa/package-lock.json ./

# Instala las dependencias
RUN npm install

# Copia el resto del código de la aplicación
COPY bibliotecaBarbosa .

# Expone el puerto de la aplicación
EXPOSE 3000

# Comando para iniciar la aplicación
CMD ["node", "src/index.js"]
