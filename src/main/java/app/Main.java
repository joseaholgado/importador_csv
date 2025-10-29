package app;
import clases.Order;
import clases.OrderImporter;

import java.io.*;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        String file = "src/main/resources/RegistroVentas1.csv";

        OrderImporter importer = new OrderImporter();
        List<Order> orders = importer.importCSV(file);

        for (Order o : orders) {
            System.out.println(o);
        }
    }
}
