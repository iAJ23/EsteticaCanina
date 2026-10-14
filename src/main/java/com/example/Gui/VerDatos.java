package com.example.Gui;

import com.example.Logic.Controller;
import com.example.Logic.Mascota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VerDatos extends JFrame {
    private JPanel verDatos;
    private JTable tablaMascota;
    private JButton eliminarButton;
    private JButton editarButton;
    private JScrollPane jsp;

    Controller controller;

    public VerDatos() {

        // Use a layout manager
        verDatos.setLayout(new BorderLayout());
        verDatos.add(jsp, BorderLayout.CENTER);  // Add the table in the center

        // Add buttons to the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(eliminarButton);
        buttonPanel.add(editarButton);
        verDatos.add(buttonPanel, BorderLayout.SOUTH);  // Add buttons at the bottom

        setContentPane(verDatos);  // Keep verDatos as the main panel
        setTitle("Estética Canina - Visualizar Datos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        controller = new Controller();

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //controlar que la tabla no este vacia
                if(tablaMascota.getRowCount()>0) {
                    // controlar que un registro esté seleccionado
                    if (tablaMascota.getSelectedRow() != -1) {
                        int num_cliente = Integer.parseInt(String.valueOf(tablaMascota.getValueAt(tablaMascota.getSelectedRow(), 0)));
                        //llamar al metodo eliminar
                        controller.deleteMascota((long) num_cliente);
                        //avisar al usuario que se eliminó correctamente

                        cargarTabla();

                        mostrarMensaje("Mascota eliminada correctamente", "Info", "Eliminar Mascota");

                        //dispose(); //agregado al final<<

                    } else {
                        mostrarMensaje("No se ha seleccionado ninguna mascota", "Error", "No se ha podido eliminar");
                    }
                }else{
                    mostrarMensaje("No hay nada para elminar en la tabla","Error","Error al eliminar");
                }
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(tablaMascota.getRowCount()>0) {

                    // controlar que un registro esté seleccionado
                    if (tablaMascota.getSelectedRow() != -1) {

                        int num_cliente = Integer.parseInt(String.valueOf(tablaMascota.getValueAt(tablaMascota.getSelectedRow(), 0)));

                         ModificarDatos modificarInterface = new ModificarDatos(num_cliente,VerDatos.this);


                    } else {
                        mostrarMensaje("No se ha seleccionado ninguna mascota", "Error", "No se ha podido editar");
                    }
                }else{
                    mostrarMensaje("No hay nada para EDITAR en la tabla","Error","Error al editar");
                }
                cargarTabla();
            }
        });
    }

    public static void cargarDatos() {
    }

    public void mostrarMensaje (String mensaje, String tipo, String titulo) {
        JOptionPane optionPane = new JOptionPane(mensaje);
        if(tipo.equalsIgnoreCase("Info")) {
            optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        }
        else if(tipo.equalsIgnoreCase("Error")) {
            optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
        }
        JDialog dialog = optionPane.createDialog(titulo);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    // Disable editing of table cells
    public void cargarTabla(){
        DefaultTableModel tabla = new DefaultTableModel() {

            @Override
            public boolean isCellEditable (int row, int column) {
                return false;
            }
        };

        String[] titulos = {"Num", "Nombre", "Color", "Raza", "Alergias", "At. Esp.","Observaciones", "Dueño", "Cel"};
        tabla.setColumnIdentifiers(titulos);

        // Load data from the database
        List<Mascota> listaMascotas = controller.getAllMascotas();

        if(listaMascotas != null) {
            for(Mascota mascota : listaMascotas){
                Object[] object = {
                        mascota.getNum_cliente(),
                        mascota.getNombre(),
                        mascota.getColor(),
                        mascota.getRaza(),
                        mascota.getAlergico(),
                        mascota.getAtencion_especial(),
                        mascota.getObservaciones(),
                        mascota.getUnDueno() != null ? mascota.getUnDueno().getNombre() : "No Disponible",
                        mascota.getUnDueno() != null ? mascota.getUnDueno().getCelDuenio() : "No Disponible"
                };

                // Add the row with the provided information
                tabla.addRow(object);
            }
        }
        tablaMascota.setModel(tabla);
    }
}