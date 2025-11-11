package clases;

import java.util.*;

/**
 * Clase responsable de generar y mostrar en consola
 * un resumen de los pedidos almacenados en la base de datos.
 */

public class SummaryService {

    /**
     * Imprime en consola un resumen de conteos de pedidos agrupados por diferentes columnas
     * @param dao objeto OrderDao utilizado para acceder a la base de datos y contar registros.
     */
    public static void printDbSummary(OrderDao dao) {
        List<String> campos = List.of("region","country","item_type","sales_channel","order_priority");
        for (String campo : campos) {
            try {
                var map = dao.countBy(campo);
                System.out.println("\n== " + campo.toUpperCase() + " ==");
                map.entrySet().stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey()))
                        .forEach(e -> System.out.printf("%-30s : %d%n", e.getKey(), e.getValue()));
            } catch (Exception e) {
                System.out.println("Error en conteo de " + campo + ": " + e.getMessage());
            }
        }
    }
}
