/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Ventanas;

import Clases.Hash;
import Clases.Conexion;
import Clases.Fechas;
import Clases.Fondo;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author harol
 */
public class Usuario extends javax.swing.JFrame {

    int i;
    static int g;

    public Usuario() {
        Fondo fondo = new Fondo("FondoMenu.jpg");
        this.setContentPane(fondo);
        initComponents();

        setLocationRelativeTo(null);
        g = 1;
        usuariotabla();
        cerra();
        jButton2.setVisible(false);
        jButton3.setVisible(false);

    }

    public void cerra() {
        try {
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    g = 0;
                }
            });
        } catch (Exception e) {
        }
    }

    public static void usuariotabla() {
        String[] datos = new String[8];
        DefaultTableModel tabla = new DefaultTableModel();

        tabla.addColumn("Id");
        tabla.addColumn("Usuario");
        tabla.addColumn("Contraseña");
        tabla.addColumn("Nombre");
        tabla.addColumn("Cargo");
        tabla.addColumn("Cedula");
        tabla.addColumn("Celular");
        tabla.addColumn("Correo");
        
        jTableUsuarios.setModel(tabla);
        TableColumnModel columnModel = jTableUsuarios.getColumnModel();
        columnModel.getColumn(0).setResizable(false);
        columnModel.getColumn(1).setResizable(false);
        columnModel.getColumn(2).setResizable(false);
        columnModel.getColumn(3).setResizable(false);
        columnModel.getColumn(4).setResizable(false);
        columnModel.getColumn(5).setResizable(false);
        columnModel.getColumn(6).setResizable(false);

        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(150);
        try {
            Connection cnn = Conexion.Conexion();

            PreparedStatement pre = cnn.prepareStatement("select * from usuarios");
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < 8; i++) {
                    datos[i] = rs.getString(i + 1);
                }
                tabla.addRow(datos);
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jTextFieldContraseña = new javax.swing.JTextField();
        jTextFieldNombre = new javax.swing.JTextField();
        jTextFieldCedula = new javax.swing.JTextField();
        jTextFieldCelular = new javax.swing.JTextField();
        jTextFieldCorrea = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jComboBoxCargo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAutoRequestFocus(false);
        setResizable(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Usuario:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Contraseña:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Nombre:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Cedula:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Celular:");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Correo:");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cargo:");

        jTextFieldCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCedulaActionPerformed(evt);
            }
        });

        jComboBoxCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Administrador", "Vendedor" }));

        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableUsuarios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableUsuarios);
        if (jTableUsuarios.getColumnModel().getColumnCount() > 0) {
            jTableUsuarios.getColumnModel().getColumn(0).setResizable(false);
            jTableUsuarios.getColumnModel().getColumn(1).setResizable(false);
            jTableUsuarios.getColumnModel().getColumn(2).setResizable(false);
            jTableUsuarios.getColumnModel().getColumn(3).setResizable(false);
            jTableUsuarios.getColumnModel().getColumn(4).setResizable(false);
            jTableUsuarios.getColumnModel().getColumn(5).setResizable(false);
            jTableUsuarios.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Creado por Corporacion Portillo ADMP ®©™ 2022 V1.0");
        jLabel9.setAlignmentX(253);
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 255, 255)));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Exclusivo para CaliDrogas El Tambo");
        jLabel10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 255, 255)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jButton1)
                .addGap(8, 8, 8)
                .addComponent(jButton2)
                .addGap(9, 9, 9)
                .addComponent(jButton3))
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(jLabel9))
            .addGroup(layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(jLabel10))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jTextFieldCorrea, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(jLabel8)
                            .addGap(5, 5, 5)
                            .addComponent(jComboBoxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jTextFieldCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(jLabel6)
                            .addGap(5, 5, 5)
                            .addComponent(jTextFieldCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2)
                        .addGap(5, 5, 5)
                        .addComponent(jTextFieldContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldNombre)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3)))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldCorrea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel9)
                .addGap(7, 7, 7)
                .addComponent(jLabel10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCedulaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String usuario = jTextFieldUsuario.getText();
        String contraseña = jTextFieldContraseña.getText();
        String nombre = jTextFieldNombre.getText();
        String cedula = jTextFieldCedula.getText();
        String celular = jTextFieldCelular.getText();
        String correo = jTextFieldCorrea.getText();
        int i = jComboBoxCargo.getSelectedIndex();
        System.out.println("" + i);

        if (!(usuario.equals("") && contraseña.equals("") && nombre.equals("") && cedula.equals("") && celular.equals("") && correo.equals("")) && jComboBoxCargo.getSelectedIndex() != 0) {
            agregar();
            usuariotabla();
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese los datos");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        eliminar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTableUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableUsuariosMouseClicked
        i = jTableUsuarios.getSelectedRow();
        jButton2.setVisible(true);
        jButton3.setVisible(true);
        String usuario = jTableUsuarios.getValueAt(i, 1).toString();
        String contraseña = jTableUsuarios.getValueAt(i, 2).toString();
        String nombre = jTableUsuarios.getValueAt(i, 3).toString();
        String cargo = jTableUsuarios.getValueAt(i, 4).toString();
        String cedula = jTableUsuarios.getValueAt(i,5).toString();
        String celular = jTableUsuarios.getValueAt(i,6).toString();
        String correo = jTableUsuarios.getValueAt(i, 7).toString();


        jTextFieldCedula.setText(cedula);
        jTextFieldCelular.setText(celular);
        jTextFieldContraseña.setText(contraseña);
        jTextFieldCorrea.setText(correo);
        jTextFieldNombre.setText(nombre);
        jTextFieldUsuario.setText(usuario);
        jComboBoxCargo.setSelectedItem(cargo);
    }//GEN-LAST:event_jTableUsuariosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if ((jTextFieldContraseña.getText().length() > 7 && jTextFieldContraseña.getText().length() < 13)||confirmarcontraseña(jTextFieldContraseña.getText())) {
            modificar();
            usuariotabla();
        }else{
            JOptionPane.showMessageDialog(this, "La contraseña debe tener entre 8 a 12 caracteres!!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    public boolean confirmarcontraseña(String contraseña) {

        boolean b = false;
        try {
            Connection cn = Conexion.Conexion();
            PreparedStatement pre = cn.prepareStatement("select contraseña from usuarios where contraseña = ?");
            pre.setString(1, contraseña);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                b = true;
            }
        } catch (SQLException e) {
        }
        return b;
    }

    public void modificar() {
        if (validacionUsuario() || jTableUsuarios.getValueAt(i, 1).toString().equals(jTextFieldUsuario.getText())) {
            try {
                Connection cn = Conexion.Conexion();
                PreparedStatement pre = cn.prepareStatement("update usuarios set usuario = ?,contraseña=?,nombre =?,cargo = ?,cedula = ?,celular =?,correo =? where idusuarios = ?");
                pre.setString(1, jTextFieldUsuario.getText());
                if (!confirmarcontraseña(jTextFieldContraseña.getText())) {
                    pre.setString(2, Hash.hash24(jTextFieldContraseña.getText()));
                } else {
                    pre.setString(2, jTextFieldContraseña.getText());
                }
                pre.setString(3, jTextFieldNombre.getText());
                pre.setString(4, jComboBoxCargo.getSelectedItem().toString());
                pre.setString(5, jTextFieldCedula.getText());
                pre.setString(6, jTextFieldCelular.getText());
                pre.setString(7, jTextFieldCorrea.getText());
                pre.setInt(8, Integer.parseInt(jTableUsuarios.getValueAt(i, 0).toString()));
                pre.executeUpdate();
                JOptionPane.showMessageDialog(null, "Actualizacion exitosa");

            } catch (HeadlessException | SQLException e) {
                System.err.println("Error al ingresar el producto " + e);
                JOptionPane.showMessageDialog(null, "¡Error al ingresar el producto!. Contacte al soporte Corporacion Portillo.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "el usuario ya existe, por favor digite oto usuario difrente");
        }
    }

    public void agregar() {
        if (validacionUsuario() && jTextFieldContraseña.getText().length() < 13 && jTextFieldContraseña.getText().length() > 7) {
            try {
                Connection cn = Conexion.Conexion();
                PreparedStatement pre = cn.prepareStatement("INSERT INTO usuarios (idusuarios,usuario,contraseña,nombre,cargo,"
                        + "cedula,celular,correo) values(?,?,?,?,?,?,?,?)");

                pre.setInt(1, 0);
                pre.setString(2, jTextFieldUsuario.getText());
                pre.setString(3, Hash.hash24(jTextFieldContraseña.getText()));
                pre.setString(4, jTextFieldNombre.getText());
                pre.setString(5, jComboBoxCargo.getSelectedItem().toString());
                pre.setString(6, jTextFieldCedula.getText());
                pre.setString(7, jTextFieldCelular.getText());
                pre.setString(8, jTextFieldCorrea.getText());

                pre.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registro exitoso");
            } catch (HeadlessException | SQLException e) {
                System.err.println("Error al ingresar el producto " + e);
                JOptionPane.showMessageDialog(null, "¡Error al ingresar el producto!. Contacte al soporte Corporacion Portillo.");
            }
        } else {
            if (!validacionUsuario()) {
                JOptionPane.showMessageDialog(null, "el usuario ya existe, por favor digite oto usuario difrente");
            } else {
                JOptionPane.showMessageDialog(null, "la contraseña debe contener mas de 8 y menos de 12 caracteres");
            }
        }
    }

    public void eliminar() {
        int i = jTableUsuarios.getSelectedRow();
        int id = Integer.parseInt(jTableUsuarios.getValueAt(i, 0).toString());
        try {
            Connection cn = Conexion.Conexion();
            PreparedStatement pr = cn.prepareStatement("delete from usuarios where idusuarios = " + id);
            pr.executeUpdate();
            usuariotabla();
        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    public boolean validacionUsuario() {

        boolean b = true;
        try {
            Connection cn = Conexion.Conexion();
            PreparedStatement pr = cn.prepareStatement("select usuario from usuarios");
            ResultSet rs = pr.executeQuery();
            String user;
            while (rs.next()) {
                user = rs.getString(1);
                if (user.equals(jTextFieldUsuario.getText())) {
                    b = false;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al ingresar el producto " + e);
            JOptionPane.showMessageDialog(null, "¡Error al ingresar el producto!. Contacte al soporte Corporacion Portillo.");
        }

        return b;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBoxCargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private static javax.swing.JTable jTableUsuarios;
    private javax.swing.JTextField jTextFieldCedula;
    private javax.swing.JTextField jTextFieldCelular;
    private javax.swing.JTextField jTextFieldContraseña;
    private javax.swing.JTextField jTextFieldCorrea;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables
}
