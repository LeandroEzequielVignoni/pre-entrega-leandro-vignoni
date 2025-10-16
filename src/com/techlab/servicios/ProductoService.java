// Dentro del paquete com.techlab.servicios
package com.techlab.servicios;

import com.techlab.modelos.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoService {
  private List<Producto> inventario = new ArrayList<>();

  // 1. Agregar un producto
  public void agregarProducto(Producto producto) {
    inventario.add(producto);
    System.out.println("✅ Producto '" + producto.getNombre() + "' agregado correctamente.");
  }

  // 2. Listar todos los productos
  public void listarProductos() {
    if (inventario.isEmpty()) {
      System.out.println("ℹ️ No hay productos en el inventario.");
      return;
    }
    System.out.println("--- LISTA DE PRODUCTOS ---");
    for (Producto p : inventario) {
      System.out.println(p.toString()); // Usamos el método toString() que definimos
    }
    System.out.println("--------------------------");
  }

  // 3. Buscar un producto por ID
  public Optional<Producto> buscarProductoPorId(int id) {

    return inventario.stream()
        .filter(p -> p.getId() == id)
        .findFirst();
  }

  // 4. Actualizar un producto
  public void actualizarProducto(int id, double nuevoPrecio, int nuevoStock) {
    Optional<Producto> productoOpt = buscarProductoPorId(id);

    if (productoOpt.isPresent()) {
      Producto producto = productoOpt.get();
      producto.setPrecio(nuevoPrecio);
      producto.setStock(nuevoStock);
      System.out.println("✅ Producto actualizado: " + producto.getNombre());
    } else {
      System.out.println("❌ Error: No se encontró un producto con el ID " + id);
    }
  }

  // 5. Eliminar un producto
  public void eliminarProducto(int id) {
    boolean removido = inventario.removeIf(p -> p.getId() == id);
    if (removido) {
      System.out.println("✅ Producto con ID " + id + " eliminado.");
    } else {
      System.out.println("❌ Error: No se encontró un producto con el ID " + id + " para eliminar.");
    }
  }

  // Dentro de la clase ProductoService
  public void reducirStock(int idProducto, int cantidad) {
    Optional<Producto> productoOpt = buscarProductoPorId(idProducto);
    if (productoOpt.isPresent()) {
      Producto producto = productoOpt.get();
      producto.setStock(producto.getStock() - cantidad);
    }
  }
}