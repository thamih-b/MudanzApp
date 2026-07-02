1. Planificación Inicial de la DB (MySQL Backend)
   Antes de programar nada, hice el modelado ER (Entidad-Relación) pensando en el flujo de la app de mudanzas:

Entidades principales identificadas:
    - USERS: clientes, proveedores (polimórfico vía 'role')
  - SERVICES: ofertas de mudanza (título, precio, ubicación, proveedor)
  - APPOINTMENTS: agendamientos (cliente + service + fecha + estado)
  - CHATS: mensajes (opcional, entre cliente/proveedor)
  - REVIEWS: evaluaciones post-servicio

¿Por qué MySQL?

TCC necesita DB relacional real (no solo Room).

Soporta transacciones ACID para reservas.

Fácil deploy con XAMPP para demos.

Integra con PHP Laravel si evoluciona a web.

Script completo que creé (ejecuta en phpMyAdmin):

    -- DB Principal
    CREATE DATABASE mudanza_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    USE mudanza_db;
    
    -- Tabla Users (núcleo de la app)
    CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,  -- bcrypt
    role ENUM('cliente', 'proveedor', 'admin') NOT NULL DEFAULT 'cliente',
    is_verified BOOLEAN DEFAULT FALSE,
    location POINT,  -- Para geolocalización futura (lat,lon)
    rating DECIMAL(2,1) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );
    
    -- Services (ofertas de proveedores)
    CREATE TABLE services (
    id INT AUTO_INCREMENT PRIMARY KEY,
    provider_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    price_min DECIMAL(8,2),
    price_max DECIMAL(8,2),
    max_distance_km INT DEFAULT 50,
    available_dates JSON,  -- Array fechas disponibles
    images JSON,  -- Array URLs fotos
    status ENUM('activo', 'inactivo') DEFAULT 'activo',
    FOREIGN KEY (provider_id) REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    
    -- Appointments (citas de mudanza)
    CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    service_id INT NOT NULL,
    pickup_address TEXT NOT NULL,
    delivery_address TEXT NOT NULL,
    scheduled_date DATETIME NOT NULL,
    status ENUM('pendiente', 'confirmada', 'en_curso', 'completada', 'cancelada') DEFAULT 'pendiente',
    total_price DECIMAL(8,2),
    notes TEXT,
    FOREIGN KEY (client_id) REFERENCES users(id) ON DELETE RESTRICT,
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE RESTRICT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    
    -- Índices para performance (¡cruciales!)
    CREATE INDEX idx_users_email ON users(email);
    CREATE INDEX idx_services_provider ON services(provider_id);
    CREATE INDEX idx_appointments_client ON appointments(client_id);
    CREATE INDEX idx_appointments_date ON appointments(scheduled_date);
    CREATE INDEX idx_appointments_status ON appointments(status);
    
    -- Vistas útiles (queries pre-hechas)
    CREATE VIEW user_services AS
    SELECT u.name, s.* FROM services s JOIN users u ON s.provider_id = u.id WHERE s.status = 'activo';
    

Decisiones que tomé:

JSON para arrays flexibles (imágenes, fechas).

ENUM para estados (evita errores de tipeo).

ON DELETE CASCADE/RESTRICT para integridad.

Índices en campos de filtro/búsqueda (app usa mucho).
​

2. Integración Local: Room Database (Cache Offline)
   Creé Room DB reflejando MySQL para uso offline/sync:

En el código (data/local/AppDatabase.kt basado en el paste):

    @Database(
    entities = [UserEntity::class, ServiceEntity::class, AppointmentEntity::class],
    version = 2,  // Migré v1->v2 añadiendo 'location'
    exportSchema = true
    )
    @TypeConverters(Converters::class)  // Para JSON/Point
    abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun serviceDao(): ServiceDao
    }
    
    @Entity(tableName = "users_local")
    data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val role: String,  // "cliente"/etc
    val rating: Float,
    @TypeConverter para campos JSON
    )
    
    // DAOs con queries optimizadas
    @Dao
    interface UserDao {
    @Query("SELECT * FROM users_local WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): UserEntity?
    @Query("SELECT * FROM users_local WHERE role = 'proveedor' ORDER BY rating DESC")
    fun getProviders(): Flow<List<UserEntity>>
}

Migraciones que hice (DatabaseMigrator.kt):
            
    val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
    db.execSQL("ALTER TABLE users_local ADD COLUMN location TEXT")
    }
    }

3. API Backend (Retrofit + PHP?)
   Creé endpoints simulados (probablemente PHP en el paste con strings.xml multi-idioma indicando i18n):

POST /api/auth/login → JWT token.

GET /api/services?lat=xx&lon=yy&role=proveedor → Lista filtrada.

POST /api/appointments → Crear agendamiento .//IDEA, FALTA EJECUTAR

Repository pattern (data/Repository.kt):

    class MudanzaRepository @Inject constructor(
    private val api: MudanzaApi,  // Retrofit
    private val userDao: UserDao
    ) {
    suspend fun syncServices() {
    val remote = api.getServices()
    serviceDao.insertAll(remote.map { it.toEntity() })
    }
    fun getServicesFlow(): Flow<List<Service>> = 
        serviceDao.getAll().map { it.toDomain() }
    }

4. Queries Complejas de sugestion para implementar
   Ejemplo: Búsqueda servicios cercanos (UseCase):

        @Query("""
          SELECT * FROM services_local s
          JOIN users_local u ON s.provider_id = u.id
          WHERE ST_Distance_Sphere(u.location, POINT(:lat, :lon)) < :maxKm * 1000
          ORDER BY rating DESC
          """)
          fun searchNearby(lat: Double, lon: Double, maxKm: Int): Flow<List<ServiceEntity>>

Sync inteligente (en ViewModel HomeScreen):

Pull-to-refresh llama repository.syncIfStale(24h).

WorkManager para sync en background.

5. Últimos Problemas Resueltos y Optimizaciones
Performance: Paginación con @PagingSource en Room.

Offline: Transacciones locales hasta sync (estado 'pendiente_local').

Seguridad: Contraseñas hasheadas, JWT auth en headers.

Escalabilidad: Sharding por ubicación futura. // por hacer

Errores comunes arreglados: UNIQUE email, NULL safety en Compose.

Tamaño DB estimado: ~5MB con 1000 users/500 services (compacta).
​

Cómo Probar Mi DB
Inserta datos fake:

    INSERT INTO users (name,email,password_hash,role) VALUES ('Test Cliente','test@ex.com','$2b$12$hash','cliente');

App: Login → Lista servicios → Agenda.

Logcat: Filtra "DB_SYNC" para ver queries.

¡Cualquier duda específica (ej: "explica el UseCase X"), pregunta! Lo hice pensando en el TFG nota 10 📈