package org.example.Modelo;

public class Vector {

    private final int dimension;
    private final double[] datos;

    public Vector(double[] datos) {
        this.dimension = datos.length;
        this.datos = new double[dimension];

        for (int i = 0; i < dimension; i++) {
            this.datos[i] = datos[i];
        }
    }

    public int getDimension() {
        return dimension;
    }

    public double getElemento(int indice) {
        return datos[indice];
    }

    public void imprimir() {
        for (int i = 0; i < dimension; i++) {
            System.out.println(datos[i]);
        }
    }
}
