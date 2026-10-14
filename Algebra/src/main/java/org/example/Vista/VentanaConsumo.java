package org.example.Vista;

import org.example.Servicio.AnalizadorConsumo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaConsumo extends JFrame {

    private JPanel panelRecibos;
    private JTextArea resultadoArea;
    private List<JTextField[]> camposRecibos = new ArrayList<>();

    public VentanaConsumo() {

        setTitle("Análisis Inteligente de Facturación");
        setSize(850, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contenedor.setBackground(new Color(245, 250, 255));

        JLabel titulo = new JLabel("Análisis de Consumo con Estimación de Cargo Fijo");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcion = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Ingrese las UNIDADES consumidas en cada periodo y el TOTAL facturado.<br>"
                        + "El sistema estimará el precio real por unidad y el cargo fijo mensual."
                        + "</div></html>"
        );
        descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(descripcion);
        contenedor.add(Box.createVerticalStrut(20));

        panelRecibos = new JPanel();
        panelRecibos.setLayout(new BoxLayout(panelRecibos, BoxLayout.Y_AXIS));
        panelRecibos.setBackground(new Color(245, 250, 255));

        JScrollPane scrollRecibos = new JScrollPane(panelRecibos);
        scrollRecibos.setPreferredSize(new Dimension(800, 280));

        JButton btnAgregar = new JButton("Agregar Periodo");
        btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregar.addActionListener(e -> agregarPeriodo());

        JButton btnAnalizar = new JButton("Analizar Facturación");
        btnAnalizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAnalizar.addActionListener(e -> analizar());

        resultadoArea = new JTextArea(12, 60);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultadoArea.setBorder(BorderFactory.createTitledBorder("Resultado del Análisis"));

        contenedor.add(scrollRecibos);
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(btnAgregar);
        contenedor.add(Box.createVerticalStrut(10));
        contenedor.add(btnAnalizar);
        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(new JScrollPane(resultadoArea));

        add(contenedor);

        // Agregar 3 periodos iniciales
        agregarPeriodo();
        agregarPeriodo();
        agregarPeriodo();
    }

    private void agregarPeriodo() {

        JPanel periodo = new JPanel();
        periodo.setBorder(BorderFactory.createTitledBorder(
                "Periodo " + (camposRecibos.size() + 1)));

        JTextField energia = new JTextField(6);
        JTextField agua = new JTextField(6);
        JTextField total = new JTextField(8);

        // Tooltips explicativos
        energia.setToolTipText("Ejemplo: 350 (kWh consumidos)");
        agua.setToolTipText("Ejemplo: 20 (m³ consumidos)");
        total.setToolTipText("Ejemplo: 12500 (monto total del recibo)");

        periodo.add(new JLabel("Energía (unidades consumidas):"));
        periodo.add(energia);

        periodo.add(new JLabel("Agua (unidades consumidas):"));
        periodo.add(agua);

        periodo.add(new JLabel("Total facturado del mes:"));
        periodo.add(total);

        panelRecibos.add(periodo);
        panelRecibos.revalidate();

        camposRecibos.add(new JTextField[]{energia, agua, total});
    }

    private void analizar() {

        try {

            int n = camposRecibos.size();

            if (n < 3) {
                resultadoArea.setText(
                        "⚠ Se requieren al menos 3 periodos para estimar el cargo fijo.");
                return;
            }

            double[][] proporciones = new double[n][2];
            double[] totales = new double[n];

            for (int i = 0; i < n; i++) {

                JTextField[] campos = camposRecibos.get(i);

                proporciones[i][0] =
                        Double.parseDouble(campos[0].getText());

                proporciones[i][1] =
                        Double.parseDouble(campos[1].getText());

                totales[i] =
                        Double.parseDouble(campos[2].getText());
            }

            AnalizadorConsumo analizador =
                    new AnalizadorConsumo();

            String resultado =
                    analizador.analizar(proporciones, totales);

            resultadoArea.setText(resultado);

        } catch (Exception ex) {
            resultadoArea.setText(
                    "⚠ Verifique que todos los valores ingresados sean numéricos válidos.");
        }
    }
}
