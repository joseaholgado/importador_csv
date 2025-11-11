package clases;

import java.sql.*;
import java.nio.file.*;

/**
 * Clase responsable de gestionar la conexión con la base de datos SQLite.
 * Se encarga de crear la carpeta ./db automáticamente y generar el esquema
 * con la tabla 'orders' si no existe.
 */

public final class Database {
    private static final String URL = "jdbc:sqlite:./db/app.db"; //Ruta de la JDBC


    /**
     * Establece una conexión con la base de datos SQLite.
     * Si la carpeta ./db no existe, la crea automáticamente antes de conectar.
     *
     * @return objeto Connection listo para usar con SQL
     * @throws SQLException si falla la conexión
     */
    public static Connection get() throws SQLException {
        ensureDbFolder();
        return DriverManager.getConnection(URL);
    }

    /**
     * Comprueba si la carpeta ./db existe; si no, la crea.
     * Esto evita el error "path does not exist" de SQLite.
     */

    private static void ensureDbFolder() {
        try {
            Path folder = Paths.get("./db");
            if (Files.notExists(folder)) {
                Files.createDirectories(folder);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear la carpeta ./db: " + e.getMessage(), e);
        }
    }

    /**
     * Inicializa el esquema de la base de datos si no existe.
     * Crea la tabla 'orders' con todos los campos que vienen del CSV.
     * También genera un índice por order_id para mejorar búsquedas.
     *
     * @throws SQLException si ocurre un error durante la creación del esquema
     */
    public static void initSchema() throws SQLException {
        ensureDbFolder();
        String sql = """
            CREATE TABLE IF NOT EXISTS orders (
              id INTEGER PRIMARY KEY,
              order_id TEXT NOT NULL,
              order_priority TEXT,
              order_date TEXT,
              region TEXT,
              country TEXT,
              item_type TEXT,
              sales_channel TEXT,
              ship_date TEXT,
              units_sold INTEGER,
              unit_price REAL,
              unit_cost REAL,
              total_revenue REAL,
              total_cost REAL,
              total_profit REAL
            );
            CREATE INDEX IF NOT EXISTS idx_orders_order_id ON orders(order_id);
            """;

        try (Connection c = get(); Statement st = c.createStatement()) {
            st.executeUpdate(sql);
        }
    }
}
