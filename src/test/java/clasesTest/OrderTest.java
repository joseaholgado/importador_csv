package clasesTest;

import clases.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase {@link Order}.
 * Verifica que los métodos y atributos principales del objeto Order
 * funcionen correctamente, incluyendo el cálculo del beneficio total
 * y la asignación de unidades vendidas.
 */
class OrderTest {

    /**
     * Verifica que el cálculo del beneficio total (totalProfit)
     */

    @Test
    void testTotalProfitCalculation() {
        // Creamos un objeto Order con datos de prueba
        Order order = new Order(
                "686800706", "M", "2024-01-10", "Asia", "Japan",
                "Books", "Online", "2024-01-15", 10,
                50.0, 30.0, 500.0, 300.0, 200.0
        );

        assertEquals(200.0, order.getTotalProfit(), "El beneficio total debería ser 200.0");
    }

    /**
     * Verifica que el número de unidades vendidas (unitsSold)
     * se asigne y recupere correctamente.
     */

    @Test
    void testUnitsSold() {
        Order order = new Order(
                "185941302", "Low", "2024-01-01", "Asia",
                "China", "Electronics", "Offline", "2024-01-05",
                5, 100.0, 80.0, 500.0, 400.0, 100.0
        );

        assertEquals(5, order.getUnitsSold(), "El número de unidades vendidas debería ser 5");
    }
}
