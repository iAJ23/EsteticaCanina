package com.example.Gui;

import com.example.Logic.Controller;
import com.example.Logic.Mascota;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarDatos extends JFrame {

    private final Controller controller = new Controller();
    private VerDatos verDatos;

    private JPanel modificarDatos;
    private JTextField txtNombre;
    private JTextField txtRaza;
    private JTextField txtColor;
    private JTextField txtCel;
    private JTextArea txtObs;
    private JComboBox<String> cbAlerg;
    private JComboBox<String> cbAtt;
    private JButton limpiarButton;
    private JButton guardarButton;
    private JButton regresarButton;
    private JTextField txtNombreDueno;
    private JLabel nombreDueno;

    Mascota masco;

    public ModificarDatos(int num_cliente, VerDatos verDatos){
        this.verDatos = verDatos;
        cargarDatos(num_cliente);

        setContentPane(modificarDatos);
        setTitle("Estética Canina - Datos");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNombre.setText("");
                txtRaza.setText("");
                txtColor.setText("");
                txtCel.setText("");
                txtObs.setText("");
                txtNombreDueno.setText("");

                cbAtt.setSelectedIndex(0);
                cbAlerg.setSelectedIndex(0);
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Obtener los datos de la GUI (Mascota)
                String nombre = txtNombre.getText();
                String raza = txtRaza.getText();
                String color = txtColor.getText();
                String observaciones = txtObs.getText();
                String alergias = (String) cbAlerg.getSelectedItem();
                String atencion = (String) cbAtt.getSelectedItem();

                //datos del dueno
                String nombreDueno = txtNombreDueno.getText();
                String celDueno = txtCel.getText();

                //llamada duplicada en validacion de telefono
                //controller.updateMascota(masco, nombre, raza, color, observaciones, alergias, atencion,nombreDueno,celDueno);

                // Validar que los campos requeridos no estén vacíos
                if (nombre.isEmpty() || raza.isEmpty() || color.isEmpty() || celDueno.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
                    return;
                }

                // Validar formato del número de teléfono
                if (!celDueno.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número de teléfono válido.");
                    return;
                }

                try {
                    controller.updateMascota(masco,nombre, raza, color, observaciones, alergias, atencion,nombreDueno,celDueno);
                    JOptionPane.showMessageDialog(null, "Mascota actualizada con éxito!");


                    verDatos.cargarTabla(); //Call verDatos method to refresh the table

                    dispose(); //closes ModificarDatos window after saving info

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la mascota: " + ex.getMessage());


                }
            }

        });
    }

    private void cargarDatos(int numCliente) {      // loads Mascota data
        this.masco = controller.getMascotaById((long) numCliente);

        txtNombre.setText(masco.getNombre());
        txtRaza.setText(masco.getRaza());
        txtColor.setText(masco.getColor());
        txtCel.setText(masco.getUnDueno().getCelDuenio());
        txtObs.setText(masco.getObservaciones());
        txtNombreDueno.setText(masco.getUnDueno().getNombre());

        cbAtt.setSelectedIndex(0);
        cbAlerg.setSelectedIndex(0);

    }

}