package app;
import clases.Order;
import clases.OrderImporter;
import clases.Menu;

import java.io.*;
import java.util.List;


public class Main {
    /**
     * Programa principal que:
     * - Lee un archivo CSV con datos de ventas
     * - Convierte cada registro en un objeto Order
     * - Muestra un menú interactivo en la consola para visualizar y ordenar los pedidos
     */

    public static void main(String[] args) throws Exception {

        String file = "src/main/resources/RegistroVentas1.csv";

        OrderImporter importer = new OrderImporter();
        List<Order> orders = importer.importCSV(file);

        Menu menu = new Menu(orders);
        menu.mostrarMenu();

        //System.out.println("\nÉxito");

    }
}
