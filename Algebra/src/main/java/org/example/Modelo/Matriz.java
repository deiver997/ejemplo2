package org.example.Modelo;

public class Matriz {

    private final int filas;
    private final int columnas;
    private final double[][] datos;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.datos = new double[filas][columnas];
    }

    public Matriz(double[][] datos) {
        this.filas = datos.length;
        this.columnas = datos[0].length;
        this.datos = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.datos[i][j] = datos[i][j];
            }
        }
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public double getElemento(int fila, int columna) {
        return datos[fila][columna];
    }

    public void setElemento(int fila, int columna, double valor) {
        datos[fila][columna] = valor;
    }

    public void imprimir() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(datos[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void imprimirFormateada() {

        for (int i = 0; i < filas; i++) {
            System.out.print("| ");

            for (int j = 0; j < columnas; j++) {
                System.out.printf("%8.3f ", datos[i][j]);
            }

            System.out.println("|");
        }
    }

}
