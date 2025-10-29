package clases;

import java.io.*; //para BufferedReader y FileReader
import java.util.ArrayList;
import java.util.List;

public class OrderImporter {
    /**
     * Importa un archivo CSV y convierte cada línea en un objeto Order.
     * @param file Ruta del archivo CSV.
     * @return Lista de objetos Order.
     * @throws IOException Si ocurre un error al leer el archivo.
     */

    public List<Order> importCSV(String file) throws IOException {
        String line;

        //Lista donde guardaremos los pedidos importados
        List<Order> orders = new ArrayList<>();

        //Abrimos el archivo para leerlo línea por línea
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine(); //salta el header



        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            // Creamos un objeto Order con los datos del CSV
            Order o = new Order(
                    data[0], data[1], data[2], data[3], data[4],
                    data[5], data[6], data[7], Integer.parseInt(data[8]),
                    Double.parseDouble(data[9]), Double.parseDouble(data[10]),
                    Double.parseDouble(data[11]), Double.parseDouble(data[12]),
                    Double.parseDouble(data[13])
            );

            orders.add(o);
        }

        reader.close();
        return orders;
    }
}
