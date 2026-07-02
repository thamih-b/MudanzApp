¡Bienvenidos al proyecto APP-MUDANZA! Esta es la aplicación móvil para Android desarrollada como Trabajo de Conclusão de Curso (TCC) por nuestro equipo de 5 estudiantes de FP en Informática.

La app está diseñada para gestionar servicios de mudanzas (mudanzas de muebles, enseres y pequeños traslados), conectando clientes con proveedores locales. Usa Kotlin, Jetpack Compose para UI moderna, MySQL como base de datos backend, y arquitectura MVVM con Room para datos locales (o Retrofit para API).
​

Objetivos principales:

Registro/login de usuarios (clientes y proveedores).

Listado de servicios disponibles con filtros.

Solicitud de presupuestos y chat en tiempo real.

Gestión de citas y seguimiento de mudanzas.

Panel admin para moderación.

El proyecto está en fase final: funcional en emulador/dispositivo, pero necesita pruebas exhaustivas y polish UI.

Requisitos para Desarrollar
Android Studio Otter (o superior, 2024.2+).

JDK 17 o superior.

Emulador API 34+ o dispositivo físico.

MySQL 8.0 local (XAMPP/WAMP) para DB de desarrollo.

Git para control de versiones.

Dependencias clave (en build.gradle): Compose 1.6+, Lifecycle 2.8+, Navigation 2.8+, Hilt 2.52, Retrofit 2.11, Room 2.7+.
​

Instalación rápida:

Clona el repo: git clone [URL].

Abre en Android Studio: File > Open > carpeta app.

Sync Gradle (botón elefante).

Configura DB (ver sección Base de Datos).

Run en emulador.

Estructura de Módulos
El proyecto sigue Multi-Módulo para escalabilidad. Carpeta principal: app/ contiene todo.

app/
├── src/main/java/com/[paquete]/appmudanza/     # Código Kotlin principal
│   ├── di/                 # Inyección de dependencias (Hilt modules)
│   ├── data/               # Repositorios, DTOs, API calls
│   │   ├── local/          # Room DB entities/DAOs
│   │   └── remote/         # Retrofit services
│   ├── domain/             # UseCases, Models de negocio
│   ├── ui/                 # Pantallas Compose (Screens/)
│   │   ├── auth/           # Login/Register
│   │   ├── home/           # Dashboard principal
│   │   ├── services/       # Listado y detalles de mudanzas
│   │   ├── profile/        # Perfil usuario
│   │   └── admin/          # Panel admin
│   ├── navigation/         # NavHost y rutas
│   └── MainActivity.kt     # Entry point con ComposeView
├── res/                    # Recursos XML (drawables, strings multi-idioma)
├── build.gradle.kts        # Dependencias app-level
└── proguard-rules.pro      # Optimización release

Paquetes clave identificados:

com.[paquete].appmudanza.ui.*: Todas las pantallas en Jetpack Compose (ej. HomeScreen, ServiceDetailScreen).
​

data.local: Entities como User, Service, Appointment (probablemente).
​

No hay README existente ni docs detalladas; este es el primero completo.
​

Arquitectura de la App (MVVM + Clean Architecture)
UI Layer: Jetpack Compose puro (no XML legacy). Usa ViewModels con StateFlow para estado reactivo.

Domain Layer: UseCases puros (ej. GetServicesUseCase, LoginUseCase).

Data Layer: Repositorios que combinan local (Room) + remote (Retrofit/MySQL via API PHP?).

Navegación: Navigation Compose con NavHost en MainActivity.

Inyección: Hilt (@HiltViewModel, @Singleton).

Tema: Material 3 con colores dinámicos (dark/light).
​

Flujo típico:

Splash -> Auth (si no logueado).

Home: Tabs (Servicios, Mis Mudanzas, Perfil).

Servicio: Lista paginada -> Detalle -> Solicitar Presupuesto.

Base de Datos (MySQL + Room)
Backend Principal: MySQL 8.0 (servidor local/remoto).

Host: localhost (dev) / [IP servidor prod].

Puerto: 3306.

DB Name: mudanza_db.

User/Pass: root / [password] (cambia en local.properties o Hilt module).

Script de creación (ejecuta en phpMyAdmin/MySQL Workbench):

CREATE DATABASE mudanza_db;
USE mudanza_db;

CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
email VARCHAR(150) UNIQUE NOT NULL,
password_hash VARCHAR(255) NOT NULL,
role ENUM('cliente', 'proveedor', 'admin') DEFAULT 'cliente',
phone VARCHAR(20),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE services (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(200) NOT NULL,
description TEXT,
price DECIMAL(10,2),
provider_id INT,
location VARCHAR(200),
FOREIGN KEY (provider_id) REFERENCES users(id),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
id INT AUTO_INCREMENT PRIMARY KEY,
client_id INT,
service_id INT,
date DATETIME NOT NULL,
status ENUM('pendiente', 'confirmada', 'completada', 'cancelada'),
notes TEXT,
FOREIGN KEY (client_id) REFERENCES users(id),
FOREIGN KEY (service_id) REFERENCES services(id)
);

-- Índices para performance
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_service_provider ON services(provider_id);

Integración en App:

Room DB Local: Cache offline (entities mirror de tablas MySQL).

@Database(entities = [User::class, Service::class, Appointment::class], version = 1)

Remote: Retrofit API (probablemente PHP backend en /api/ endpoints como /login, /services).

Sync: UseCase sincroniza local/remote on app start.
​

Config DB en código:

En DatabaseModule.kt (Hilt): return Room.databaseBuilder(...).build().

API Base URL: En BuildConfig.API_URL o secrets.

Cómo Compilar y Probar
Sync y Build: Gradle Sync > Build > Make Project.

Run: Selecciona emulador > Run 'app'.

Debug: Logs en Logcat (filtra "APP-MUDANZA").

Tests: Unitarios en test/ (JUnit), UI en androidTest/ (Compose UI Test).

Release: ./gradlew assembleRelease (firma con keystore).

Errores comunes:

DB conexión: Verifica MySQL running y credenciales.

Compose Preview: "Restart Preview" si falla.

Gradle: Limpia ./gradlew clean si sync falla.
​

Contribuciones del Equipo

Enrique Martínez y Miguel Martínez: Arquitectura Android, Compose UI principal
Thamiris Bittencourt (Thami B): Arquitectura Android, DB design, Compose UI general.


Commits: Siempre feat:, fix:, docs: en Git.


📄 Licencia

Propietarios / Todos los Derechos Reservados © 2026 APP-MUDANZA Todos los derechos reservados.

Prohibida la reproducción, distribución, modificación o uso comercial sin autorización expresa por escrito de los autores.

✅ Uso personal y educativo permitido únicamente para estudio. 🤝

🛠 Desarrollado con ❤️ para organización productiva. Última actualización: Marzo 2026
