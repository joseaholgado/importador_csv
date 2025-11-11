package clasesTest;

import clases.Database;
import java.sql.*;

public class ConnexionTest {

    public static void main(String[] args) {
        try (Connection conn = Database.get()) {
            if (conn != null) {
                System.out.println("✅ Conexión a SQLite establecida correctamente");
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("Base de datos: " + meta.getURL());
            } else {
                System.out.println("❌ No se pudo establecer la conexión (conn es null).");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con SQLite: " + e.getMessage());
        }
    }
}
