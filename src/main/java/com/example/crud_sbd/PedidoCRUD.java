package com.example.crud_sbd;

import javax.swing.*;
import java.awt.*;

public class PedidoCRUD extends JFrame {
    private JTextField txtPedidoID, txtClienteCI, txtFechaEntrega, txtUnidades, txtPrecio, txtEstado, txtTipoTrabajo, txtDescripcion;
    private JButton btnInsertar, btnModificar, btnEliminar, btnConsultar;

    public PedidoCRUD() {
        setTitle("Pedidos En Confecciones Kenneth");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Pedidos En Confecciones Kenneth", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(60, 90, 200));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);


        JPanel panelCampos = new JPanel(new GridLayout(8, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCampos.add(new JLabel("ID Pedido:"));
        txtPedidoID = new JTextField();
        panelCampos.add(txtPedidoID);

        panelCampos.add(new JLabel("Cliente CI:"));
        txtClienteCI = new JTextField();
        panelCampos.add(txtClienteCI);

        panelCampos.add(new JLabel("Fecha Entrega (YYYY-MM-DD):"));
        txtFechaEntrega = new JTextField();
        panelCampos.add(txtFechaEntrega);

        panelCampos.add(new JLabel("Unidades:"));
        txtUnidades = new JTextField();
        panelCampos.add(txtUnidades);

        panelCampos.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelCampos.add(txtPrecio);

        panelCampos.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        panelCampos.add(txtEstado);

        panelCampos.add(new JLabel("Tipo Trabajo:"));
        txtTipoTrabajo = new JTextField();
        panelCampos.add(txtTipoTrabajo);

        panelCampos.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelCampos.add(txtDescripcion);

        add(panelCampos, BorderLayout.CENTER);


        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnInsertar = new JButton("Insertar");
        btnInsertar.setBackground(new Color(0, 153, 76));
        btnInsertar.setForeground(Color.WHITE);
        panelBotones.add(btnInsertar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(0, 102, 204));
        btnModificar.setForeground(Color.WHITE);
        panelBotones.add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(204, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        panelBotones.add(btnEliminar);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBackground(new Color(255, 153, 0));
        btnConsultar.setForeground(Color.WHITE);
        panelBotones.add(btnConsultar);

        add(panelBotones, BorderLayout.SOUTH);


        configurarAcciones();




        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new BorderLayout());
        JLabel lblImagen = new JLabel();


        ImageIcon icono = new ImageIcon("C:\\Users\\Usuario\\Desktop\\CRUD_SBD\\src\\main\\resources\\peladaConfeccionando.jpg"); // Cambia el nombre y la ruta según tu archivo
        Image img = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(img));
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setVerticalAlignment(JLabel.CENTER);

        panelImagen.add(lblImagen, BorderLayout.CENTER);
        add(panelImagen, BorderLayout.EAST);
    }

    private void configurarAcciones() {
        IMEC_Pedido dao = new IMEC_Pedido();


        btnInsertar.addActionListener(e -> {
            try {

                if (txtClienteCI.getText().trim().isEmpty() || txtFechaEntrega.getText().trim().isEmpty() ||
                        txtUnidades.getText().trim().isEmpty() || txtPrecio.getText().trim().isEmpty() ||
                        txtEstado.getText().trim().isEmpty() || txtTipoTrabajo.getText().trim().isEmpty() ||
                        txtDescripcion.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                String clienteCI = txtClienteCI.getText().trim();
                String fechaEntrega = txtFechaEntrega.getText().trim();
                int unidades = Integer.parseInt(txtUnidades.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim().replace(",", "."));
                String estado = txtEstado.getText().trim();
                String tipoTrabajo = txtTipoTrabajo.getText().trim();
                String descripcion = txtDescripcion.getText().trim();



                dao.insertarPedido(clienteCI, fechaEntrega, unidades, precio, estado, tipoTrabajo, descripcion);
                JOptionPane.showMessageDialog(this, "Pedido insertado correctamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Verifica que los campos 'Unidades' y 'Precio' contengan valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });





        btnModificar.addActionListener(e -> {
            try {
                int pedidoID = Integer.parseInt(txtPedidoID.getText().trim()); // El ID es obligatorio

                String clienteCI = txtClienteCI.getText().trim().isEmpty() ? null : txtClienteCI.getText().trim();
                String fechaEntrega = txtFechaEntrega.getText().trim().isEmpty() ? null : txtFechaEntrega.getText().trim();

                Integer unidades = txtUnidades.getText().trim().isEmpty() ? null : Integer.parseInt(txtUnidades.getText().trim());
                Double precio = txtPrecio.getText().trim().isEmpty() ? null : Double.parseDouble(txtPrecio.getText().trim().replace(",", "."));

                String estado = txtEstado.getText().trim().isEmpty() ? null : txtEstado.getText().trim();
                String tipoTrabajo = txtTipoTrabajo.getText().trim().isEmpty() ? null : txtTipoTrabajo.getText().trim();
                String descripcion = txtDescripcion.getText().trim().isEmpty() ? null : txtDescripcion.getText().trim();


                dao.modificarPedido(pedidoID, clienteCI, fechaEntrega, unidades, precio, estado, tipoTrabajo, descripcion);
                JOptionPane.showMessageDialog(this, "Pedido modificado correctamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Ingresa un ID válido y valores correctos para los campos que deseas modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado al modificar pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });



        btnEliminar.addActionListener(e -> {
            try {
                dao.eliminarPedido(Integer.parseInt(txtPedidoID.getText()));
                JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        btnConsultar.addActionListener(e -> {
            try {
                int pedidoID = Integer.parseInt(txtPedidoID.getText());
                dao.consultarPedido(pedidoID);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.", "Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PedidoCRUD frame = new PedidoCRUD();
            frame.setVisible(true);
        });
    }
}
