package com.techlab.main;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.modelos.Pedido;
import com.techlab.modelos.LineaPedido;
import com.techlab.modelos.Producto;
import com.techlab.servicios.PedidoService;
import com.techlab.servicios.ProductoService;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    // Inicialización de servicios y herramientas
    Scanner scanner = new Scanner(System.in);
    ProductoService productoService = new ProductoService();
    PedidoService pedidoService = new PedidoService(productoService); // Inyeccion de dependencia
    int opcion = 0;

    // --- DATOS DE PRUEBA PARA INICIAR EL PROGRAMA ---
    productoService.agregarProducto(new Producto("Café Premium 1kg", 12500.50, 25));
    productoService.agregarProducto(new Producto("Teclado Mecánico RGB", 85000.0, 10));
    productoService.agregarProducto(new Producto("Mouse Inalámbrico", 34999.99, 30));
    productoService.agregarProducto(new Producto("Monitor Curvo 27\"", 275000.0, 5));
    // --------------------------------------------------

    // Bucle principal del menú interactivo
    while (opcion != 7) {
      mostrarMenu();
      try {
        System.out.print("Elija una opción: ");
        opcion = scanner.nextInt();
        scanner.nextLine(); // Limpieza de buffer

        switch (opcion) {
          case 1: // AGREGAR PRODUCTO
            System.out.println("\n--- Agregar Nuevo Producto ---");
            System.out.print("Nombre del producto: ");
            String nombre = scanner.nextLine();
            System.out.print("Precio: ");
            double precio = scanner.nextDouble();
            System.out.print("Stock inicial: ");
            int stock = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            productoService.agregarProducto(new Producto(nombre, precio, stock));
            break;

          case 2: // LISTAR PRODUCTOS
            System.out.println("\n--- Inventario Actual ---");
            productoService.listarProductos();
            break;

          case 3: // BUSCAR Y ACTUALIZAR PRODUCTO
            System.out.print("\nIngrese el ID del producto a buscar/actualizar: ");
            int idBuscar = scanner.nextInt();
            scanner.nextLine(); // Limpiar

            Optional<Producto> productoEncontrado = productoService.buscarProductoPorId(idBuscar);


            productoEncontrado.ifPresentOrElse(
                producto -> {
                  System.out.println("✅ Producto encontrado: " + producto);
                  System.out.print("¿Desea actualizarlo? (S/N): ");
                  String respuesta = scanner.nextLine();
                  if (respuesta.equalsIgnoreCase("S")) {
                    System.out.print("Nuevo precio (actual: " + producto.getPrecio() + "): ");
                    double nuevoPrecio = scanner.nextDouble();
                    System.out.print("Nuevo stock (actual: " + producto.getStock() + "): ");
                    int nuevoStock = scanner.nextInt();
                    scanner.nextLine(); // Limpiar
                    productoService.actualizarProducto(producto.getId(), nuevoPrecio, nuevoStock);
                  }
                },
                () -> System.out.println("❌ No se encontró ningún producto con el ID " + idBuscar)
            );
            break;

          case 4: // ELIMINAR PRODUCTO
            System.out.print("\nIngrese el ID del producto a eliminar: ");
            int idEliminar = scanner.nextInt();
            scanner.nextLine();
            productoService.eliminarProducto(idEliminar);
            break;

          case 5: // CREAR UN PEDIDO
            Pedido nuevoPedido = new Pedido();
            System.out.println("\n--- Creando Nuevo Pedido #" + nuevoPedido.getId() + " ---");
            productoService.listarProductos(); // Mostrar los productos para facilitar la elección

            while (true) {
              try {
                System.out.print("Ingrese el ID del producto a agregar (o 0 para terminar): ");
                int idProductoPedido = scanner.nextInt();
                scanner.nextLine(); // LIMPIEZA

                if (idProductoPedido == 0) {
                  break;
                }
                System.out.print("Ingrese la cantidad: ");
                int cantidadPedido = scanner.nextInt();
                scanner.nextLine(); // Limpia después de la cantidad

                LineaPedido nuevaLinea = pedidoService.crearLineaPedido(idProductoPedido, cantidadPedido);
                nuevoPedido.agregarLinea(nuevaLinea);
                System.out.println("👍 Producto '" + nuevaLinea.getProducto().getNombre() + "' agregado al pedido.");

              } catch (StockInsuficienteException | IllegalArgumentException e) {
                System.out.println("❗ Error al agregar: " + e.getMessage());
              } catch (InputMismatchException e) {
                System.out.println("❗ Error: Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpieza caso de error de entrada
              }
            }

            if (nuevoPedido.getLineas().isEmpty()) {
              System.out.println("ℹ️ Pedido cancelado ya que no se agregaron productos.");
            } else {
              System.out.println("\n--- RESUMEN DEL PEDIDO ---");
              System.out.println(nuevoPedido);



              System.out.print("¿Desea confirmar el pedido? (S/N): ");
              String confirmar = scanner.nextLine();

              if (confirmar.equalsIgnoreCase("S")) {
                pedidoService.confirmarPedido(nuevoPedido);
              } else {
                System.out.println("ℹ️ Pedido cancelado por el usuario.");
              }
            }
            break;

          case 6: // LISTAR PEDIDOS REALIZADOS
            pedidoService.listarPedidos();
            break;

          case 7: // SALIR
            System.out.println("\n👋 ¡Hasta luego! Saliendo del sistema...");
            break;

          default:
            System.out.println("❌ Opción no válida. Por favor, elija una opción del 1 al 7.");
        }
      } catch (InputMismatchException e) {
        System.out.println("❌ Error: Debe ingresar un número para seleccionar una opción. Intente de nuevo.");
        scanner.nextLine();
        opcion = 0; // Reset
      }
      System.out.println();
    }

    scanner.close(); // Buena práctica: cerrar el scanner al finalizar el programa
  }

  /**
   * Muestra el menú de opciones principal en la consola.
   */
  private static void mostrarMenu() {
    System.out.println("=================================== SISTEMA DE GESTIÓN - TECHLAB ==================================");
    System.out.println("1) Agregar producto");
    System.out.println("2) Listar productos");
    System.out.println("3) Buscar/Actualizar producto");
    System.out.println("4) Eliminar producto");
    System.out.println("5) Crear un pedido");
    System.out.println("6) Listar pedidos");
    System.out.println("7) Salir");
    System.out.println("==================================================================================================");
  }
}