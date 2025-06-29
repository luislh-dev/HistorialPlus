[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/luislh-dev/HistorialPlus)

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
   git clone https://github.com/LuisLopez-developer/HistorialPlus.git
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
  > Asegúrate de que el contenido del archivo `.env` este completo, y con las credenciales correctas.

## Configuración de Git Hooks

El proyecto incluye hooks de Git para asegurar la calidad del código. **Se configuran automáticamente al ejecutar o construir el proyecto con Gradle** (por ejemplo, usando la opción de "Run" en tu IDE o ejecutando `./gradlew build` en la terminal).

1. **pre-commit**: Ejecuta `checkstyleMain`
2. **pre-push**: Antes de hacer push, ejecuta:
   - `checkstyleMain` en todo el proyecto.
   - Todos los tests (`./gradlew test`).
   - Verifica que el proyecto compile correctamente (`./gradlew compileJava`).


> [!TIP]
> No necesitas instalar los hooks manualmente. Simplemente, ejecuta el proyecto o cualquier tarea de Gradle y los hooks se instalarán o actualizarán automáticamente en `.git/hooks`.

### Configuración manual (solo si es necesaria)

```bash
git config core.hooksPath githooks
chmod +x githooks/*