// Paquete: com.techlab.modelos
package com.techlab.modelos;

public class LineaPedido {
  private Producto producto;
  private int cantidad;

  public LineaPedido(Producto producto, int cantidad) {
    this.producto = producto;
    this.cantidad = cantidad;
  }

  public Producto getProducto() {
    return producto;
  }

  public int getCantidad() {
    return cantidad;
  }

  public double getSubtotal() {
    return producto.getPrecio() * cantidad;
  }

  @Override
  public String toString() {
    return String.format(" -> Producto: %-20s | Cantidad: %d | Subtotal: $%.2f",
        producto.getNombre(), cantidad, getSubtotal());
  }
}