// Paquete: com.techlab.excepciones
package com.techlab.excepciones;

/**
 * Excepción que se lanza cuando se intenta realizar una operación
 * (como crear un pedido) y no hay suficiente stock de un producto.
 */
public class StockInsuficienteException extends Exception {

  public StockInsuficienteException(String message) {
    // Llama al constructor de la clase padre (Exception) para establecer el mensaje de error.
    super(message);
  }
}