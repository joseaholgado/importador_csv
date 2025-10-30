package clases;

import org.apache.commons.collections.list.TreeList;

import java.io.*; //para BufferedReader y FileReader
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class OrderImporter {
    /**
     * Importa un archivo CSV y convierte cada línea en un objeto Order.
     * @param file Ruta del archivo CSV.
     * @return Lista de objetos Order.
     * @throws IOException Si ocurre un error al leer el archivo.
     */

    public List<Order> importCSV(String file) throws IOException {
        String line;
        int incrementalId = 1;

        //Lista donde guardaremos los pedidos importados
        List<Order> orders = new ArrayList<>();
        orders.sort(Comparator.comparing(Order::getOrderId));

        //Abrimos el archivo para leerlo línea por línea
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine(); //salta el header



        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            // Creamos un objeto Order con los datos del CSV
            Order order = new Order(
                    incrementalId++,
                    data[0], data[1], data[2], data[3], data[4],
                    data[5], data[6], data[7], Integer.parseInt(data[8]),
                    Double.parseDouble(data[9]), Double.parseDouble(data[10]),
                    Double.parseDouble(data[11]), Double.parseDouble(data[12]),
                    Double.parseDouble(data[13])
            );

            orders.add(order);
        }

        reader.close();

        return orders;
    }
}
