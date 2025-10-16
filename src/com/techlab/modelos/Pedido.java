// Paquete: com.techlab.modelos
package com.techlab.modelos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
  private static int contadorId = 0;
  private int id;
  private List<LineaPedido> lineas;

  public Pedido() {
    this.id = ++contadorId;
    this.lineas = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public List<LineaPedido> getLineas() {
    return lineas;
  }

  public void agregarLinea(LineaPedido linea) {
    this.lineas.add(linea);
  }

  public double calcularTotal() {

    return lineas.stream()
        .mapToDouble(LineaPedido::getSubtotal)
        .sum();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("================ PEDIDO ID: ").append(id).append(" ================\n");
    for (LineaPedido linea : lineas) {
      sb.append(linea.toString()).append("\n");
    }
    sb.append("--------------------------------------------------\n");
    sb.append(String.format("TOTAL DEL PEDIDO: $%.2f\n", calcularTotal()));
    sb.append("==================================================");
    return sb.toString();
  }
}