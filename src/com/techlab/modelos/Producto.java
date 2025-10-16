// Dentro del paquete com.techlab.modelos
package com.techlab.modelos;

public class Producto {
  private static int contadorId = 0; // Para generar IDs autoincrementales
  private int id;
  private String nombre;
  private double precio;
  private int stock;

  // Constructor
  public Producto(String nombre, double precio, int stock) {
    this.id = ++contadorId; // Aumenta el contador y asigna el nuevo valor
    this.nombre = nombre;
    this.precio = precio;
    this.stock = stock;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public double getPrecio() {
    return precio;
  }

  public int getStock() {
    return stock;
  }

  // Setters
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }


  @Override
  public String toString() {
    return "ID: " + id + ", Nombre: '" + nombre + "', Precio: $" + precio + ", Stock: " + stock;
  }
}