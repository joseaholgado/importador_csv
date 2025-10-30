package clases;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona un menú interactivo en consola
 * para visualizar y ordenar una lista de pedidos (Order).
 *
 * Permite al usuario:
 * - Mostrar todos los pedidos en su orden original (por ID incremental).
 * - Ordenar los pedidos por el campo OrderID.
 * - Salir del programa.
 */

public class Menu {
    private List<Order> orders;
    private OrderImporter importer;
    private Boolean salir = false;


    public Menu(List<Order> orders, OrderImporter importer) {
        this.orders = orders;
        this.importer = importer;
    }

    public String showMenu() {
        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        String newRute = null;


        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Mostrar todos los pedidos");
            System.out.println("2. Ordenar por OrderID");
            System.out.println("3. Cambiar archivo CSV");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = sc.nextInt();  // intenta leer un número


                switch (opcion) {
                    case 1:
                        showOrders();
                        break;
                    case 2:
                        sortByOrderId();
                        break;
                    case 3:
                        newRute = changeFile();
                        if (newRute != null) return newRute;
                        break;
                    case 4:
                        System.out.println("Saliendo...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número (no letras).");
                sc.nextLine();
            }

        } while (opcion != 4);

        return null;
    }


    private void showOrders() {
        orders.sort(Comparator.comparing(Order::getId));
        for (Order o : orders) {
            System.out.println(o);
        }
    }

    private void sortByOrderId() {
        orders.sort(Comparator.comparing(Order::getOrderId, Comparator.nullsLast(String::compareTo)));
        System.out.println("Pedidos ordenados por OrderID");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private String changeFile() {
        Boolean validFile = false;
        File selectedFile = null;

        do {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona el nuevo archivo CSV");

            //Es lo que limita para ficheros csv y evitar ficheros maliciosos
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showOpenDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();

                // Verificación adicional por seguridad (por si el usuario escribe manualmente otro tipo)
                if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
                    System.out.println("Solo se permiten archivos con extensión .csv");
                } else {
                    validFile = true;
                    System.out.println("Has seleccionado el archivo " + selectedFile.getName() + "\n");
                }
            } else {
                System.out.println("No se seleccionó ningún archivo.");
                return null;
            }

        } while (!validFile);

        return selectedFile.getAbsolutePath();
    }
}



