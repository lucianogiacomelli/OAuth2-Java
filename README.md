# ⚙️ oauth2 — Demo Spring Boot con OAuth2 / Form Login y seguridad por métodos

Este repositorio contiene una aplicación de ejemplo construida con Spring Boot que demuestra integración de OAuth2 (clientes GitHub/Google), login por formulario y control de acceso a nivel de método usando Spring Security.

Checklist — lo que encontrarás en este README
- [x] Resumen del proyecto orientado a un lector no técnico.  
- [x] Tecnologías y herramientas usadas.  
- [x] Qué se construyó y por qué importa (valor del proyecto).  
- [x] Resumen de la arquitectura y decisiones clave de seguridad.  
- [x] Cómo ejecutar la aplicación (instrucciones mínimas, en PowerShell).  
- [x] Dónde mirar en el código para validar el trabajo (puntos de interés para revisión).  

Este proyecto muestra cómo habilitar autenticación mediante formularios y clientes OAuth2 (por ejemplo GitHub y Google) y cómo aplicar autorización a nivel de método con anotaciones `@PreAuthorize`. Los detalles de seguridad se configuran en código y en `application.properties`, y la aplicación está preparada para persistencia con PostgreSQL (configurada mediante variables de entorno).

- Demuestra integración de OAuth2 clients con Spring Security y login por formulario.  
- Muestra control de acceso a nivel de método (`@EnableMethodSecurity` + `@PreAuthorize`), útil para aplicar políticas finas en endpoints REST.  
- Buen punto de partida para extender hacia un sistema RBAC completo o para integrar JWT / almacenamiento de usuarios en BD.

Stack tecnológico
- Java 25 
- Spring Boot 4.1.0  
- Spring Security + Spring Security OAuth2 Client  
- Lombok   
- Gradle

Qué incluye (resumen de funcionalidades)
- Login por formulario (`formLogin()`) y soporte para OAuth2 login (`oauth2Login()`), con configuración en `application.properties`.  
- Control de acceso por método: `@EnableMethodSecurity` y uso de `@PreAuthorize` en controladores.  
- Endpoints de ejemplo:
  - `GET /api/no-sec` — endpoint público (`@PreAuthorize("permitAll()")`).
  - `GET /api/sec` — endpoint protegido, sólo accesible si el usuario está autenticado (`@PreAuthorize("isAuthenticated()")`).
- Configuración de persistencia preparada para PostgreSQL (variables de entorno para URL/usuario/contraseña) y `spring.jpa.hibernate.ddl-auto=update` para desarrollo.

Decisiones clave de seguridad
- CSRF deshabilitado en la configuración de ejemplo (`.csrf(csrf -> csrf.disable())`) para facilitar llamadas desde clientes sin cookies (útil en demostraciones); en producción deberías evaluar este ajuste.  
- Se habilitaron `formLogin()` y `oauth2Login()` por defecto (ver `SecurityConfig`). Los proveedores OAuth configurados por propiedades son GitHub y Google.  
- Seguridad a nivel de método habilitada con `@EnableMethodSecurity`, lo que permite usar `@PreAuthorize` y expresar políticas directamente sobre controladores/servicios.  
- Variables sensibles (credenciales DB, client IDs/secrets, cuenta de seguridad) se leen desde variables de entorno en `application.properties`.

Ejemplo de peticiones
- Endpoint público (no autenticado):

```bash
curl http://localhost:8080/api/no-sec
# Respuesta: "No sec"
```

- Endpoint protegido (requiere autenticación). Para interactuar:
  - Abrir http://localhost:8080 en un navegador y usar el formulario de login o el flujo OAuth (ej. visitar `/oauth2/authorization/github` para iniciar login con GitHub).

```bash
curl -u admin:password http://localhost:8080/api/sec
```

Nota: el proyecto habilita `formLogin()` y `oauth2Login()` explícitamente.

Endpoints OAuth2
- Iniciar login con GitHub: `/oauth2/authorization/github`  
- Iniciar login con Google: `/oauth2/authorization/google`

Dónde mirar en el código (puntos de interés)
- `src/main/java/com/giacomelli/oauth2/Secuity/config/SecurityConfig.java` — configuración de seguridad (CSRF, formLogin, oauth2Login, habilitación de seguridad de métodos).  
- `src/main/java/com/giacomelli/oauth2/Controller/HelloController.java` — controladores de ejemplo con `@PreAuthorize`.  
- `src/main/java/com/giacomelli/oauth2/Oauth2Application.java` — clase principal Spring Boot.  
- `src/main/resources/application.properties` — variables de configuración (DB, usuarios, OAuth clients).  
- `build.gradle` — versiones y dependencias (Spring Boot 4.1.0, Java 25, dependencias de seguridad y OAuth2).