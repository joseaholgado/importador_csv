package app;
import clases.Order;
import clases.OrderImporter;
import clases.Menu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.List;


public class Main {
    /**
     * Programa principal que:
     * - Lee un archivo CSV con datos
     * - Convierte cada registro en un objeto Order
     * - Muestra un menú interactivo en la consola para visualizar y ordenar los pedidos
     * - Tiene en cuenta que el usuario elija solo csv y si elige otro que no es
     */

    public static void main(String[] args) throws Exception {
        String newRute = null;
        File selectedFile = null;
        boolean validFile=false;


        do {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona el archivo CSV");

            //Es lo que limita para ficheros csv y evitar ficheros maliciosos
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showOpenDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();

                    // Verificación adicional por seguridad (por si el usuario escribe manualmente otro tipo)
                    if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
                        System.out.println("Solo se permiten archivos con extensión .csv");
                        System.out.println("\nVuelva a intertar con archivo CSV");
                    } else {
                        validFile = true;
                    }
                } else {
                    System.out.println("No se seleccionó ningún archivo. Programa cancelado.");
                    return;
                }
        } while (!validFile);

        String filePath = selectedFile.getAbsolutePath();
        System.out.println("Has seleccionado el archivo " + selectedFile.getName() + "\n");

        OrderImporter importer = new OrderImporter();
        List<Order> orders = importer.importCSV(filePath);

        do {
            Menu menu = new Menu(orders, importer);
            newRute = menu.showMenu();

            if (newRute != null) {
                selectedFile = new File(newRute);
                System.out.println("Cargando nuevo archivo " + selectedFile.getName());
                orders = importer.importCSV(newRute);
            }
        } while (newRute != null);

        System.out.println("\nArchivo cargado correctamente: " + filePath);
    }
}


