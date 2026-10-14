package com.example.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Principal extends JFrame{
    private JButton cargarDatosButton;
    private JButton verDatosButton;
    private JButton salirButton;
    private JLabel Pulga;
    private JPanel myPanel;

    public Principal() {
    setContentPane(myPanel);
    setTitle("Estetica Canina App");
    setSize(400,400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);

        cargarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CargarDatos cargarDatos = new CargarDatos();
            }
        });


        verDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            VerDatos verDatos = new VerDatos();

            //adapter para abrir la ventana con el boton ver datos
                verDatos.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowOpened(WindowEvent e) {

                        // logica para ejecutar en la ventana abierta:
                        verDatos.cargarTabla();
                    }
                });

            }
        });




        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        //establecer los nombres de las columnas
    }
}
