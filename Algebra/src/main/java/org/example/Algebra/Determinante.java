package org.example.Algebra;

import org.example.Modelo.Matriz;

public class Determinante {

    public static double calcular(Matriz A) {

        if (A.getFilas() != A.getColumnas()) {
            throw new IllegalArgumentException("La matriz debe ser cuadrada.");
        }

        int n = A.getFilas();

        // 1x1
        if (n == 1) {
            return A.getElemento(0, 0);
        }

        // 2x2
        if (n == 2) {
            return determinante2x2(A);
        }

        // 3x3
        if (n == 3) {
            return determinante3x3(A);
        }

        // General (cofactores)
        return determinanteCofactores(A);
    }

    // -------------------------
    // 2x2
    // -------------------------

    private static double determinante2x2(Matriz A) {

        return A.getElemento(0,0) * A.getElemento(1,1)
                - A.getElemento(0,1) * A.getElemento(1,0);
    }

    // -------------------------
    // 3x3 (Sarrus)
    // -------------------------

    private static double determinante3x3(Matriz A) {

        double a = A.getElemento(0,0);
        double b = A.getElemento(0,1);
        double c = A.getElemento(0,2);

        double d = A.getElemento(1,0);
        double e = A.getElemento(1,1);
        double f = A.getElemento(1,2);

        double g = A.getElemento(2,0);
        double h = A.getElemento(2,1);
        double i = A.getElemento(2,2);

        return (a*e*i + b*f*g + c*d*h)
                - (c*e*g + b*d*i + a*f*h);
    }

    // -------------------------
    // Método general (cofactores)
    // -------------------------

    private static double determinanteCofactores(Matriz A) {

        int n = A.getFilas();
        double determinante = 0;

        for (int j = 0; j < n; j++) {

            Matriz menor = construirMenor(A, 0, j);
            double cofactor = Math.pow(-1, j) * A.getElemento(0, j);

            determinante += cofactor * calcular(menor);
        }

        return determinante;
    }

    // Construye la matriz menor eliminando fila y columna
    private static Matriz construirMenor(Matriz A, int filaEliminar, int columnaEliminar) {

        int n = A.getFilas();
        Matriz menor = new Matriz(n - 1, n - 1);

        int filaNueva = 0;

        for (int i = 0; i < n; i++) {

            if (i == filaEliminar) continue;

            int columnaNueva = 0;

            for (int j = 0; j < n; j++) {

                if (j == columnaEliminar) continue;

                menor.setElemento(filaNueva, columnaNueva,
                        A.getElemento(i, j));

                columnaNueva++;
            }

            filaNueva++;
        }

        return menor;
    }
}
