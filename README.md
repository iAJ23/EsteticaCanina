# EsteticaCanina — Sistema web para estética canina

Aplicación full stack para administrar una estética canina: permite registrar mascotas y sus propietarios, consultar expedientes, organizar citas y controlar el acceso del personal por roles.

Este proyecto nació como una aplicación Java de escritorio y se transformó en una aplicación web para usar desde un navegador. Conserva el dominio y las entidades que siguen siendo útiles, y reemplaza las ventanas Swing por páginas web y endpoints REST.

> **Demo:** todavía no hay una URL pública de la aplicación. GitHub aloja el código; para ejecutar Spring Boot y conectarse a MySQL hace falta desplegar la aplicación y la base de datos en un servicio de alojamiento.

## Contenido

- [Funciones](#funciones)
- [Roles y permisos](#roles-y-permisos)
- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Transformación de escritorio a web](#transformación-de-escritorio-a-web)
- [Decisiones y retos técnicos](#decisiones-y-retos-técnicos)
- [Requisitos](#requisitos)
- [Configuración local](#configuración-local)
- [Rutas web y API](#rutas-web-y-api)
- [Seguridad y datos](#seguridad-y-datos)
- [Despliegue](#despliegue)
- [Mejoras futuras](#mejoras-futuras)

## Funciones

- Página de inicio con accesos separados para **Registrar mascota** y **Ver datos**.
- Alta, consulta y edición de mascotas y propietarios.
- Expediente de cada mascota con raza, color, alergias, necesidades de atención especial y observaciones.
- Agenda para crear, consultar, actualizar y eliminar citas, asociadas a una mascota y con fecha, hora, servicio, estado y notas.
- Administración de cuentas de usuario, roles, estado de cuenta y cambio de contraseña.
- API REST para consultar y realizar operaciones CRUD de mascotas y citas.
- Interfaz adaptable con páginas renderizadas en el servidor mediante Thymeleaf.

## Roles y permisos

| Rol | Permisos |
| --- | --- |
| `ADMIN` | Acceso total; administra usuarios y roles, y puede eliminar registros. |
| `OPERADOR` | Consulta, registra y edita mascotas y citas. No puede administrar usuarios ni eliminar registros. |
| `CONSULTA` | Acceso de solo lectura a los datos y la agenda. |

No hay registro público de cuentas. El administrador crea las cuentas del personal desde la sección de usuarios.

## Tecnologías

| Área | Tecnología |
| --- | --- |
| Lenguaje y runtime | Java 17 |
| Backend | Spring Boot 3.5.16, Spring MVC y Spring Web |
| Vistas web | Thymeleaf, HTML y CSS |
| API | Controladores REST de Spring (`/api/mascotas` y `/api/citas`) |
| Persistencia | Spring Data JPA, Hibernate y Jakarta Persistence |
| Base de datos | MySQL y MySQL Connector/J |
| Autenticación y autorización | Spring Security, roles y contraseñas con BCrypt |
| Construcción y dependencias | Apache Maven |
| Pruebas | Spring Boot Test y JUnit 4 para la prueba heredada |

Las versiones de Spring y sus dependencias administradas se declaran en `pom.xml` usando `spring-boot-starter-parent`. Java 17 permite mantener una base moderna compatible con Spring Boot 3 y el espacio de nombres Jakarta.

## Arquitectura

El código está organizado por responsabilidades:

```text
src/main/java/com/example/
├── Gui/          # Ventanas Swing heredadas del proyecto de escritorio
├── Logic/        # Entidades del dominio, estados y roles
├── Persistence/  # Repositorios Spring Data y persistencia heredada
├── security/     # Inicio de sesión, autorización y creación inicial de ADMIN
└── web/          # Controladores MVC/REST, formularios, DTO y servicios
```

La interfaz web usa controladores MVC y plantillas bajo `src/main/resources/templates`. Los estilos, imágenes y otros recursos del navegador están en `src/main/resources/static`.

### Modelo principal

- **Mascota:** datos y notas del expediente; se relaciona uno a uno con su propietario (`Dueno`).
- **Dueno:** nombre y teléfono del propietario.
- **Cita:** fecha, hora, servicio, estado y notas; cada cita corresponde a una mascota.
- **Usuario:** nombre de usuario, contraseña cifrada, rol y estado activo.

## Transformación de escritorio a web

La versión de escritorio agrupaba el flujo en ventanas como `Principal`, `CargarDatos`, `VerDatos` y `ModificarDatos`. Esa interfaz funciona en la computadora donde se ejecuta Java, pero no puede servirse directamente a un navegador.

Para convertir el proyecto:

1. Se conservaron las entidades y los conceptos del dominio que podían reutilizarse, incluido el propietario (`Dueno`).
2. Las ventanas Swing dejaron de ser el punto de entrada para usuarios web. Se añadieron páginas Thymeleaf equivalentes para el inicio, el registro, la consulta, la agenda y la administración de cuentas.
3. Se separaron controladores web, servicios y repositorios para que las páginas y la API usen la misma lógica de aplicación.
4. Se añadieron endpoints REST para que otros clientes puedan consultar o gestionar mascotas y citas.
5. Se conectó la aplicación con MySQL mediante variables de entorno y JPA/Hibernate.
6. Se agregaron inicio de sesión y autorización por roles para limitar las acciones disponibles según el usuario.

Las clases Swing y sus formularios `.form` permanecen en el repositorio como parte del origen y referencia de la migración; la aplicación web arranca desde `com.example.App`.

## Decisiones y retos técnicos

- **Interfaz de escritorio frente a navegador:** las ventanas Swing no se pueden convertir en páginas web automáticamente. Se recrearon los flujos como rutas MVC y plantillas HTML, manteniendo los procesos de negocio útiles.
- **Compatibilidad del stack:** el proyecto se configuró con Java 17 y Spring Boot 3.5.16. Spring Boot 3 utiliza Jakarta Persistence (`jakarta.persistence`) para mapear entidades JPA.
- **Separación de responsabilidades:** los controladores reciben las peticiones, los servicios aplican validaciones y reglas, y los repositorios consultan la base de datos.
- **Acceso seguro:** se añadió Spring Security con roles y BCrypt para almacenar las contraseñas como hashes. La cuenta inicial `ADMIN` se crea solo cuando la base de datos no contiene usuarios y requiere `APP_ADMIN_PASSWORD` de al menos 12 caracteres.
- **Credenciales fuera del código:** la configuración web toma los datos de conexión y el secreto de inicialización desde variables de entorno. La contraseña que estaba escrita en la configuración Hibernate heredada se retiró y se sustituyó por valores de marcador.
- **Nombres del dominio:** en el proyecto original se usaba `Dueno` para el propietario de la mascota. La interfaz y la documentación se refieren a esa persona como propietario, sin duplicar el concepto en otra entidad.
- **Datos relacionados:** las citas enlazan a una mascota existente, y su servicio valida que se proporcionen fecha, hora, servicio y mascota antes de guardar.

## Requisitos

- JDK 17 o posterior.
- Apache Maven 3.6.3 o posterior.
- MySQL en ejecución y una base de datos disponible.
- Git para clonar el repositorio.

## Configuración local

### 1. Clonar el proyecto

```bash
git clone https://github.com/iAJ23/EsteticaCanina.git
cd EsteticaCanina
```

### 2. Crear la base de datos

En MySQL ejecuta:

```sql
CREATE DATABASE estetica_canina CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Hibernate crea o actualiza las tablas al iniciar (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Configurar variables de entorno

`.env.example` sirve como guía. La aplicación no carga archivos `.env` automáticamente; define las variables en IntelliJ IDEA, en la terminal o en la configuración del servicio de alojamiento:

| Variable | Uso | Valor local de ejemplo |
| --- | --- | --- |
| `DB_URL` | URL JDBC de MySQL | `jdbc:mysql://localhost:3306/estetica_canina?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `DB_USERNAME` | Usuario de la base de datos | `tu_usuario` |
| `DB_PASSWORD` | Contraseña de la base de datos | `tu_clave` |
| `APP_ADMIN_USERNAME` | Usuario inicial del administrador | `admin` |
| `APP_ADMIN_PASSWORD` | Contraseña inicial; mínimo 12 caracteres | Define una clave segura propia |
| `PORT` | Puerto HTTP, cuando el proveedor lo asigna | `8080` |

En PowerShell, reemplaza los valores de ejemplo y ejecuta:

```powershell
$env:DB_URL = 'jdbc:mysql://localhost:3306/estetica_canina?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
$env:DB_USERNAME = 'tu_usuario'
$env:DB_PASSWORD = 'tu_clave'
$env:APP_ADMIN_USERNAME = 'admin'
$env:APP_ADMIN_PASSWORD = 'define_una_clave_segura_de_12_o_mas_caracteres'
mvn spring-boot:run
```

En IntelliJ IDEA, configura las mismas variables en la configuración de ejecución de `com.example.App`, recarga el proyecto Maven y ejecútalo. Después abre [http://localhost:8080](http://localhost:8080) e inicia sesión con la cuenta definida.

**Importante:** la cuenta inicial se crea únicamente cuando no existe ningún usuario. Si ya existe una cuenta en la base, cambiar `APP_ADMIN_PASSWORD` no cambia su contraseña. No uses valores de ejemplo ni credenciales reales en commits.

## Rutas web y API

### Páginas web

| Ruta | Descripción |
| --- | --- |
| `/` | Inicio y accesos a las funciones principales |
| `/login` | Inicio de sesión |
| `/mascotas` | Directorio y consulta de mascotas |
| `/mascotas/nueva` | Registro de mascota y propietario |
| `/agenda` | Consulta de citas |
| `/agenda/nueva` | Registro de cita |
| `/usuarios` | Gestión de cuentas (solo `ADMIN`) |

### API REST

| Recurso | Operaciones |
| --- | --- |
| `/api/mascotas` | `GET` listar; `GET /{id}` consultar; `POST` crear; `PUT /{id}` actualizar; `DELETE /{id}` eliminar |
| `/api/citas` | `GET` listar; `GET /{id}` consultar; `POST` crear; `PUT /{id}` actualizar; `DELETE /{id}` eliminar |

La API está protegida por Spring Security y las operaciones respetan los permisos configurados para cada rol.

## Seguridad y datos

- No subas `.env`, contraseñas, datos personales reales ni volcados de MySQL.
- En producción, configura credenciales y secretos mediante el almacén de variables secretas del proveedor.
- Usa una cuenta MySQL con privilegios limitados para la aplicación, no la cuenta `root`.
- Cambia la clave inicial después del primer acceso y crea cuentas individuales para el personal.
- La carpeta `target/`, las salidas locales y la configuración privada están excluidas mediante `.gitignore`.
- La aplicación conserva datos de propietarios y teléfonos: usa datos ficticios en capturas y demostraciones públicas.

## Despliegue

El código está publicado en [GitHub](https://github.com/iAJ23/EsteticaCanina). GitHub Pages puede alojar páginas estáticas, pero no ejecuta este backend de Spring Boot ni proporciona la base MySQL de la aplicación. Para ofrecer una demo navegable se necesita un proveedor que pueda ejecutar Java/Spring Boot y conectar a una base MySQL. En ese servicio configura `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `APP_ADMIN_USERNAME`, `APP_ADMIN_PASSWORD` y, si corresponde, `PORT` como variables de entorno o secretos.

Cuando exista una demo pública, agrega aquí su URL y capturas de pantalla sin información personal real.

## Mejoras futuras

- Desplegar la aplicación y la base MySQL en un entorno público con HTTPS.
- Añadir capturas de la interfaz y una demo con datos ficticios.
- Incorporar migraciones versionadas de esquema (por ejemplo, Flyway) para los despliegues de producción, en vez de depender de `ddl-auto=update`.
- Ampliar las pruebas automatizadas de servicios, seguridad, repositorios y rutas web/API.
- Añadir documentación OpenAPI para la API REST.
- Revisar la configuración Hibernate heredada si se desea volver a ejecutar la versión Swing de manera independiente.
