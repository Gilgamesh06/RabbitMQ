# Taller de Software 3: `Publish-Suscribe`

# Pasos despliegue 

Desplegar contenedores version 1 
```bash
curl -O https://raw.githubusercontent.com/Gilgamesh06/RabbitMQ/main/version1/deploy-version1.sh
chmod +x deploy-version1.sh
./deploy-version1.sh
```

Desplegar contenedores version 2
```bash
curl -O https://raw.githubusercontent.com/Gilgamesh06/RabbitMQ/main/version2/deploy-version2.sh
chmod +x deploy-version2.sh
./deploy-version2.sh
```

Desplegar contenedores version 3
```bash
curl -O https://raw.githubusercontent.com/Gilgamesh06/RabbitMQ/main/version2/deploy-version3.sh
chmod +x deploy-version3.sh
./deploy-version3.sh
```


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

    [![Version 3](/images/Version3.png)]()
