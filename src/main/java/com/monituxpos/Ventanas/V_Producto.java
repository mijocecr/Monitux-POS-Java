/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.Categoria;
import com.monituxpos.Clases.MonituxDBContext;
import com.monituxpos.Clases.Producto;
import com.monituxpos.Clases.Proveedor;
import com.monituxpos.Clases.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Producto extends javax.swing.JFrame {
    
    
    
    public int Secuencial_Usuario=V_Menu_Principal.getSecuencial_Usuario();
    public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
    public int Secuencial_Proveedor;
    public int Secuencial_Categoria;
    public int Secuencial;
    public boolean esNuevo;
    
    private byte[] imagen;

public byte[] getImagen() {
    return imagen;
}

public void setImagen(byte[] imagen) {
    this.imagen = imagen;
}

    

 
//********************************************
    

    private Runnable onProductoEditado;

   

    public void setOnProductoEditado(Runnable listener) {
        this.onProductoEditado = listener;
    }

 
    
    public void notificarEdicion() {
    if (onProductoEditado != null) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                // Si necesitas hacer algo pesado aquí, hazlo
                return null;
            }

            @Override
            protected void done() {
                try {
                    onProductoEditado.run(); // ✅ Aquí sí puedes tocar la GUI
                } catch (Exception ex) {
                    System.err.println("⚠️ Error en onProductoEditado: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }.execute();
    }
}

    
    

    //********************************************


    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(V_Producto.class.getName());

    /**
     * Creates new form V_Producto
     */
    public V_Producto() {
        initComponents();
        
        
        
    }

 
    
    public V_Producto(boolean esNuevo, Producto vistaProducto) {
    this.esNuevo = esNuevo;

    initComponents(); // Inicializa la interfaz

    if (!esNuevo && vistaProducto != null) {
        txt_Cantidad.setEnabled(false);

        // Asignación segura de campos
        txt_Codigo.setText(valueOrEmpty(vistaProducto.getCodigo()));
        txt_Descripcion.setText(valueOrEmpty(vistaProducto.getDescripcion()));
        txt_Cantidad.setText(String.valueOf(vistaProducto.getCantidad()));
        txt_PrecioCosto.setText(String.valueOf(vistaProducto.getPrecio_Costo()));
        txt_PrecioVenta.setText(String.valueOf(vistaProducto.getPrecio_Venta()));
        txt_Marca.setText(valueOrEmpty(vistaProducto.getMarca()));
        txt_bc.setText(valueOrEmpty(vistaProducto.getCodigo_Barra()));
        txt_CodigoFabricante.setText(valueOrEmpty(vistaProducto.getCodigo_Fabricante()));
        txt_ExistenciaMinima.setText(String.valueOf(vistaProducto.getExistencia_Minima()));

        // Fecha de caducidad segura
        if (vistaProducto.isExpira() && vistaProducto.getFecha_Caducidad() != null && !vistaProducto.getFecha_Caducidad().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(vistaProducto.getFecha_Caducidad(), formatter);
                datePicker1.setDate(fecha);
                jCheckBox1.setSelected(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Fecha inválida: " + vistaProducto.getFecha_Caducidad());
            }
        } else {
            jCheckBox1.setSelected(false);
            datePicker1.setDate(null);
        }

        // Tipo de producto
        if (vistaProducto.getTipo() != null && jComboBox3.getItemCount() > 0) {
            jComboBox3.setSelectedItem(vistaProducto.getTipo());
        }
        jComboBox3.setEnabled(false);

        // Identificadores
        Secuencial = vistaProducto.getSecuencial();
        Secuencial_Proveedor = vistaProducto.getSecuencial_Proveedor();
        Secuencial_Categoria = vistaProducto.getSecuencial_Categoria();

        // Combos
        Util.llenarComboProveedor(comboProveedor, Secuencial_Empresa);
        Util.llenar_Combo_Categoria(comboCategoria, Secuencial_Empresa);

        // Imagen redimensionada
        imagen = vistaProducto.getImagen();
        if (imagen != null) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(imagen);
                BufferedImage imagenCargada = ImageIO.read(bis);

                int ancho = labelImagen.getWidth();
                int alto = labelImagen.getHeight();

                Image imagenEscalada = imagenCargada.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                labelImagen.setIcon(new ImageIcon(imagenEscalada));
            } catch (IOException e) {
                e.printStackTrace();
                labelImagen.setIcon(null);
            }
        } else {
            labelImagen.setIcon(null);
        }
    } else {
        Secuencial = -1;
    }
}

