# HistorialPlus  📋

HistorialPlus es una aplicación web diseñada para gestionar registros históricos. Este documento proporciona una guía
para configurar, instalar y ejecutar el proyecto en tu entorno local.

## Antes de comenzar 🚀
Asegúrate de tener los siguientes requisitos:
- Java SDK 21
- Plugin para .env: Para que la aplicación funcione correctamente, necesitarás un plugin que maneje variables de entorno definidas en un archivo .env.

> [!NOTE]
> No es obligatorio usar un archivo `.env` junto con el plugin, siempre que las variables de entorno definidas en `example.env` estén configuradas en el sistema.

## Pasos para configurar el proyecto
1. Clona el repositorio
   Realiza un fork del repositorio original y luego clona tu fork a tu máquina local:
   
   ```
   git clone https://github.com/tu-usuario/HistorialPlus.git
   ```
2. Ingresa al directorio

    ```
    cd HistorialPlus
   ```
3. Crear y configurar el archivo `.env`
   Utiliza el archivo `example.env` proporcionado como plantilla. Copia el contenido de `example.env` y crea un nuevo archivo `.env`:

   ```
   cp example.env .env
   ```
   
  > [!IMPORTANT]  
  > Asegúrate de que el contenido del archivo `.env` este completo
