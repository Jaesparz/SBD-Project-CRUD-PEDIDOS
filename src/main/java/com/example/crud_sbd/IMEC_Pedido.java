package com.example.crud_sbd;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IMEC_Pedido {
    public void insertarPedido(String clienteCI, String fechaEntrega, int unidades, double precio, String estado, String tipoTrabajo, String descripcion) {
        String queryCheckCliente = "SELECT COUNT(*) AS count FROM Cliente WHERE cliente_ci = ?";
        String queryInsert = "INSERT INTO Pedido (cliente_ci, fecha_entrega, unidades, precio, estado, tipo_trabajo, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstCheckCliente = con.prepareStatement(queryCheckCliente);
             PreparedStatement pstInsert = con.prepareStatement(queryInsert)) {

            // Verificar si el cliente existe
            pstCheckCliente.setString(1, clienteCI);
            ResultSet rs = pstCheckCliente.executeQuery();
            if (rs.next() && rs.getInt("count") == 0) {
                // El cliente no existe
                System.out.println("Error: El cliente con CI " + clienteCI + " no existe.");
                return;
            }

            // Insertar el pedido
            pstInsert.setString(1, clienteCI);
            pstInsert.setString(2, fechaEntrega);
            pstInsert.setInt(3, unidades);
            pstInsert.setDouble(4, precio);
            pstInsert.setString(5, estado);
            pstInsert.setString(6, tipoTrabajo);
            pstInsert.setString(7, descripcion);

            int rowsAffected = pstInsert.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pedido insertado correctamente.");
            } else {
                System.out.println("No se pudo insertar el pedido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void modificarPedido(int pedidoID, String clienteCI, String fechaEntrega, Integer unidades, Double precio, String estado, String tipoTrabajo, String descripcion) {
        String querySelect = "SELECT * FROM Pedido WHERE pedido_id = ?";
        String queryUpdate = "UPDATE Pedido SET cliente_ci = ?, fecha_entrega = ?, unidades = ?, precio = ?, estado = ?, tipo_trabajo = ?, descripcion = ? WHERE pedido_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstSelect = con.prepareStatement(querySelect);
             PreparedStatement pstUpdate = con.prepareStatement(queryUpdate)) {


            pstSelect.setInt(1, pedidoID);
            ResultSet rs = pstSelect.executeQuery();

            if (rs.next()) {

                String currentClienteCI = rs.getString("cliente_ci");
                String currentFechaEntrega = rs.getString("fecha_entrega");
                int currentUnidades = rs.getInt("unidades");
                double currentPrecio = rs.getDouble("precio");
                String currentEstado = rs.getString("estado");
                String currentTipoTrabajo = rs.getString("tipo_trabajo");
                String currentDescripcion = rs.getString("descripcion");


                pstUpdate.setString(1, clienteCI != null && !clienteCI.isEmpty() ? clienteCI : currentClienteCI);
                pstUpdate.setString(2, fechaEntrega != null && !fechaEntrega.isEmpty() ? fechaEntrega : currentFechaEntrega);
                pstUpdate.setInt(3, unidades != null ? unidades : currentUnidades);
                pstUpdate.setDouble(4, precio != null ? precio : currentPrecio);
                pstUpdate.setString(5, estado != null && !estado.isEmpty() ? estado : currentEstado);
                pstUpdate.setString(6, tipoTrabajo != null && !tipoTrabajo.isEmpty() ? tipoTrabajo : currentTipoTrabajo);
                pstUpdate.setString(7, descripcion != null && !descripcion.isEmpty() ? descripcion : currentDescripcion);
                pstUpdate.setInt(8, pedidoID);

                int rowsAffected = pstUpdate.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Pedido modificado correctamente.");
                } else {
                    System.out.println("No se modificó el pedido.");
                }
            } else {
                System.out.println("El pedido con ID " + pedidoID + " no existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void eliminarPedido(int pedidoID) {
        String deleteMaterialQuery = "DELETE FROM MaterialNecesario WHERE pedido_id = ?";
        String deletePedidoQuery = "DELETE FROM Pedido WHERE pedido_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstMaterial = con.prepareStatement(deleteMaterialQuery);
             PreparedStatement pstPedido = con.prepareStatement(deletePedidoQuery)) {

            // Primero elimina los materiales relacionados
            pstMaterial.setInt(1, pedidoID);
            pstMaterial.executeUpdate();

            // Luego elimina el pedido
            pstPedido.setInt(1, pedidoID);
            int rowsAffected = pstPedido.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pedido eliminado correctamente.");
            } else {
                System.out.println("No se encontró el pedido con ID: " + pedidoID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void consultarPedido(int pedidoID) {
        String query = "SELECT * FROM Pedido WHERE pedido_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, pedidoID);
            ResultSet rs = pst.executeQuery();

            // Si se encuentra el pedido, muestra sus datos
            if (rs.next()) {
                StringBuilder datosPedido = new StringBuilder();
                datosPedido.append("ID Pedido: ").append(rs.getInt("pedido_id")).append("\n");
                datosPedido.append("Cliente CI: ").append(rs.getString("cliente_ci")).append("\n");
                datosPedido.append("Fecha Entrega: ").append(rs.getString("fecha_entrega")).append("\n");
                datosPedido.append("Unidades: ").append(rs.getInt("unidades")).append("\n");
                datosPedido.append("Precio: $").append(rs.getDouble("precio")).append("\n");
                datosPedido.append("Estado: ").append(rs.getString("estado")).append("\n");
                datosPedido.append("Tipo Trabajo: ").append(rs.getString("tipo_trabajo")).append("\n");
                datosPedido.append("Descripción: ").append(rs.getString("descripcion")).append("\n");

                // Muestra los datos en un JOptionPane
                JOptionPane.showMessageDialog(null, datosPedido.toString(), "Información del Pedido", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Si no se encuentra el pedido
                JOptionPane.showMessageDialog(null, "No se encontró el pedido con ID: " + pedidoID, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}