// Método auxiliar para evitar nulos
private String valueOrEmpty(String valor) {
    return valor != null ? valor : "";
}

 
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        label_bc = new javax.swing.JLabel();
        labelImagen = new javax.swing.JLabel();
        label_qr = new javax.swing.JLabel();
        txt_Codigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_Cantidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_Marca = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        comboProveedor = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txt_bc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_PrecioCosto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_PrecioVenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_ExistenciaMinima = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txt_CodigoFabricante = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Descripcion = new javax.swing.JTextArea();
        datePicker1 = new com.github.lgooddatepicker.components.DatePicker();
        jCheckBox1 = new javax.swing.JCheckBox();
        labelImagen1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        Menu_Nuevo = new javax.swing.JMenuItem();
        Menu_Guardar = new javax.swing.JMenuItem();
        Menu_Eliminar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        Menu_Salir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(35, 32, 40));

        jLabel2.setText("Codigo:");
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));

        label_bc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_bc.setToolTipText("Mueva rueda del raton para copiar al portapapeles.");
        label_bc.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                label_bcMouseWheelMoved(evt);
            }
        });
        label_bc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_bcMouseClicked(evt);
            }
        });

        labelImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        labelImagen.setToolTipText("Click para escoger imagen.");
        labelImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelImagenMouseClicked(evt);
            }
        });

        label_qr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_qr.setToolTipText("Mueva rueda del raton para copiar al portapapeles.");
        label_qr.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                label_qrMouseWheelMoved(evt);
            }
        });
        label_qr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_qrMouseClicked(evt);
            }
        });

        jLabel3.setText("Cantidad:");
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        txt_Cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_CantidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_CantidadKeyReleased(evt);
            }
        });

        jLabel4.setText("Marca:");
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Proveedor");
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));

        comboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboProveedorMouseClicked(evt);
            }
        });
        comboProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProveedorActionPerformed(evt);
            }
        });

        jLabel6.setText("Categoria");
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));

        comboCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboCategoriaMouseClicked(evt);
            }
        });

        jLabel7.setText("Codigo Barras:");
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));

        txt_bc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bcActionPerformed(evt);
            }
        });
        txt_bc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bcKeyReleased(evt);
            }
        });

        jLabel8.setText("Precio Costo:");
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));

        txt_PrecioCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_PrecioCostoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_PrecioCostoKeyReleased(evt);
            }
        });

        jLabel9.setText("Precio Venta:");
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));

        txt_PrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_PrecioVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_PrecioVentaKeyReleased(evt);
            }
        });

        jLabel10.setText("Existencia Minima:");
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));

        txt_ExistenciaMinima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ExistenciaMinimaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ExistenciaMinimaKeyReleased(evt);
            }
        });

        jLabel11.setText("Tipo:");
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Producto", "Servicio" }));

        jLabel12.setText("Codigo Fabricante:");
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));

        jLabel13.setText("Descripcion");
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));

        txt_Descripcion.setColumns(20);
        txt_Descripcion.setLineWrap(true);
        txt_Descripcion.setRows(5);
        jScrollPane1.setViewportView(txt_Descripcion);

        datePicker1.setVisible(false);

        jCheckBox1.setText("Expira");
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });
        jCheckBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCheckBox1PropertyChange(evt);
            }
        });

        labelImagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/camera.png"))); // NOI18N
        labelImagen1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelImagen1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_bc, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_PrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_PrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_ExistenciaMinima, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_CodigoFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(jLabel13)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(214, 214, 214))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel2))
                                    .addComponent(jLabel3)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Marca, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_bc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label_qr, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(labelImagen1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(comboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5)))
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(label_bc, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel4))
                            .addComponent(txt_Marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)
                        .addGap(6, 6, 6)
                        .addComponent(comboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel7))
                            .addComponent(txt_bc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_PrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel9))
                            .addComponent(txt_PrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_ExistenciaMinima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_CodigoFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1)))
                    .addComponent(label_qr, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Productos");
        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setOpaque(true);

        jMenu1.setText("Opciones");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bricks.png"))); // NOI18N
        jMenu2.setText("Registrar");
        jMenu2.setToolTipText("");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry.png"))); // NOI18N
        jMenuItem2.setText("Proveedor");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/award_star_gold_2.png"))); // NOI18N
        jMenuItem3.setText("Categoria");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenu1.add(jMenu2);

        Menu_Nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/page_white.png"))); // NOI18N
        Menu_Nuevo.setText("Nuevo");
        Menu_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_NuevoActionPerformed(evt);
            }
        });
        jMenu1.add(Menu_Nuevo);

        Menu_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk.png"))); // NOI18N
        Menu_Guardar.setText("Guardar");
        Menu_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_GuardarActionPerformed(evt);
            }
        });
        jMenu1.add(Menu_Guardar);

        Menu_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exclamation.png"))); // NOI18N
        Menu_Eliminar.setText("Eliminar");
        Menu_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_EliminarActionPerformed(evt);
            }
        });
        jMenu1.add(Menu_Eliminar);
        jMenu1.add(jSeparator1);

        Menu_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/door_out.png"))); // NOI18N
        Menu_Salir.setText("Salir");
        Menu_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_SalirActionPerformed(evt);
            }
        });
        jMenu1.add(Menu_Salir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void label_bcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_bcMouseClicked

       
    }//GEN-LAST:event_label_bcMouseClicked

    
    
  
    
    
    
    
    
   
    
    
    
    
    private void labelImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelImagenMouseClicked


        
        
        try {
            String imagenSeleccionada = Util.abrirDialogoSeleccionFilename();

            if (imagenSeleccionada != null && !imagenSeleccionada.trim().isEmpty()) {
                File archivoImagen = new File(imagenSeleccionada);

                if (archivoImagen.exists() && archivoImagen.isFile()) {
                    // Cargar imagen como BufferedImage
                    BufferedImage buffered = ImageIO.read(archivoImagen);

                    if (buffered != null) {
                        // Redimensionar al tamaño del JLabel
                        int ancho = labelImagen.getWidth();
                        int alto = labelImagen.getHeight();

                        Image imagenEscalada = buffered.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

                        // Mostrar imagen redimensionada en el JLabel
                        labelImagen.setIcon(new ImageIcon(imagenEscalada));

                        // Convertir imagen original (no escalada) a byte[] para guardar
                        ByteArrayOutputStream ms = new ByteArrayOutputStream();
                        ImageIO.write(buffered, "png", ms);
                        imagen = ms.toByteArray();
                    } else {
                        imagen = null;
                        System.err.println("Formato de imagen no soportado.");
                    }
                } else {
                    imagen = null;
                    System.err.println("Archivo no encontrado o inválido: " + imagenSeleccionada);
                }
            }
        } catch (IOException e) {
            imagen = null;
            e.printStackTrace();
            // VMenuPrincipal.MSG.showMSG("No se pudo cargar la imagen seleccionada.", "Error");
        }
        

        // TODO add your handling code here:
    }//GEN-LAST:event_labelImagenMouseClicked

    private void Dibujar_Codigos(){
    
               if (!txt_bc.getText().isBlank()) {
    String mensajeQR = "Codigo: " + txt_Codigo.getText() +
                       "\nDescripcion: " + txt_Descripcion.getText() +
                       "\nPrecio Venta: " + txt_PrecioVenta.getText() +
                       "\nMarca: " + txt_Marca.getText() +
                       "\nCodigo Barra: " + txt_bc.getText() +
                       "\nCodigo Fabricante: " + txt_CodigoFabricante.getText() +
                       "\nStock Minimo: " + txt_ExistenciaMinima.getText();

    // Generar código QR
    BufferedImage imagenQR = Util.generarCodigoQR(Secuencial, mensajeQR, Secuencial_Empresa);
    Image imagenQRRedimensionada = imagenQR.getScaledInstance(
        label_qr.getWidth(), label_qr.getHeight(), Image.SCALE_SMOOTH
    );
    label_qr.setIcon(new ImageIcon(imagenQRRedimensionada));

    // Liberar imagen anterior si existe
    Icon iconoAnterior = label_bc.getIcon();
    if (iconoAnterior instanceof ImageIcon) {
        ((ImageIcon) iconoAnterior).getImage().flush();
    }

    // Generar código de barras
    BufferedImage imagenBarra = Util.generarCodigoBarra(Secuencial, txt_bc.getText(), Secuencial_Empresa);
    Image imagenBarraRedimensionada = imagenBarra.getScaledInstance(
        label_bc.getWidth(), label_bc.getHeight(), Image.SCALE_SMOOTH
    );
    label_bc.setIcon(new ImageIcon(imagenBarraRedimensionada));
}

        
    
    }
    
    private void label_qrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_qrMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_label_qrMouseClicked

    private void txt_bcKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bcKeyReleased

   Dibujar_Codigos();

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bcKeyReleased

    private void txt_bcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bcActionPerformed

    private void comboProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboProveedorMouseClicked


        Util.llenarComboProveedor(comboProveedor, Secuencial_Empresa);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_comboProveedorMouseClicked

    private void comboCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCategoriaMouseClicked


        Util.llenar_Combo_Categoria(comboCategoria, Secuencial_Empresa);
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCategoriaMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
 this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.black);
        Util.llenarComboProveedor(comboProveedor, Secuencial_Empresa);
        Util.llenar_Combo_Categoria(comboCategoria, Secuencial_Empresa);

     
          setTitle("Monitux-POS v." + "");//V_Menu_Principal.VER);

          Dibujar_Codigos();



        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void label_bcMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_label_bcMouseWheelMoved


        if (label_bc.getIcon() instanceof ImageIcon icono) {
    Image imagen = icono.getImage();
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
        new Transferable() {
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] { DataFlavor.imageFlavor };
            }
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }
            public Object getTransferData(DataFlavor flavor) {
                return imagen;
            }
        }, null
    );
}


        // TODO add your handling code here:
    }//GEN-LAST:event_label_bcMouseWheelMoved

    private void label_qrMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_label_qrMouseWheelMoved

        
        
        if (label_qr.getIcon() instanceof ImageIcon icono) {
    Image imagen = icono.getImage();
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
        new Transferable() {
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] { DataFlavor.imageFlavor };
            }
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }
            public Object getTransferData(DataFlavor flavor) {
                return imagen;
            }
        }, null
    );
        }
        

        // TODO add your handling code here:
    }//GEN-LAST:event_label_qrMouseWheelMoved

    private void Menu_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_SalirActionPerformed

        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_SalirActionPerformed

    private void Menu_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_EliminarActionPerformed

        
        //**********************************************

    int res = JOptionPane.showConfirmDialog(
        null,
        "¿Está seguro de eliminar este producto?",
        "Confirmación",
        JOptionPane.YES_NO_OPTION
    );

    if (res == JOptionPane.YES_OPTION) {
        EntityManager em = null;

        try {
            // Liberar imágenes si existen
            if (labelImagen.getIcon() != null) {
                ((ImageIcon) labelImagen.getIcon()).getImage().flush();
                labelImagen.setIcon(null);
            }
            if (label_bc.getIcon() != null) {
                ((ImageIcon) label_bc.getIcon()).getImage().flush();
                label_bc.setIcon(null);
            }
            if (label_qr.getIcon() != null) {
                ((ImageIcon) label_qr.getIcon()).getImage().flush();
                label_qr.setIcon(null);
            }

            // Abrir nuevo EntityManager
            em = MonituxDBContext.getEntityManager();

            if (em == null || !em.isOpen()) {
                throw new IllegalStateException("EntityManager no disponible.");
            }

            Producto producto = em.createQuery(
                "SELECT p FROM Producto p WHERE p.Secuencial = :Secuencial", Producto.class)
                .setParameter("Secuencial", this.Secuencial)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (producto == null) {
                JOptionPane.showMessageDialog(null, "El producto no existe en la base de datos.");
                return;
            }

            Util.registrarActividad(
                Secuencial_Usuario,
                "Ha eliminado el producto: " + producto.getCodigo(),
                Secuencial_Empresa
            );

            if (!"Servicio".equals(producto.getTipo())) {
                Util.registrarMovimientoKardex(
                    producto.getSecuencial(),
                    producto.getCantidad(),
                    producto.getDescripcion(),
                    producto.getCantidad(),
                    producto.getPrecio_Costo(),
                    producto.getPrecio_Venta(),
                    "Salida",
                    Secuencial_Empresa
                );
            }

            // Reabrir EntityManager antes de eliminar
            if (em != null && em.isOpen()) {
                em.close();
            }
            em = MonituxDBContext.getEntityManager();

            em.getTransaction().begin();
            em.remove(em.contains(producto) ? producto : em.merge(producto));
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");

            if (onProductoEditado != null) {
                onProductoEditado.run();
            }

            this.dispose();
            System.out.println("✅ Producto eliminado correctamente.");

        } catch (Exception ex) {
            System.err.println("❌ Error al eliminar producto: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


        
//**********************************************
    }//GEN-LAST:event_Menu_EliminarActionPerformed

    private void Menu_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_GuardarActionPerformed

     EntityManager em = null;

    try {
        em = MonituxDBContext.getEntityManager();

        boolean esServicio = "Servicio".equals(jComboBox3.getSelectedItem());
        boolean tipoSeleccionado = jComboBox3.getSelectedIndex() != -1;
        boolean proveedorValido = comboProveedor.getSelectedItem() != null;
        boolean categoriaValida = comboCategoria.getSelectedItem() != null;

        if (!tipoSeleccionado || (!esServicio && (!proveedorValido || !categoriaValida))) {
            JOptionPane.showMessageDialog(null, "Faltan campos obligatorios.");
            return;
        }

        Producto producto = em.createQuery(
            "SELECT p FROM Producto p WHERE p.Secuencial = :secuencial AND p.Secuencial_Empresa = :empresa",
            Producto.class)
            .setParameter("secuencial", this.Secuencial)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultStream()
            .findFirst()
            .orElse(new Producto());

        boolean esNuevo = producto.getSecuencial() == 0;

        producto.setSecuencial_Empresa(Secuencial_Empresa);
        producto.setCodigo(txt_Codigo.getText());
        producto.setDescripcion(txt_Descripcion.getText());
        producto.setMarca(txt_Marca.getText());
        producto.setCodigo_Barra(txt_bc.getText());
        producto.setCodigo_Fabricante(txt_CodigoFabricante.getText());
        producto.setTipo(jComboBox3.getSelectedItem().toString());
        producto.setExpira(jCheckBox1.isSelected());

        LocalDate fechaCaducidad = datePicker1.getDate();
        producto.setFecha_Caducidad(jCheckBox1.isSelected() && fechaCaducidad != null
            ? fechaCaducidad.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            : null);

        try {
            producto.setPrecio_Venta(Double.parseDouble(txt_PrecioVenta.getText().replace(',', '.')));
            producto.setPrecio_Costo(Double.parseDouble(txt_PrecioCosto.getText().replace(',', '.')));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Precio inválido.");
            return;
        }

        double cantidadAnterior = producto.getCantidad();

        if (!esServicio) {
            try {
                producto.setCantidad(Double.parseDouble(txt_Cantidad.getText().replace(',', '.')));
                producto.setExistencia_Minima(Double.parseDouble(txt_ExistenciaMinima.getText().replace(',', '.')));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad o existencia mínima inválida.");
                return;
            }

            String proveedorRaw = comboProveedor.getSelectedItem().toString();
            String categoriaRaw = comboCategoria.getSelectedItem().toString();

            if (!proveedorRaw.contains("-") || !categoriaRaw.contains("-")) {
                JOptionPane.showMessageDialog(null, "Formato de proveedor o categoría inválido.");
                return;
            }

            producto.setSecuencial_Proveedor(Integer.parseInt(proveedorRaw.split("-")[0].trim()));
            producto.setSecuencial_Categoria(Integer.parseInt(categoriaRaw.split("-")[0].trim()));
        } else {
            producto.setCantidad(0);
            producto.setExistencia_Minima(0);
            producto.setSecuencial_Categoria(0);
            producto.setSecuencial_Proveedor(0);
        }

        em.getTransaction().begin();
        if (esNuevo) {
            em.persist(producto);
        }
        em.getTransaction().commit();

        if (imagen != null && imagen.length > 0) {
            em.getTransaction().begin();
            producto.setImagen(imagen);
            em.merge(producto);
            em.getTransaction().commit();
        }

        if (!esServicio) {
            double diferencia = producto.getCantidad() - cantidadAnterior;
            if (esNuevo || diferencia != 0) {
                String tipoMovimiento = esNuevo ? "Entrada" : (diferencia > 0 ? "Entrada" : "Salida");
                Util.registrarMovimientoKardex(
                    producto.getSecuencial(),
                    esNuevo ? 0 : cantidadAnterior,
                    producto.getDescripcion(),
                    esNuevo ? producto.getCantidad() : Math.abs(diferencia),
                    producto.getPrecio_Costo(),
                    producto.getPrecio_Venta(),
                    tipoMovimiento,
                    producto.getSecuencial_Empresa()
                );
            }
        }

        JOptionPane.showMessageDialog(null,
            esNuevo ? "Producto creado correctamente." : "Producto actualizado correctamente.");

        if (em.isOpen()) {
            Util.registrarActividad(
                Secuencial_Usuario,
                "Ha " + (esNuevo ? "creado" : "modificado") + " el producto: " + producto.getCodigo(),
                producto.getSecuencial_Empresa());
        }

        if (onProductoEditado != null) {
    new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() {
            onProductoEditado.run();
            return null;
        }
    }.execute();
}


        this.dispose();
        System.out.println("✅ Producto " + (esNuevo ? "creado" : "actualizado") + " correctamente.");

    } catch (Exception ex) {
        if (em != null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        JOptionPane.showMessageDialog(null, "❌ Error al guardar el producto: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
       
    }
    }//GEN-LAST:event_Menu_GuardarActionPerformed

    private void Menu_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_NuevoActionPerformed

        Secuencial=0;
        txt_Codigo.setText("");
        //        txt_Nombre.setText("");
        //        txt_Direccion.setText("");
        //        txt_Telefono.setText("");
        //        txt_Email.setText("");
        //        checkBoxActivo.setSelected(true);
        label_bc.setIcon(null);

        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_NuevoActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

     
  V_Proveedor proveedor = new V_Proveedor();
  proveedor.setVisible(true);
  proveedor.setDefaultCloseOperation(1);
//
//JDialog dialogo = new JDialog((Frame) null, "Proveedor", true);
//dialogo.setContentPane(proveedor.getContentPane());
//dialogo.setJMenuBar(proveedor.getJMenuBar()); // 👈 Esto preserva la barra de menús
//dialogo.pack();
//dialogo.setLocationRelativeTo(null);
//proveedor.primera_carga();
//dialogo.setVisible(true);
//dialogo.setResizable(false);

        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jCheckBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCheckBox1PropertyChange

       

 
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1PropertyChange

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged

         datePicker1.setVisible(jCheckBox1.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1StateChanged

    private void labelImagen1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelImagen1MouseClicked


        // Abrir ventana de captura
V_Captura_Imagen ventanaCamara = new V_Captura_Imagen(Secuencial, "Capturando...");
ventanaCamara.setModal(true);
ventanaCamara.setVisible(true);

// Obtener imagen capturada
BufferedImage imagenCapturada = V_Captura_Imagen.getImagen();

if (imagenCapturada != null) {
    // Liberar imagen anterior si existe
    if (labelImagen.getIcon() != null) {
        labelImagen.setIcon(null);
    }

    // Mostrar imagen redimensionada en JLabel
    Image imagenEscalada = imagenCapturada.getScaledInstance(
        labelImagen.getWidth(),
        labelImagen.getHeight(),
        Image.SCALE_SMOOTH
    );
    labelImagen.setIcon(new ImageIcon(imagenEscalada));

    // Convertir a byte[] (sin compresión personalizada)
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
        ImageIO.write(imagenCapturada, "png", baos); // Usa "jpg" si deseas compresión
        imagen = baos.toByteArray(); // Variable byte[] para guardar la imagen
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al procesar la imagen capturada.", "Error", JOptionPane.ERROR_MESSAGE);
    }

} else {
    JOptionPane.showMessageDialog(null, "No se pudo capturar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
}



        // TODO add your handling code here:
    }//GEN-LAST:event_labelImagen1MouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed



// Abrir ventana V_Producto en modo edición
V_Categoria x = new V_Categoria();
x.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
x.setVisible(true);

// Cierre automático para pruebas (opcional)
//Timer timer = new Timer(5000, e -> x.dispose()); // Cierra en 5 segundos
//timer.setRepeats(false);
//timer.start();

        
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void txt_CantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CantidadKeyReleased



        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CantidadKeyReleased

    private void txt_PrecioCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PrecioCostoKeyReleased

   

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PrecioCostoKeyReleased

    private void txt_PrecioVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PrecioVentaKeyReleased


    

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PrecioVentaKeyReleased

    private void txt_ExistenciaMinimaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ExistenciaMinimaKeyReleased

    
        
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ExistenciaMinimaKeyReleased

    private void txt_CantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CantidadKeyPressed


        

        txt_Cantidad.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        // Permitir solo dígitos, retroceso y punto
        if (!Character.isDigit(c) && c != '.' && c != '\b') {
            e.consume(); // Bloquea el carácter
        }

        // Solo un punto decimal permitido
        if (c == '.' && txt_Cantidad.getText().contains(".")) {
            e.consume(); // Bloquea el segundo punto
        }
    }
});


        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CantidadKeyPressed

    private void txt_PrecioCostoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PrecioCostoKeyPressed
     
        
        txt_PrecioCosto.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        // Permitir solo dígitos, retroceso y punto
        if (!Character.isDigit(c) && c != '.' && c != '\b') {
            e.consume(); // Bloquea el carácter
        }

        // Solo un punto decimal permitido
        if (c == '.' && txt_PrecioCosto.getText().contains(".")) {
            e.consume(); // Bloquea el segundo punto
        }
    }
});
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PrecioCostoKeyPressed

    private void txt_PrecioVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PrecioVentaKeyPressed
       
        
        txt_PrecioVenta.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        // Permitir solo dígitos, retroceso y punto
        if (!Character.isDigit(c) && c != '.' && c != '\b') {
            e.consume(); // Bloquea el carácter
        }

        // Solo un punto decimal permitido
        if (c == '.' && txt_PrecioVenta.getText().contains(".")) {
            e.consume(); // Bloquea el segundo punto
        }
    }
});
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PrecioVentaKeyPressed

    private void txt_ExistenciaMinimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ExistenciaMinimaKeyPressed

            
        
            txt_ExistenciaMinima.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        // Permitir solo dígitos, retroceso y punto
        if (!Character.isDigit(c) && c != '.' && c != '\b') {
            e.consume(); // Bloquea el carácter
        }

        // Solo un punto decimal permitido
        if (c == '.' && txt_ExistenciaMinima.getText().contains(".")) {
            e.consume(); // Bloquea el segundo punto
        }
    }
});

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ExistenciaMinimaKeyPressed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

        Dibujar_Codigos();

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void comboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboProveedorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new V_Producto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Menu_Eliminar;
    private javax.swing.JMenuItem Menu_Guardar;
    private javax.swing.JMenuItem Menu_Nuevo;
    private javax.swing.JMenuItem Menu_Salir;
    private javax.swing.JComboBox<String> comboCategoria;
    private javax.swing.JComboBox<String> comboProveedor;
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelImagen1;
    private javax.swing.JLabel label_bc;
    private javax.swing.JLabel label_qr;
    private javax.swing.JTextField txt_Cantidad;
    private javax.swing.JTextField txt_Codigo;
    private javax.swing.JTextField txt_CodigoFabricante;
    private javax.swing.JTextArea txt_Descripcion;
    private javax.swing.JTextField txt_ExistenciaMinima;
    private javax.swing.JTextField txt_Marca;
    private javax.swing.JTextField txt_PrecioCosto;
    private javax.swing.JTextField txt_PrecioVenta;
    private javax.swing.JTextField txt_bc;
    // End of variables declaration//GEN-END:variables
}
