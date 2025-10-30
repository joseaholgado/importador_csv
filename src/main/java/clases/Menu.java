package clases;

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

    public Menu(List<Order> orders) {
        this.orders = orders;
    }

    public void mostrarMenu() {
        int opcion = 0;
        Scanner sc = new Scanner(System.in);


        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Mostrar todos los pedidos");
            System.out.println("2. Ordenar por OrderID");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = sc.nextInt();  // intenta leer un número


            switch(opcion) {
                case 1:
                    mostrarPedidos();
                    break;
                case 2:
                    ordenarPorOrderId();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número (no letras).");
                sc.nextLine();
            }
            
        } while(opcion != 3);

    }


    private void mostrarPedidos() {
        orders.sort(Comparator.comparing(Order::getId));
        for(Order o : orders) {
            System.out.println(o);
        }
    }

    private void ordenarPorOrderId() {
        orders.sort(Comparator.comparing(Order::getOrderId, Comparator.nullsLast(String::compareTo)));
        System.out.println("Pedidos ordenados por OrderID");
        for(Order o : orders) {
            System.out.println(o);
        }
    }

}
