package org.example.Servicio;

import org.example.Modelo.Matriz;
import org.example.Modelo.Vector;
import org.example.Algebra.SistemaLineal;
import org.example.Algebra.Determinante;

public class AnalizadorConsumo {

    public String analizar(double[][] proporciones,
                           double[] totales) {

        if (proporciones.length < 3) {
            return "⚠ Se requieren al menos 3 periodos para calcular el cargo fijo.";
        }

        int n = proporciones.length;

        // =============================
        // Construir matriz con cargo fijo
        // =============================

        double[][] matrizExtendida = new double[n][3];

        for (int i = 0; i < n; i++) {

            if (proporciones[i][0] <= 0 ||
                    proporciones[i][1] <= 0 ||
                    totales[i] <= 0) {

                return "⚠ Todos los valores deben ser mayores que cero.";
            }

            matrizExtendida[i][0] = proporciones[i][0]; // Energía
            matrizExtendida[i][1] = proporciones[i][1]; // Agua
            matrizExtendida[i][2] = 1;                  // Cargo fijo
        }

        Matriz A = new Matriz(matrizExtendida);
        Vector B = new Vector(totales);

        double det = Determinante.calcular(A);

        if (Math.abs(det) < 1e-6) {
            return "⚠ Los periodos ingresados son demasiado similares.\n"
                    + "No es posible realizar un análisis confiable.";
        }

        SistemaLineal sistema = new SistemaLineal(A, B);

        // 🔎 Imprimir antes de resolver
        sistema.imprimirSistema("ANTES DE GAUSS-JORDAN");

        sistema.resolverGaussJordan();

        // 🔎 Imprimir después de resolver
        sistema.imprimirSistema("DESPUÉS DE GAUSS-JORDAN");

        Vector solucion = sistema.obtenerSolucion();

        double precioEnergia = solucion.getElemento(0);
        double precioAgua = solucion.getElemento(1);
        double cargoFijo = solucion.getElemento(2);

        if (precioEnergia < 0 || precioAgua < 0 || cargoFijo < 0) {
            return "⚠ Los datos ingresados no son coherentes.\n"
                    + "Verifique proporciones y totales.";
        }

        StringBuilder resultado = new StringBuilder();

        resultado.append("ANÁLISIS ESTRUCTURAL DE FACTURACIÓN\n");
        resultado.append("===========================================\n\n");

        resultado.append("Precio por unidad de Energía: ")
                .append(String.format("%,.2f", precioEnergia))
                .append("\n");

        resultado.append("Precio por unidad de Agua: ")
                .append(String.format("%,.2f", precioAgua))
                .append("\n");

        resultado.append("Cargo fijo mensual estimado: ")
                .append(String.format("%,.2f", cargoFijo))
                .append("\n\n");

        resultado.append("-------------------------------------------\n");
        resultado.append("DESGLOSE POR PERIODO\n");
        resultado.append("-------------------------------------------\n\n");

        for (int i = 0; i < n; i++) {

            double energiaPeriodo = proporciones[i][0] * precioEnergia;
            double aguaPeriodo = proporciones[i][1] * precioAgua;

            resultado.append("Periodo ").append(i + 1).append(":\n");
            resultado.append("  Energía: ")
                    .append(String.format("%,.2f", energiaPeriodo))
                    .append("\n");
            resultado.append("  Agua: ")
                    .append(String.format("%,.2f", aguaPeriodo))
                    .append("\n");
            resultado.append("  Cargo fijo: ")
                    .append(String.format("%,.2f", cargoFijo))
                    .append("\n\n");
        }

        // =============================
        // ANÁLISIS DE VARIACIONES
        // =============================

        resultado.append("-------------------------------------------\n");
        resultado.append("ANÁLISIS DE VARIACIÓN ENTRE MESES\n");
        resultado.append("-------------------------------------------\n\n");

        for (int i = 1; i < n; i++) {

            double deltaEnergia = proporciones[i][0] - proporciones[i - 1][0];
            double deltaAgua = proporciones[i][1] - proporciones[i - 1][1];

            double porcentajeEnergia =
                    (deltaEnergia / proporciones[i - 1][0]) * 100;

            double porcentajeAgua =
                    (deltaAgua / proporciones[i - 1][1]) * 100;

            double impactoEnergia = deltaEnergia * precioEnergia;
            double impactoAgua = deltaAgua * precioAgua;

            resultado.append("Comparación Periodo ")
                    .append(i)
                    .append(" → ")
                    .append(i + 1)
                    .append(":\n");

            resultado.append("  Variación Energía: ")
                    .append(String.format("%,.2f", deltaEnergia))
                    .append(" unidades (")
                    .append(String.format("%,.2f", porcentajeEnergia))
                    .append("%)\n");

            resultado.append("  Variación Agua: ")
                    .append(String.format("%,.2f", deltaAgua))
                    .append(" unidades (")
                    .append(String.format("%,.2f", porcentajeAgua))
                    .append("%)\n");

            resultado.append("  Impacto económico Energía: ")
                    .append(String.format("%,.2f", impactoEnergia))
                    .append("\n");

            resultado.append("  Impacto económico Agua: ")
                    .append(String.format("%,.2f", impactoAgua))
                    .append("\n\n");
        }

        return resultado.toString();
    }
}
