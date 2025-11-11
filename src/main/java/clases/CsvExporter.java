package clases;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;



/**
 * Clase encargada de exportar los pedidos almacenados en la aplicación
 * a un archivo CSV con el formato y orden especificado por el orden de Order ID
 */

public class CsvExporter {

    private static final String[] HEADER = {
            "Order ID","Order Priority","Order Date","Region","Country",
            "Item Type","Sales Channel","Ship Date","Units Sold","Unit Price",
            "Unit Cost","Total Revenue","Total Cost","Total Profit"
    };

    public static void exportSortedByOrderId(List<Order> orders, Path outPath) throws IOException {
        if (outPath.getParent() != null) Files.createDirectories(outPath.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8)) {
            bw.write(String.join(",", HEADER));
            bw.newLine();
            for (Order o : orders) {
                bw.write(row(o));
                bw.newLine();
            }
        }
    }

    private static String row(Order o) {
        String orderDate = formatDateOut(o.getOrderDate());
        String shipDate  = formatDateOut(o.getShipDate());
        return String.join(",",
                csv(o.getOrderId()),
                csv(o.getPriority()),
                csv(orderDate),
                csv(o.getRegion()),
                csv(o.getCountry()),
                csv(o.getItemType()),
                csv(o.getSalesChannel()),
                csv(shipDate),
                String.valueOf(o.getUnitsSold()),
                String.valueOf(o.getUnitPrice()),
                String.valueOf(o.getUnitCost()),
                String.valueOf(o.getTotalRevenue()),
                String.valueOf(o.getTotalCost()),
                String.valueOf(o.getTotalProfit())
        );
    }

    // Formatea de 'yyyy-MM-dd' a 'dd/MM/yyyy'
    private static String formatDateOut(String yyyyMMdd) {
        if (yyyyMMdd == null || yyyyMMdd.isBlank()) return "";
        String[] p = yyyyMMdd.split("-");
        if (p.length != 3) return yyyyMMdd; // si no viene en ese formato, lo deja tal cual
        return p[2] + "/" + p[1] + "/" + p[0];
    }

    private static String csv(String s) {
        if (s == null) return "";
        boolean needsQuotes = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        String v = s.replace("\"", "\"\"");
        return needsQuotes ? "\"" + v + "\"" : v;
    }
}
