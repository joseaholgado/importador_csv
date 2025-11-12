package app;
import clases.Order;
import clases.OrderImporter;
import clases.Menu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.List;

// Importamos la BD, resumen y exportación
import clases.Database;
import clases.OrderDao;
import clases.CsvExporter;
import clases.SummaryService;
import java.nio.file.Path;

public class Main {
    /**
     * Programa principal que:
     * - Lee un archivo CSV con datos
     * - Convierte cada registro en un objeto Order
     * - Muestra un menú interactivo en la consola para visualizar y ordenar los pedidos
     * - Tiene en cuenta que el usuario elija solo csv y si elige otro que no es
     * - Almacena en la base de datos y generar el resumen y exportación final desde la terminal.
     *
     */

    public static void main(String[] args) throws Exception {
        String newRute = null;
        File selectedFile = null;
        boolean validFile=false;

        try {
            // Inicializa el esquema de BD (si no existe lo crea)
            Database.initSchema();


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

            // Persistimo la BD, hacemos el resumen y exportamos el csv
            OrderDao dao = new OrderDao();

            // Para no duplicr datos. Limpiamos la tabla para no duplicar al volver a cargar csv
            dao.clearAll();

            dao.insertAll(orders);  // guarda en SQLite

            // Mostrar resumen desde BD
            //SummaryService.printDbSummary(dao);

            // Exportar CSV ordenado por Order ID con fechas dd/MM/yyyy
            Path out = Path.of("./salida/Exportacion_CSV.csv");
            var sortedFromDb = dao.findAllOrderByOrderId();
            CsvExporter.exportSortedByOrderId(sortedFromDb, out);
            //System.out.println("\nCSV exportado en: salida/" + out.getFileName());

            do {
                Menu menu = new Menu(orders, importer, dao, out);
                newRute = menu.showMenu();

                if (newRute != null) {
                    selectedFile = new File(newRute);
                    System.out.println("Cargando nuevo archivo " + selectedFile.getName());
                    orders = importer.importCSV(newRute);

                    dao.clearAll();//Limpiamos tabla

                    // Guarda el nuevo CSV en BD
                    dao.insertAll(orders);
                    //SummaryService.printDbSummary(dao); Muestra resumen del Csv al cambiar en el menú
                    var sorted2 = dao.findAllOrderByOrderId();
                    CsvExporter.exportSortedByOrderId(sorted2, out);
                    //System.out.println("\nCSV exportado en: " + out.toAbsolutePath());
                }
            } while (newRute != null);

        } catch (java.sql.SQLException e) { //  manejo de errores de BD
            System.err.println("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}


