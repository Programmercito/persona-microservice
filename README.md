# Proyecto de Microservicios - Devsu Challenge

Este proyecto consiste en dos microservicios desarrollados en Java con Spring Boot:

1.  **persona-microservice**: Gestiona la información de clientes.
2.  **cuentas-microservice**: Gestiona las cuentas y movimientos financieros de los clientes.

Ambos servicios se comunican y operan de manera independiente, utilizando bases de datos separadas y un bus de mensajería (ActiveMQ) para la comunicación asíncrona.

## Prerrequisitos

Asegúrate de tener instalado lo siguiente en tu sistema:

- **JDK 25**: El proyecto está compilado y diseñado para ejecutarse con Java 25.
- **Maven**: Para la compilación y gestión de dependencias del proyecto.
- **Docker y Docker Compose**: (Opcional) Para levantar el entorno completo de forma sencilla.

## Instalación

Puedes elegir entre dos métodos para levantar el entorno: usando Docker (recomendado) o de forma manual.

### Método 1: Usando Docker (Recomendado)

Este es el método más sencillo, ya que levanta toda la infraestructura (microservicios, bases de datos, Nginx, ActiveMQ) con un solo comando.

1.  **Clonar los repositorios**:
    Asegúrate de clonar ambos repositorios en el mismo directorio padre.

    ```bash
    git clone https://github.com/Programmercito/persona-microservice.git
    git clone https://github.com/Programmercito/cuentas-microservice.git
    ```

2.  **Levantar los contenedores**:
    Navega al directorio `cuentas-microservice` y ejecuta el siguiente comando:

    ```bash
    docker-compose up --build -d
    ```

    Este comando construirá las imágenes de los microservicios y levantará todos los servicios definidos en el archivo `docker-compose.yaml`.

3.  **Acceso a los servicios**:
    Un proxy inverso Nginx se encargará de redirigir las peticiones a los microservicios correspondientes. Los endpoints estarán disponibles en `localhost`:
    - **API de Identidad**: `http://localhost/api/identidad/...`
    - **API de Finanzas**: `http://localhost/api/finanzas/...`

### Método 2: Instalación Manual

Si prefieres no usar Docker, puedes compilar y ejecutar cada microservicio manualmente.

1.  **Clonar los repositorios**:

    ```bash
    git clone https://github.com/Programmercito/persona-microservice.git
    git clone https://github.com/Programmercito/cuentas-microservice.git
    ```

2.  **Compilar los proyectos**:
    Para levantar en local se debe tener Mariadb y Activemq Artemis levantados y en sus puertos por defecto, se puede utilizar docker :
    Windows:
    docker run -d --name mariadb-osbo -e MARIADB_ROOT_PASSWORD=root -v c:/opt/osbo:/var/lib/mysql -p 3306:3306 mariadb:latest
    Linux:
    docker run -d --name mariadb-osbo -e MARIADB_ROOT_PASSWORD=root -v /opt/osbo:/var/lib/mysql -p 3306:3306 mariadb:latest

    ActiveMQ Artemis:
    docker run --detach --name artemis-broker -p 61616:61616 -p 8161:8161  -e ARTEMIS_USERNAME=artemis -e ARTEMIS_PASSWORD=artemis --rm apache/activemq-artemis:latest-alpine

    Crear la bases de datos : personas y cuentas

    Navega al directorio raíz de cada proyecto y compila usando Maven.

    ```bash
    # Para persona-microservice
    cd persona-microservice
    mvn clean install

    # Para cuentas-microservice
    cd ../cuentas-microservice
    mvn clean install
    ```
    Para ejecutar ambos microservicios:

    mvn spring-boot:run

    persona-microservicio se ejecutara en el puerto 8080 y cuentas en 8081

3.  **Ejecutar los microservicios**:
    Una vez compilados, puedes ejecutar cada aplicación. Deberás configurar las variables de entorno para la conexión a la base de datos y ActiveMQ manualmente.

## Base de Datos

Los archivos SQL para la creación de las tablas se encuentran en el directorio `/files` de cada proyecto. Sin embargo, la aplicación está configurada con JPA para crear y actualizar las tablas automáticamente (`ddl-auto`), por lo que no es estrictamente necesario ejecutar los scripts para el primer arranque.

## Pruebas con Postman

Para facilitar las pruebas de la API, se ha incluido una colección de Postman en el directorio `/files` de cada repositorio (`postman.json`). Puedes importarla en Postman para tener acceso a todos los endpoints preconfigurados y listos para usar.

