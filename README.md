# TBD-Lab03-1-2024
Laboratorio N°3 de Taller de Base de Datos

## Instrucciones para correr el proyecto

# Prerrequisitos
Se debe contar con las siguientes tecnologías para la ejecución optima del proyecto:
	
	• PgAdmin4
  • PostGIS
	• Node.js
	• IDE Intellij
	• JDK 17
	• PostgreSQL 16
 	• MongoDB Compass


# Clonar el Repositorio
Se debe clonar o descargar el proyecto desde GitHub, la URL al repositorio del proyecto es https://github.com/Opsord/TBD-Lab03-1-2024


# Base de Datos

### POSTGRESQL
Dentro de PgAdmin4 se debe crear una base de datos cuyo nombre debe ser Lab3TBD-1-2024, posteriormente se deben cargar en la base de datos recién creada los archivos que se encuentan en la ruta https://github.com/Opsord/TBD-Lab03-1-2024/tree/main/Database en el siguiente orden:

1- dbCreateV2.sql: Es el script de creación de tablas.
2- dbTriggersV2.sql: Es el archivo que contiene los triggers.
3- dbLoadDataV2.sql: Es el archivo que contiene la población de las tablas.

### MONGODB

Para ejecutar MongoDB 



# Backend
Dentro del repositorio de GitHub hay un carpeta llamada LAB-TBD, la que contiene el backend del proyecto. Para ejecutar el backend se deben seguir los siguientes pasos:

1. Abrir el proyecto en el IDE de preferencia (por ejemplo, IntelliJ IDEA, Eclipse).
2. Configurar las credenciales de la base de datos en el archivo `application.properties` ubicado en `src/main/resources/`.
3. Ejecuta la aplicación. La aplicación estará disponible en [http://localhost:8090](http://localhost:8090).


# Frontend
Dentro del repositorio de GitHub hay un carpeta llamada Frontend, la que contiene el frontend del proyecto. Para ejecutar el frontend se deben seguir los siguientes pasos:

1. Con el repositorio clonado, ir a a la carpeta Frontend.
2. Instalar los paquetes necesarios usando npm (Instalar node de ser necesario).
```
npm i
```
3. Crear un archivo .env en esta carpeta y agregar lo siguiente, remplazando LLAVE_API_DE_GOOGLE por su propia llave.
```
VITE_GOOGLE_API = LLAVE_API_DE_GOOGLE
```
4. Ejecutar el frontend con el siguiente comando.
```
npm run dev
```
5. La aplicación estará disponible en [http://localhost:5173](http://localhost:5173).


## Uso

1. Ejecuta el backend y el frontend de la aplicación en tus editores de código favoritos.
2. Accede a la aplicación desde tu navegador web.
3. Inicia sesión como coordinador o voluntario según corresponda.
4. Explora las diferentes funcionalidades ofrecidas por el sistema, como registro de voluntarios, asignación de tareas, seguimiento de tareas, etc.
5. ¡Comienza a utilizar el sistema para gestionar emergencias de manera eficiente!

## Contribución

Las contribuciones son bienvenidas. Si deseas contribuir al desarrollo de este proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad (`git checkout -b feature/AmazingFeature`).
3. Realiza tus cambios.
4. Haz commit de tus cambios (`git commit -m 'Add some AmazingFeature'`).
5. Haz push a la rama (`git push origin feature/AmazingFeature`).
6. Abre un pull request.

## Soporte

Para obtener ayuda o reportar problemas, por favor contacta al equipo de desarrollo.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.
