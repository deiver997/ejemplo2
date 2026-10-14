package org.example.Algebra;

import org.example.Modelo.Matriz;
import org.example.Modelo.Vector;

public class SistemaLineal {

    private Matriz matrizAumentada;

    public SistemaLineal(Matriz A, Vector B) {
        construirMatrizAumentada(A, B);
    }

    private void construirMatrizAumentada(Matriz A, Vector B) {
        int filas = A.getFilas();
        int columnas = A.getColumnas();

        matrizAumentada = new Matriz(filas, columnas + 1);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizAumentada.setElemento(i, j, A.getElemento(i, j));
            }
            matrizAumentada.setElemento(i, columnas, B.getElemento(i));
        }
    }

    // -------------------------
    // OPERACIONES ELEMENTALES
    // -------------------------

    private void intercambiarFilas(int f1, int f2) {
        for (int j = 0; j < matrizAumentada.getColumnas(); j++) {
            double temp = matrizAumentada.getElemento(f1, j);
            matrizAumentada.setElemento(f1, j, matrizAumentada.getElemento(f2, j));
            matrizAumentada.setElemento(f2, j, temp);
        }
    }

    private void multiplicarFila(int fila, double escalar) {
        for (int j = 0; j < matrizAumentada.getColumnas(); j++) {
            double valor = matrizAumentada.getElemento(fila, j);
            matrizAumentada.setElemento(fila, j, valor * escalar);
        }
    }

    private void sumarFilas(int filaDestino, int filaOrigen, double escalar) {
        for (int j = 0; j < matrizAumentada.getColumnas(); j++) {
            double valorDestino = matrizAumentada.getElemento(filaDestino, j);
            double valorOrigen = matrizAumentada.getElemento(filaOrigen, j);
            matrizAumentada.setElemento(filaDestino, j, valorDestino + escalar * valorOrigen);
        }
    }

    // -------------------------
    // GAUSS SIMPLE
    // -------------------------

    public void resolverGaussJordan() {

        int filas = matrizAumentada.getFilas();
        int columnas = matrizAumentada.getColumnas();

        for (int i = 0; i < filas; i++) {

            double pivote = matrizAumentada.getElemento(i, i);

            // 🔹 Si el pivote es 0, intentar intercambiar fila
            if (Math.abs(pivote) < 1e-9) {

                for (int k = i + 1; k < filas; k++) {

                    if (Math.abs(matrizAumentada.getElemento(k, i)) > 1e-9) {
                        intercambiarFilas(i, k);
                        break;
                    }
                }

                pivote = matrizAumentada.getElemento(i, i);
            }

            if (Math.abs(pivote) < 1e-9) {
                continue;
            }

            // 🔹 Normalizar fila pivote
            for (int j = 0; j < columnas; j++) {
                double valor = matrizAumentada.getElemento(i, j);
                matrizAumentada.setElemento(i, j, valor / pivote);
            }

            // 🔹 Hacer ceros en columna
            for (int k = 0; k < filas; k++) {

                if (k != i) {

                    double factor = matrizAumentada.getElemento(k, i);

                    for (int j = 0; j < columnas; j++) {

                        double nuevoValor =
                                matrizAumentada.getElemento(k, j)
                                        - factor * matrizAumentada.getElemento(i, j);

                        matrizAumentada.setElemento(k, j, nuevoValor);
                    }
                }
            }
        }
    }



    public int calcularRangoCoeficientes() {

        int filas = matrizAumentada.getFilas();
        int columnasCoef = matrizAumentada.getColumnas() - 1;

        int rango = 0;

        for (int i = 0; i < filas; i++) {

            boolean filaNoNula = false;

            for (int j = 0; j < columnasCoef; j++) {
                if (Math.abs(matrizAumentada.getElemento(i, j)) > 1e-9) {
                    filaNoNula = true;
                    break;
                }
            }

            if (filaNoNula) {
                rango++;
            }
        }

        return rango;
    }
    public int calcularRangoAumentada() {

        int filas = matrizAumentada.getFilas();
        int columnas = matrizAumentada.getColumnas();

        int rango = 0;

        for (int i = 0; i < filas; i++) {

            boolean filaNoNula = false;

            for (int j = 0; j < columnas; j++) {
                if (Math.abs(matrizAumentada.getElemento(i, j)) > 1e-9) {
                    filaNoNula = true;
                    break;
                }
            }

            if (filaNoNula) {
                rango++;
            }
        }

        return rango;
    }

    public String clasificarSistema() {

        int rangoA = calcularRangoCoeficientes();
        int rangoAB = calcularRangoAumentada();
        int n = matrizAumentada.getColumnas() - 1;

        if (rangoA == rangoAB && rangoA == n) {
            return "Sistema Compatible Determinado (única solución)";
        }

        if (rangoA == rangoAB && rangoA < n) {
            return "Sistema Compatible Indeterminado (infinitas soluciones)";
        }

        return "Sistema Incompatible (sin solución)";
    }




    public void imprimirSistema(String titulo) {

        System.out.println("\n=================================");
        System.out.println(titulo);
        System.out.println("=================================");

        int filas = matrizAumentada.getFilas();
        int columnas = matrizAumentada.getColumnas();

        for (int i = 0; i < filas; i++) {

            System.out.print("| ");

            for (int j = 0; j < columnas - 1; j++) {
                System.out.printf("%10.3f ",
                        matrizAumentada.getElemento(i, j));
            }

            System.out.print(" || ");

            System.out.printf("%10.3f",
                    matrizAumentada.getElemento(i, columnas - 1));

            System.out.println(" |");
        }
    }

    public Vector obtenerSolucion() {

        int filas = matrizAumentada.getFilas();
        int columnas = matrizAumentada.getColumnas();

        double[] solucion = new double[filas];

        for (int i = 0; i < filas; i++) {
            solucion[i] = matrizAumentada.getElemento(i, columnas - 1);
        }

        return new Vector(solucion);
    }

}
