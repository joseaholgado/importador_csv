package clases;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import clases.OrderDao;
import java.nio.file.Path;

/**
 * Clase que gestiona un menú interactivo en consola
 * para visualizar y ordenar una lista de pedidos (Order).
 *
 * Permite al usuario:
 * - Mostrar todos los pedidos en su orden original (por ID incremental).
 * - Ordenar los pedidos por el campo OrderID.
 * - Cambiar el archivo CSV, mostrar el resumen desde la base de datos
 * - Exportar un nuevo CSV ordenado.
 * - Salir del programa.
 */

public class Menu {
    private List<Order> orders;
    private OrderImporter importer;
    private boolean salir = false;
    private final OrderDao dao;
    private final Path out;


    public Menu(List<Order> orders, OrderImporter importer, OrderDao dao, Path out) {
        this.orders = orders;
        this.importer = importer;
        this.dao = dao;   // NUEVO
        this.out = out;   // NUEVO
    }

    /**
     * Muestra el menú interactivo en consola
     * y gestiona la selección del usuario.
     */

    public String showMenu() {
        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        String newRute = null;



        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Mostrar todos los pedidos");
            System.out.println("2. Ordenar por OrderID");
            System.out.println("3. Cambiar archivo CSV");
            System.out.println("4. Resumen por columnas (BD)");
            System.out.println("5. Exportar CSV ordenado (BD)");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = sc.nextInt();  // intenta leer un número
                sc.nextLine();

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
                        showDbSummary();
                        break;
                    case 5:
                        exportFromDb();
                        break;
                    case 6:
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

        } while (!salir);

        return null;
    }

    /**
     * Muestra los pedidos en su orden original (por ID incremental).
     */
    private void showOrders() {
        orders.sort(Comparator.comparing(Order::getId));
        for (Order o : orders) {
            System.out.println(o);
        }
    }

    /**
     * Ordena los pedidos por OrderID
     */
    private void sortByOrderId() {
        orders.sort(Comparator.comparing(Order::getOrderId, Comparator.nullsLast(String::compareTo)));
        System.out.println("Pedidos ordenados por OrderID");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    /**
     * Permite al usuario seleccionar un nuevo archivo CSV.
     * Incluye validación para aceptar solo ficheros .csv.
     */

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

    /**
     * Muestra el resumen de pedidos agrupado por:
     * Región, País, Tipo de producto, Canal de venta y Prioridad.
     * (El resumen se obtiene directamente desde la base de datos)
     */

    private void showDbSummary() {
        try {
            clases.SummaryService.printDbSummary(dao);
        } catch (Exception e) {
            System.out.println("Error mostrando resumen: " + e.getMessage());
        }
    }

    /**
     * Exporta los datos actuales de la base de datos
     * a un nuevo archivo CSV, ordenados por OrderID y
     * con fechas formateadas en dd/MM/yyyy.
     */

    private void exportFromDb() {
        try {
            var sorted = dao.findAllOrderByOrderId();
            clases.CsvExporter.exportSortedByOrderId(sorted, out);
            System.out.println("Exportado CSV en: " + out.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error exportando CSV: " + e.getMessage());
        }
    }
}



