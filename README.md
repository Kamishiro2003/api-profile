# **api-profile**

**Descripción:**  
Este proyecto es una API RESTful desarrollada con Spring Boot que permite gestionar usuarios y sus contraseñas. Implementa autenticación basada en JWT, sigue buenas prácticas de desarrollo como las normas de Checkstyle de Google. Además, se ha logrado una cobertura de pruebas del 99% con Jacoco.

---

## **Herramientas Utilizadas**

- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3.4.1
- **Gestor de Dependencias:** Gradle (versión 8.11.1)
- **Base de Datos:** MySQL (imagen oficial Docker: `mysql:8.0`)
- **Contenedorización:** Docker
- **Documentación API:** Swagger UI ([http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html))
- **Pruebas Unitarias:** JUnit 5, Mockito
- **Cobertura de Código:** Jacoco (99% de cobertura)
- **Estilo de Código:** Checkstyle (basado en las normas de Google)

---

## **Requisitos Previos**

Para ejecutar este proyecto, asegúrate de tener instalado lo siguiente:

1. **Java 17**  
   Descarga e instala desde [OpenJDK](https://openjdk.org/).

2. **Gradle 8.11.1**
   Descarga e instala desde [Gradle](https://gradle.org/install/).
   
3. **Docker**
   Asegúrate de tener Docker instalado y configurado. Usa la imagen oficial de MySQL (mysql:8.0) para la base de datos.

---

## **Configuración Inicial**

1. **Clona el Repositorio**
   - git clone https://github.com/Kamishiro2003/api-profile.git
   - cd api-profile
   
2. **Configura el Archivo .env**
   - Copia el archivo .env.example a .env:
      - cp .env.example .env
   - Edita el archivo .env con las variables necesarias para el entorno
  
3. **Genera el Build**
   Construye el Proyecto con Gradle
      - ./gradlew build
  
4. **Inicia los Contenedores Docker**
   - docker-compose up --build

5. **Verifica la Documentación del swagger**
   Una vez que el proyecto esté en funcionamiento, accede a la documentación de la API en:
   - http://localhost:8080/swagger-ui/index.html

---

## **Cobertura de Pruebas**
Se ha implementado Jacoco para medir la cobertura de pruebas. El informe indica una cobertura del 99%.
  - Para generar el informe localmente:
      - ./gradlew jacocoTestReport
  - El informe estará disponible en:
      - build/reports/jacoco/test/html/index.html

---

## **Contacto**
Si tienes preguntas o sugerencias, no dudes en contactarme:

**Nombre**: Darwin Granados
**Correo Electrónico**: darwingranados54@gmail.com
**GitHub**: [Kamishiro2003](https://github.com/Kamishiro2003)
