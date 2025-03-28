#!/bin/bash

# Variables
REPO_URL="https://github.com/Gilgamesh06/RabbitMQ.git" 
WORK_DIR="~/agents/"
DOCKER_CMD=$(command -v docker)
SSH_CMD=$(command -v ssh)

# Función para instalar Docker en la máquina remota
install_docker() {
  MACHINE_IP=$1
  echo "Conectando a $MACHINE_IP para instalar Docker..."

  ssh student@$MACHINE_IP "if ! command -v docker &> /dev/null; then
      echo 'Docker no está instalado. Instalando Docker...';
      sudo apt-get update && sudo apt-get install -y docker.io;
      sudo systemctl start docker && sudo systemctl enable docker;
  else
      echo 'Docker ya está instalado.';
  fi"
}

# Función para clonar el repositorio remotamente
clone_repo() {
  MACHINE_IP=$1
  echo "Conectando a $MACHINE_IP para clonar el repositorio..."

  ssh student@$MACHINE_IP "if [ ! -d '$WORK_DIR' ]; then
      git clone $REPO_URL $WORK_DIR;
  else
      echo 'Repositorio ya clonado en $WORK_DIR';
  fi"
}

# Función para ejecutar Docker Compose remotamente
run_docker_compose() {
  MACHINE_IP=$1
  VERSION=$2
  echo "Conectando a $MACHINE_IP para ejecutar docker-compose (versión $VERSION)..."
  ssh student@$MACHINE_IP "cd $WORK_DIR/version$VERSION && docker-compose up -d"
}

# Ejecutar en la máquina local (10.6.101.95)
echo "Ejecutando Script 1 (todo en la máquina local)..."
install_docker "10.6.101.95"
clone_repo "10.6.101.95"
run_docker_compose "10.6.101.95" 1

echo "Despliegue completado para Script 1."

