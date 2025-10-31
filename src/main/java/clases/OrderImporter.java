package clases;

import java.io.*; //para BufferedReader y FileReader
import java.util.*;

public class OrderImporter {

    /**
     * Clase responsable de importar los datos de un archivo CSV
     * y convertir cada línea en un objeto {@link Order}.
     *
     * Este importador detecta automáticamente la posición de las columnas
     * mediante el encabezado del archivo, lo que permite
     * procesar archivos CSV incluso si las columnas no están siempre en el mismo orden.
     */

    public List<Order> importCSV(String file) throws IOException {
        String line;
        int incrementalId = 1;
        List<Order> orders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            // Leer encabezado y dividirlo
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(",");

            // Crear un mapa clave-valor
            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(headers[i].trim(), i);
            }

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Order order = new Order(
                        incrementalId++,
                        data[columnIndex.get("Order ID")],
                        data[columnIndex.get("Order Priority")],
                        data[columnIndex.get("Order Date")],
                        data[columnIndex.get("Region")],
                        data[columnIndex.get("Country")],
                        data[columnIndex.get("Item Type")],
                        data[columnIndex.get("Sales Channel")],
                        data[columnIndex.get("Ship Date")],
                        Integer.parseInt(data[columnIndex.get("Units Sold")]),
                        Double.parseDouble(data[columnIndex.get("Unit Price")]),
                        Double.parseDouble(data[columnIndex.get("Unit Cost")]),
                        Double.parseDouble(data[columnIndex.get("Total Revenue")]),
                        Double.parseDouble(data[columnIndex.get("Total Cost")]),
                        Double.parseDouble(data[columnIndex.get("Total Profit")])
                );

                orders.add(order);
            }
        }

        return orders;
    }
}
