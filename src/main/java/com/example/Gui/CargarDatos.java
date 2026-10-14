package com.example.Gui;

import com.example.Logic.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CargarDatos extends JFrame {

    public final Controller controller = new Controller();

    private JPanel cargarDatos;
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

    public CargarDatos() {
        setContentPane(cargarDatos);
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
                // Obtener los datos de la GUI
                String nombre = txtNombre.getText();
                String raza = txtRaza.getText();
                String color = txtColor.getText();
                String observaciones = txtObs.getText();
                String alergias = (String) cbAlerg.getSelectedItem();
                String atencion = (String) cbAtt.getSelectedItem();
                String nombreDueno = txtNombreDueno.getText();
                String celDueno = txtCel.getText();

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
                    controller.saveMascota(nombre, raza, color, observaciones, alergias, atencion, celDueno,nombreDueno);
                    JOptionPane.showMessageDialog(null, "Mascota guardada con éxito!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar la mascota: " + ex.getMessage());
                }

                //set everything to "blank" after saving (could be in a method)
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
    }

}
