/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Ventanas;

import Clases.Conexion;
import Clases.Fechas;
import Clases.Fondo;
import Clases.FormatoPesos;
import Clases.ImagenBoton;
import Clases.Imagenes;
import Clases.Utilidad;
import static Ventanas.Administrador.jLabelCaja;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author harol
 */
public class Vendedor extends javax.swing.JFrame {

    /**
     * Creates new form Vendedor
     */
    public Vendedor() {
        Fondo fondo = new Fondo("Fondo_Dialogos.jpg");
        this.setContentPane(fondo);
        this.setUndecorated(true);
        initComponents();
        new ImagenBoton("cerrar.png", JBotonCerrar, JBotonCerrar.getBounds().width, JBotonCerrar.getBounds().height);
        new ImagenBoton("Minimizar.png", Minimizar, Minimizar.getBounds().width, Minimizar.getBounds().height);
        
        new Imagenes("Cerrar_Sesion.png", jLabelCerrarSesion);
        new Imagenes("Administrador.png", jLabelAdministrador);
        JBotonCerrar.setContentAreaFilled(false);
        Minimizar.setContentAreaFilled(false);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("Menu");
        Shape p = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 30, 30);
        this.setShape(p);
        
        new Imagenes("ventas.png", jLabelventas);
        new Imagenes("Inventario.png", jLabelinventario);
        invisible();
        VentaDia();
        VentaSemana();
        caja();
        VentaMes();
        progesoUtilidad();
    }
    public void caja() {
        try ( Connection cn = Conexion.Conexion()) {

            PreparedStatement pr1 = cn.prepareStatement("select max(id) from caja where fecha =?");
            pr1.setDate(1, new java.sql.Date(Fechas.fechaActualDate().getTime()));
            ResultSet rs1 = pr1.executeQuery();
            while (rs1.next()) {
                if (rs1.getInt(1) != 0) {
                    PreparedStatement ps = cn.prepareStatement("select total from caja where fecha =? and id =?");
                    ps.setDate(1, new java.sql.Date(Fechas.fechaActualDate().getTime()));
                    ps.setInt(2, rs1.getInt(1));
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        jLabelCaja.setText(FormatoPesos.formato(rs.getDouble(1)));
                    }
                } else {
                    new Caja_Dia(this, true).setVisible(true);
                    caja();
                }
            }
            cn.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    public void progesoUtilidad() {
        try ( Connection cn = Conexion.Conexion()) {
            int utilidad = 0;
            LocalDate fecha = LocalDate.now();
            int mesNum = fecha.getMonthValue();
            int añoNum = 0;
            if (mesNum == 1) {
                mesNum = 12;
                añoNum = fecha.getYear() - 1;
            } else {
                mesNum--;
                añoNum = fecha.getYear();
            }
            Month mes = Month.of(mesNum);
            LocalDate fecha1 = LocalDate.of(añoNum, mes.getValue(), 1);
            LocalDate fecha2 = LocalDate.of(añoNum, mes.getValue(), Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
            PreparedStatement pr1 = cn.prepareStatement("select utilidad from ventas where fecha between ? and ?");
            pr1.setDate(1, new java.sql.Date(java.util.Date.from(fecha1.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            pr1.setDate(2, new java.sql.Date(java.util.Date.from(fecha2.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            ResultSet rs1 = pr1.executeQuery();
            while (rs1.next()) {
                utilidad += rs1.getDouble(1);
            }
            jProgressBar1.setMaximum(utilidad);
            jProgressBar1.setValue(Utilidad.utilidadMes());
            utilidadPor();
            cn.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public static void utilidadPor() {
        String por ;
        double porsentaje = (Double.valueOf(Utilidad.utilidadMes())/Double.valueOf(jProgressBar1.getMaximum())) * 100;
        por="%" + new DecimalFormat("###,###.##").format(porsentaje);
        jLabel9.setText(FormatoPesos.formato(Utilidad.utilidadMes()) );
        jLabel13.setText(FormatoPesos.formato(jProgressBar1.getMaximum()));
        jLabel10.setText(por);
        jProgressBar1.setString(por);
    }
    
    public static void VentaDia() {

        DecimalFormat formatea = new DecimalFormat("###,###");
        double venta_dia = 0;
        Date fecha_actual = Fechas.fechaActualDate();

        try {
            Connection cnn = Conexion.Conexion();
            PreparedStatement pre = cnn.prepareStatement("select precio_Total from ventas Where fecha = ?");
            pre.setDate(1, new java.sql.Date(fecha_actual.getTime()));
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                do {
                    venta_dia += rs.getDouble(1);

                    jLabelVentasHoy.setText("$" + formatea.format(venta_dia));
                } while (rs.next());
            } else {
                jLabelVentasHoy.setText("$0");
            }

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void VentaMes() {

        ZoneId defaultZoneId = ZoneId.systemDefault();
        DecimalFormat formatea = new DecimalFormat("###,###");
        double venta_dia = 0;
        Date fecha_actual = Fechas.fechaActualDate();
        GregorianCalendar semana = new GregorianCalendar();
        semana.setTime(fecha_actual);
        Month mes = LocalDate.now().getMonth();
        LocalDate fecha_semana = LocalDate.of(LocalDate.now().getYear(), mes, 1);
        java.util.Date fecha_sem = Date.from(fecha_semana.atStartOfDay(defaultZoneId).toInstant());
        java.sql.Date bd_fecha = new java.sql.Date(fecha_sem.getTime());

        try {
            Connection cnn = Conexion.Conexion();
            PreparedStatement pre = cnn.prepareStatement("select precio_Total from ventas Where fecha BETWEEN ? and ?");
            pre.setDate(1, bd_fecha);
            pre.setDate(2, new java.sql.Date(fecha_actual.getTime()));
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                do {
                    venta_dia += rs.getDouble(1);

                    VentaMEs.setText("$" + formatea.format(venta_dia));
                } while (rs.next());
            } else {
                VentaMEs.setText("$0");
            }

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void VentaSemana() {

        Date fecha_actual = Fechas.fechaActualDate();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fecha_actual);
        LocalDate fecha_semana;
        int i = gc.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                fecha_semana = LocalDate.now().minusDays(6);
                dia_semana(fecha_actual, fecha_semana);
                break;
            case 2:
                fecha_semana = LocalDate.now().minusDays(0);
                dia_semana(fecha_actual, fecha_semana);
                break;
            case 3:
                fecha_semana = LocalDate.now().minusDays(1);
                dia_semana(fecha_actual, fecha_semana);
            case 4:
                fecha_semana = LocalDate.now().minusDays(2);
                dia_semana(fecha_actual, fecha_semana);
                break;
            case 5:
                fecha_semana = LocalDate.now().minusDays(3);
                dia_semana(fecha_actual, fecha_semana);
                break;
            case 6:
                fecha_semana = LocalDate.now().minusDays(4);
                dia_semana(fecha_actual, fecha_semana);
                break;
            case 7:
                fecha_semana = LocalDate.now().minusDays(5);
                dia_semana(fecha_actual, fecha_semana);
                break;

        }

    }

    public static void dia_semana(Date fecha_actual, LocalDate fecha_semana) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        DecimalFormat formatea = new DecimalFormat("###,###");
        double venta_dia = 0;
        java.util.Date fecha_sem = Date.from(fecha_semana.atStartOfDay(defaultZoneId).toInstant());
        java.sql.Date bd_fecha = new java.sql.Date(fecha_sem.getTime());

        try {
            Connection cnn = Conexion.Conexion();
            PreparedStatement pre = cnn.prepareStatement("select precio_Total from ventas Where fecha BETWEEN ? and ?");
            pre.setDate(2, new java.sql.Date(fecha_actual.getTime()));
            pre.setDate(1, bd_fecha);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                do {
                    venta_dia += rs.getDouble(1);

                    jLabelVentaSemana.setText("$" + formatea.format(venta_dia));
                } while (rs.next());
            } else {
                jLabelVentaSemana.setText("$0");
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

        jLabelCerrarSesion = new javax.swing.JLabel();
        jLabelAdministrador = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelventas = new javax.swing.JLabel();
        jLabelinventario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        JBotonCerrar = new javax.swing.JButton();
        Minimizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelVentasHoy = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelVentaSemana = new javax.swing.JLabel();
        VentaMEs = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabelCaja = new javax.swing.JLabel();
        jLabelbarra = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelCerrarSesion.setText("cerrar ");
        jLabelCerrarSesion.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelCerrarSesionMouseMoved(evt);
            }
        });
        jLabelCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelCerrarSesionMouseClicked(evt);
            }
        });
        getContentPane().add(jLabelCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 340, 40, 40));

        jLabelAdministrador.setText("          0");
        jLabelAdministrador.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelAdministradorMouseMoved(evt);
            }
        });
        getContentPane().add(jLabelAdministrador, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 400, 40, 40));

        jLabel6.setBackground(new java.awt.Color(0, 153, 153));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setText("Ventas");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 86, 60, -1));

        jLabelventas.setBackground(new java.awt.Color(153, 153, 153));
        jLabelventas.setText("jLabel1");
        jLabelventas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabelventas.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jLabelventas.setMaximumSize(new java.awt.Dimension(35, 35));
        jLabelventas.setMinimumSize(new java.awt.Dimension(35, 35));
        jLabelventas.setPreferredSize(new java.awt.Dimension(60, 60));
        getContentPane().add(jLabelventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 73, -1, -1));

        jLabelinventario.setBackground(new java.awt.Color(153, 153, 153));
        jLabelinventario.setText("jLabel1");
        jLabelinventario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabelinventario.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jLabelinventario.setMaximumSize(new java.awt.Dimension(35, 35));
        jLabelinventario.setMinimumSize(new java.awt.Dimension(35, 35));
        jLabelinventario.setPreferredSize(new java.awt.Dimension(60, 60));
        getContentPane().add(jLabelinventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 183, -1, -1));

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setText("Inventario");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 196, 90, -1));

        JBotonCerrar.setBorder(null);
        JBotonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBotonCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(JBotonCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 17, 25, 25));

        Minimizar.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        Minimizar.setBorder(null);
        Minimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinimizarActionPerformed(evt);
            }
        });
        getContentPane().add(Minimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(683, 17, 25, 25));

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));

        jLabelVentasHoy.setBackground(new java.awt.Color(255, 255, 255));
        jLabelVentasHoy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelVentasHoy.setText("jLabel4");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("                   VENTA HOY:");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("              VENTA SEMANA:");

        jLabelVentaSemana.setBackground(new java.awt.Color(255, 255, 255));
        jLabelVentaSemana.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelVentaSemana.setText("jLabel4");

        VentaMEs.setBackground(new java.awt.Color(255, 255, 255));
        VentaMEs.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        VentaMEs.setText("jLabel4");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("                      VENTA MES:");

        jProgressBar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jProgressBar1.setMaximum(0);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("jLabel9");
        jLabel9.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("jLabel10");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("jLabel9");
        jLabel13.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("Este mes:");
        jLabel14.setToolTipText("");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel15.setText("Anterior mes:");
        jLabel15.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVentasHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelVentaSemana)
                    .addComponent(VentaMEs))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(129, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(79, 79, 79))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabelVentasHoy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabelVentaSemana))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(VentaMEs)
                    .addComponent(jLabel4))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 290));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("CAJA");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, -1, -1));

        jLabelCaja.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelCaja.setForeground(new java.awt.Color(204, 204, 204));
        jLabelCaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCaja.setText("jLabel5");
        jLabelCaja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabelCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 240, 50));

        jLabelbarra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelbarraMouseMoved(evt);
            }
        });
        jLabelbarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelbarraMouseExited(evt);
            }
        });
        getContentPane().add(jLabelbarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 300, 80, 160));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        new Ventas().setVisible(true);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        new Catalogo().setVisible(true);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void JBotonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBotonCerrarActionPerformed
        Object[] opc = new Object[]{"SI", "NO"};
        int i = JOptionPane.showOptionDialog(null, "¿Desea salir?", "salir", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opc, opc[0]);
        if (i == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_JBotonCerrarActionPerformed

    private void MinimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizarActionPerformed
        this.setState(ICONIFIED);
    }//GEN-LAST:event_MinimizarActionPerformed

    int xm;
    int ym;
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xm = evt.getX();
        ym = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xm, y - ym);
    }//GEN-LAST:event_formMouseDragged

    private void jLabelCerrarSesionMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCerrarSesionMouseMoved
        visible();
    }//GEN-LAST:event_jLabelCerrarSesionMouseMoved

    private void jLabelCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelCerrarSesionMouseClicked
        int i = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Salir?", "Cerrar Sesion", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            this.dispose();
            new Login().setVisible(true);
        }
    }//GEN-LAST:event_jLabelCerrarSesionMouseClicked

    private void jLabelbarraMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelbarraMouseMoved
        visible();
    }//GEN-LAST:event_jLabelbarraMouseMoved

    private void jLabelbarraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelbarraMouseExited
        invisible();
    }//GEN-LAST:event_jLabelbarraMouseExited

    private void jLabelAdministradorMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelAdministradorMouseMoved
        visible();
    }//GEN-LAST:event_jLabelAdministradorMouseMoved

    public void visible() {
        jLabelbarra.setVisible(true);
        jLabelCerrarSesion.setVisible(true);
    }

    /**
     *
     */
    public void invisible() {
        jLabelbarra.setVisible(false);
        jLabelCerrarSesion.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBotonCerrar;
    private javax.swing.JButton Minimizar;
    private static javax.swing.JLabel VentaMEs;
    private javax.swing.JLabel jLabel1;
    private static javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private static javax.swing.JLabel jLabel13;
    private static javax.swing.JLabel jLabel14;
    private static javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private static javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAdministrador;
    public static javax.swing.JLabel jLabelCaja;
    private javax.swing.JLabel jLabelCerrarSesion;
    public static javax.swing.JLabel jLabelVentaSemana;
    private static javax.swing.JLabel jLabelVentasHoy;
    private javax.swing.JLabel jLabelbarra;
    private javax.swing.JLabel jLabelinventario;
    private javax.swing.JLabel jLabelventas;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
