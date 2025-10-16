// Paquete: com.techlab.servicios
package com.techlab.servicios;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.modelos.LineaPedido;
import com.techlab.modelos.Pedido;
import com.techlab.modelos.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoService {
  private List<Pedido> pedidosRealizados;
  private ProductoService productoService; // Dependencia para interactuar con el inventario

  public PedidoService(ProductoService productoService) {
    this.productoService = productoService;
    this.pedidosRealizados = new ArrayList<>();
  }

  /**
   * Proceso principal para crear un pedido.
   * Verifica el stock y lanza una excepción si no es suficiente.
   * @param idProducto El ID del producto a agregar.
   * @param cantidad La cantidad deseada.
   * @return Un objeto LineaPedido si la operación es exitosa.
   * @throws StockInsuficienteException si la cantidad supera el stock.
   */
  public LineaPedido crearLineaPedido(int idProducto, int cantidad) throws StockInsuficienteException {
    Optional<Producto> productoOpt = productoService.buscarProductoPorId(idProducto);

    if (productoOpt.isEmpty()) {

      throw new IllegalArgumentException("❌ Producto con ID " + idProducto + " no encontrado.");
    }

    Producto producto = productoOpt.get();

    if (producto.getStock() < cantidad) {

      throw new StockInsuficienteException("Stock insuficiente para '" + producto.getNombre() +
          "'. Solicitado: " + cantidad + ", Disponible: " + producto.getStock());
    }

    return new LineaPedido(producto, cantidad);
  }

  /**
   * Confirma el pedido, descuenta el stock y lo guarda.
   * @param pedido El pedido a confirmar.
   */
  public void confirmarPedido(Pedido pedido) {
    for (LineaPedido linea : pedido.getLineas()) {
      productoService.reducirStock(linea.getProducto().getId(), linea.getCantidad());
    }
    pedidosRealizados.add(pedido);
    System.out.println("✅ Pedido #" + pedido.getId() + " confirmado y stock actualizado.");
  }

  /**
   * Muestra todos los pedidos que han sido confirmados.
   */
  public void listarPedidos() {
    if (pedidosRealizados.isEmpty()) {
      System.out.println("ℹ️ Aún no se han realizado pedidos.");
      return;
    }
    System.out.println("\n--- HISTORIAL DE PEDIDOS ---");
    pedidosRealizados.forEach(System.out::println);
    System.out.println("----------------------------\n");
  }
}