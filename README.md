# Estética Canina web

Aplicación Spring Boot con interfaz web y API REST para administrar mascotas y sus propietarios.

Proyecto de portafolio: convierte el flujo de una estética canina de escritorio en una aplicación web con agenda, control de acceso por roles y persistencia en MySQL. Reemplaza esta línea con la URL pública cuando despliegues la aplicación.

## Funcionalidades

- Inicio web con accesos separados para registrar mascotas y consultar datos.
- Registro y administración de mascotas y propietarios.
- Agenda de citas.
- Roles `ADMIN`, `OPERADOR` y `CONSULTA` con permisos distintos.
- API REST para mascotas y citas.

## Requisitos

- JDK 17 o posterior
- Apache Maven 3.6.3 o posterior
- MySQL en ejecución
- En el primer arranque, define `APP_ADMIN_PASSWORD` con al menos 12 caracteres para crear la cuenta administradora inicial (`APP_ADMIN_USERNAME` puede cambiar su nombre; por defecto es `admin`).

## Base de datos

En MySQL, crea la base de datos una sola vez:

```sql
CREATE DATABASE estetica_canina CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

La aplicación crea o actualiza las tablas al iniciar. Para credenciales distintas, configura `DB_URL`, `DB_USERNAME` y `DB_PASSWORD` en el entorno antes de ejecutarla.

## Ejecutar desde IntelliJ IDEA

1. Abre el proyecto y selecciona un JDK 17 (o posterior) en **Project Structure**.
2. Recarga el proyecto Maven desde la ventana **Maven**.
3. Configura en IntelliJ estas variables de entorno en la configuración de ejecución: `DB_URL=jdbc:mysql://localhost:3306/estetica_canina?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`, `DB_USERNAME=tu_usuario` y `DB_PASSWORD=tu_contraseña`.
4. En el primer arranque, agrega `APP_ADMIN_USERNAME=admin` y `APP_ADMIN_PASSWORD=una_clave_segura_de_12_o_mas_caracteres`.
5. Ejecuta `com.example.App`.
6. Abre [http://localhost:8080](http://localhost:8080) e inicia sesión con la cuenta inicial.

También puedes iniciar desde una terminal en PowerShell:

```powershell
$env:DB_URL = 'jdbc:mysql://localhost:3306/estetica_canina?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
$env:DB_USERNAME = 'tu_usuario'
$env:DB_PASSWORD = 'tu_contraseña'
$env:APP_ADMIN_USERNAME = 'admin'
$env:APP_ADMIN_PASSWORD = 'una_clave_segura_de_12_o_mas_caracteres'
mvn spring-boot:run
```

Para guardar las variables de entorno localmente, copia `.env.example` como `.env` y completa los valores. La aplicación no carga `.env` automáticamente; configura esas variables en IntelliJ, en PowerShell o en el proveedor de alojamiento.

## Publicación

GitHub sirve para alojar el código y mostrarlo en tu portafolio. GitHub Pages solo publica sitios estáticos; esta aplicación necesita ejecutar Spring Boot y conectarse a MySQL, así que para una URL pública también se debe desplegar el backend y la base de datos en un proveedor compatible con Java y MySQL. Configura allí `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `APP_ADMIN_USERNAME` y `APP_ADMIN_PASSWORD` como variables secretas. No subas contraseñas, archivos `.env`, datos reales de clientes ni volcados de la base de datos al repositorio.

Cuando tengas la URL desplegada, reemplaza la línea de “Proyecto de portafolio” al principio por un enlace a la demo. Añade capturas de pantalla propias en una carpeta `docs/` si quieres presentar la interfaz en GitHub.

## Rutas

- Página de inicio: `http://localhost:8080/`
- Inicio de sesión: `http://localhost:8080/login`
- Directorio / ver datos: `http://localhost:8080/mascotas`
- Formulario de registro: `http://localhost:8080/mascotas/nueva`
- Agenda: `http://localhost:8080/agenda`
- Gestión de cuentas (solo ADMIN): `http://localhost:8080/usuarios`
- API: CRUD de mascotas en `/api/mascotas` y CRUD de citas en `/api/citas`

Los registros de propietario se administran junto con cada mascota, de acuerdo con la relación uno a uno que ya define el modelo. Los roles son `ADMIN` (acceso total y gestión de usuarios), `OPERADOR` (consulta, registro y edición) y `CONSULTA` (solo lectura). No existe registro público de usuarios; el administrador crea las cuentas.
