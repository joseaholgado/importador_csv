package clases;

import java.sql.*;
import java.util.*;

/**
 * Clase encargada de gestionar todas las operaciones de acceso a la base de datos
 * relacionadas con la tabla "orders".
 *
 * Permite insertar pedidos, vaciar la tabla, contar registros agrupados por columnas
 * y recuperar los pedidos ordenados por su número de pedido (Order ID).
 */

public class OrderDao {

    /**
     * Inserta en la base de datos todos los pedidos de la lista recibida.
     * Utiliza una transacción (commit manual) y un PreparedStatement con batch
     * para optimizar la inserción masiva y mejorar el rendimiento.
     */

    public void insertAll(List<Order> orders) throws SQLException {
        String sql = "INSERT INTO orders (id,order_id,order_priority,order_date,region,country,item_type," +
                "sales_channel,ship_date,units_sold,unit_price,unit_cost,total_revenue,total_cost,total_profit) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = Database.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            c.setAutoCommit(false);
            for (Order o : orders) {
                int i = 1;
                ps.setInt(i++, o.getId());
                ps.setString(i++, o.getOrderId());
                ps.setString(i++, o.getPriority());
                ps.setString(i++, o.getOrderDate());
                ps.setString(i++, o.getRegion());
                ps.setString(i++, o.getCountry());
                ps.setString(i++, o.getItemType());
                ps.setString(i++, o.getSalesChannel());
                ps.setString(i++, o.getShipDate());
                ps.setInt(i++, o.getUnitsSold());
                ps.setDouble(i++, o.getUnitPrice());
                ps.setDouble(i++, o.getUnitCost());
                ps.setDouble(i++, o.getTotalRevenue());
                ps.setDouble(i++, o.getTotalCost());
                ps.setDouble(i++, o.getTotalProfit());
                ps.addBatch();
            }
            ps.executeBatch();
            c.commit();
        }
    }

    /**
     * Borra todos los registros de la tabla "orders".
     * Se usa para limpiar la base de datos antes de importar un nuevo CSV,
     * de forma que no haya duplicados ni mezclas entre archivos distintos.
     */

    public void clearAll() throws SQLException {
        try (Connection c = Database.get(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM orders");
        }
    }

    /**
     * Realiza un conteo de pedidos agrupado por el nombre de columna recibido.
     *
     * Por ejemplo, se puede usar para obtener el número de pedidos por región,
     * país, tipo de producto, canal de venta o prioridad.
     *
     * @param column Nombre de la columna por la que se desea agrupar
     * @return Mapa con clave = valor de la columna, valor = número de registros
     */

    public Map<String, Long> countBy(String column) throws SQLException {
        String sql = "SELECT " + column + " AS k, COUNT(*) AS c FROM orders GROUP BY " + column;
        Map<String, Long> out = new LinkedHashMap<>();
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.put(String.valueOf(rs.getString("k")), rs.getLong("c"));
            }
        }
        return out;
    }

    /**
     * Recupera todos los pedidos de la base de datos, ordenados por el campo Order ID.
     * Este método se usa principalmente para la exportación final del CSV.
     * @return Lista de objetos Order con los datos de la base de datos.
     */

    public List<Order> findAllOrderByOrderId() throws SQLException {
        String sql = "SELECT * FROM orders ORDER BY order_id";
        List<Order> list = new ArrayList<>();
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet r = ps.executeQuery()) {
            while (r.next()) {
                list.add(new Order(
                        r.getInt("id"),
                        r.getString("order_id"),
                        r.getString("order_priority"),
                        r.getString("order_date"),
                        r.getString("region"),
                        r.getString("country"),
                        r.getString("item_type"),
                        r.getString("sales_channel"),
                        r.getString("ship_date"),
                        r.getInt("units_sold"),
                        r.getDouble("unit_price"),
                        r.getDouble("unit_cost"),
                        r.getDouble("total_revenue"),
                        r.getDouble("total_cost"),
                        r.getDouble("total_profit")
                ));
            }
        }
        return list;
    }
}
