
# Taller de Software 3: `Publish-Subscribe`

## Pasos para el despliegue

Para desplegar los contenedores correspondientes a cada versión del proyecto, sigue estos pasos:

### Desplegar contenedores - **Versión 1**

```bash
curl -O https://raw.githubusercontent.com/Gilgamesh06/RabbitMQ/main/version1/deploy-version1.sh
chmod +x deploy-version1.sh
./deploy-version1.sh
```

### Desplegar contenedores - **Versión 2**

```bash
curl -O https://raw.githubusercontent.com/Gilgamesh06/RabbitMQ/main/version2/deploy-version2.sh
chmod +x deploy-version2.sh
./deploy-version2.sh
```

### Desplegar contenedores - **Versión 3**

```bash
curl -O https://raw.githubusercontent.com/Gilgamesh06/RabbitMQ/main/version3/deploy-version3.sh
chmod +x deploy-version3.sh
./deploy-version3.sh
```

**Nota**: Durante la ejecución de los scripts, se te pedirá la **contraseña SSH** para proceder con el despliegue. Asegúrate de tener acceso a las máquinas de destino dentro de la red de la universidad.

## Requisitos previos

- **Acceso a la red de la universidad**: Los scripts deben ejecutarse dentro de la red de la universidad debido a las configuraciones de red y seguridad de los contenedores. Asegúrate de estar conectado a la red de la universidad antes de ejecutar los scripts.
  
- **Permisos SSH**: Se requerirá ingresar la contraseña SSH de la máquina remota en cada paso de despliegue. Esto es necesario para garantizar la seguridad y el control sobre las máquinas donde se están ejecutando los contenedores.

## Descripción del Proyecto
* Este taller tocaba realizar la creación de tres consumidores y tres productores conectados por medio de RabbitMQ 
* Para ello se desarrollo el siguiente esquema:

    ![Esquema Proyecto](/images/RabbitMQ.png)

    ## Esquema del Proyecto

    * El proyecto simula 3 bibliotecas simples representadas por 3 sedes de la universidad que seran los consumidores y 3 productores que realizan las actividad de eliminar, agregar y actualizar los libros de la bibliotecas estos diferentes productores pueden enviar el mensaje a un determinado consumidor o enviarlo a todos por medio del biding common

    * En este esquema se desarrollo con 3 tecnólogias 
        * **FastAPI**
        * **NodeJS**
        * **SpringBoot**
    
    ## Versiones

    [![Version 1](/images/Version1.png)](/version1/)

    [![Version 2](/images/Version2.png)](/version2/)

    [![Version 3](/images/Version3.png)](/version3/)
