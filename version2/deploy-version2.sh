#!/bin/bash

# Variables
REPO_URL="https://github.com/Gilgamesh06/RabbitMQ.git" 
WORK_DIR="~/agents"
DOCKER_CMD=$(command -v docker)
JAVA_CMD=$(command -v java)
MAVEN_CMD=$(command -v mvn)

# Conectarse a la máquina local (10.6.101.95) y ejecutar todo en un solo bloque
echo "Ejecutando Script en la máquina local (10.6.101.95)..."

ssh student@10.6.101.95 "
  # Instalar Docker si no está instalado
  if ! command -v docker &> /dev/null; then
    echo 'Docker no está instalado. Instalando Docker...';
    sudo apt-get update && sudo apt-get install -y docker.io;
    sudo systemctl start docker && sudo systemctl enable docker;
  fi;

  # Clonar el repositorio si no está presente
  if [ -d '$WORK_DIR' ]; then
      echo 'Repositorio ya clonado en $WORK_DIR. Haciendo git pull...';
      cd $WORK_DIR && git pull;  # Hace git pull para obtener los últimos cambios
  else
      git clone $REPO_URL $WORK_DIR;
  fi;

  # Ejecutar Docker Compose en la máquina local (version 2)
  docker stop \$(docker ps -q);
  cd $WORK_DIR/version2 && docker-compose -f docker-compose-cola.yml up -d;
"

echo "Despliegue completado para Script en la máquina local (10.6.101.95)."


ssh student@10.6.101.96 "
  # Instalar Docker si no está instalado
  if ! command -v docker &> /dev/null; then
    echo 'Docker no está instalado. Instalando Docker...';
    sudo apt-get update && sudo apt-get install -y docker.io;
    sudo systemctl start docker && sudo systemctl enable docker;
  fi;

  # Instalar Java si no está instalado
  echo 'Java no está instalado. Instalando OpenJDK 17...';
  sudo apt-get install -y openjdk-17-jdk;
  java -version;

  # Clonar el repositorio si no está presente
  if [ -d '$WORK_DIR' ]; then
      echo 'Repositorio ya clonado en $WORK_DIR. Haciendo git pull...';
      cd $WORK_DIR && git pull;  # Hace git pull para obtener los últimos cambios
  else
      git clone $REPO_URL $WORK_DIR;
  fi;

  # Dar permisos de ejecución al archivo 'wait-for-it.sh'
  chmod 777 $WORK_DIR/agents/Consumidor-FastAPI/wait-for-it.sh;

  # Ejecutar Maven Wrapper (mvnw) si es necesario
  cd $WORK_DIR/agents/Consumidor-SpringBoot/bibliotecaMalaga && ./mvnw clean install;
  cd $WORK_DIR/agents/Productor-SpringBoot/agregarLibro && ./mvnw clean install;

  # Ejecutar Docker Compose en la máquina local (version 2)
  docker stop \$(docker ps -q);
  cd $WORK_DIR/version2 && docker-compose -f docker-compose-agents.yml up -d;
"

echo "Despliegue completado para Script en la máquina local (10.6.101.96)."
