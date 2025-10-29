package app;
import clases.Order;
import clases.OrderImporter;

import java.io.*;
import java.util.List;




public class Main {
    /**
     * Lee un archivo CSV con datos de ventas, los convierte a objetos Order
     * y los imprime en consola.
     */

    public static void main(String[] args) throws Exception {

        String file = "src/main/resources/RegistroVentas1.csv";

        OrderImporter importer = new OrderImporter();
        List<Order> orders = importer.importCSV(file);

        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println("\nÉxito");

    }
}
