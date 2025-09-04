/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.NoticiasRSS;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Menu_Principal extends javax.swing.JFrame {
    
    public static int Secuencial_Empresa;
    public static int Secuencial_Usuario;
    public static String Acceso;
    public static String Nombre_Empresa;
    public static String Telefono_Empresa;
    public static String Direccion_Empresa;
    public static String Email;
    public static String  URL_RSS;

    
            private Timer cintaTimer;
private int titularesMostrados = 0;
private int maxTitularesPorCiclo = 20; // Esto Falta hacerlo dinamico
private int indiceActual = 0;
private List<String[]> titulares = new ArrayList<>();
    
    
    public static String getURL_RSS() {
        return URL_RSS;
    }

    public static void setURL_RSS(String URL_RSS) {
        V_Menu_Principal.URL_RSS = URL_RSS;
    }
public NoticiasRSS fuente= new NoticiasRSS();//Esta linea es

    public static int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public static void setSecuencial_Empresa(int Secuencial_Empresa) {
        V_Menu_Principal.Secuencial_Empresa = Secuencial_Empresa;
    }

    public static int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public static void setSecuencial_Usuario(int Secuencial_Usuario) {
        V_Menu_Principal.Secuencial_Usuario = Secuencial_Usuario;
    }

    public static String getAcceso() {
        return Acceso;
    }

    public static void setAcceso(String Acceso) {
        V_Menu_Principal.Acceso = Acceso;
    }

    public static String getNombre_Empresa() {
        return Nombre_Empresa;
    }

    public static void setNombre_Empresa(String Nombre_Empresa) {
        V_Menu_Principal.Nombre_Empresa = Nombre_Empresa;
    }

    public static String getTelefono_Empresa() {
        return Telefono_Empresa;
    }

    public static void setTelefono_Empresa(String Telefono_Empresa) {
        V_Menu_Principal.Telefono_Empresa = Telefono_Empresa;
    }

    public static String getDireccion_Empresa() {
        return Direccion_Empresa;
    }

    public static void setDireccion_Empresa(String Direccion_Empresa) {
        V_Menu_Principal.Direccion_Empresa = Direccion_Empresa;
    }

  
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(V_Menu_Principal.class.getName());

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String Email) {
        V_Menu_Principal.Email = Email;
    }

 


    /**
     * Creates new form V_Menu_Principal
     */






    public int getMaxTitularesPorCiclo() {
        return maxTitularesPorCiclo;
    }

    public void setMaxTitularesPorCiclo(int maxTitularesPorCiclo) {
        this.maxTitularesPorCiclo = maxTitularesPorCiclo;
    }


    
   private void iniciarCintaLED(JLabel lblTitular) {
    new SwingWorker<List<String[]>, Void>() {
        @Override
        protected List<String[]> doInBackground() {
            NoticiasRSS fuente = new NoticiasRSS();
            fuente.cargarTitularesRSS(URL_RSS);
            return fuente.getTitulares();
        }

        @Override
        protected void done() {
            try {
                List<String[]> titulares = get();
                if (titulares == null || titulares.isEmpty()) {
                    lblTitular.setText("No hay titulares disponibles.");
                    return;
                }

                final int[] indiceActual = {0};

                Timer cintaTimer = new Timer(30, e -> {
                    lblTitular.setLocation(lblTitular.getX() - 2, lblTitular.getY());

                    if (lblTitular.getX() + lblTitular.getWidth() < 0) {
                        String[] titular = titulares.get(indiceActual[0]);
                        String texto = titular[0];
                        String enlace = titular.length > 1 ? titular[1] : null;

                        // Cargar ícono en segundo plano
                        new SwingWorker<ImageIcon, Void>() {
                            @Override
                            protected ImageIcon doInBackground() {
                                try {
                                    URL url = getClass().getResource("/icons/news.png");
                                    if (url == null) return null;
                                    ImageIcon original = new ImageIcon(url);
                                    Image escalada = original.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                                    return new ImageIcon(escalada);
                                } catch (Exception ex) {
                                    System.out.println("Error cargando ícono: " + ex.getMessage());
                                    return null;
                                }
                            }

                            @Override
                            protected void done() {
                                try {
                                    ImageIcon icono = get();
                                    lblTitular.setIcon(icono);
                                    lblTitular.setIconTextGap(10);
                                } catch (Exception ex) {
                                    System.out.println("Error al aplicar ícono: " + ex.getMessage());
                                }
                            }
                        }.execute();

                        // Mostrar texto con estilo
                        lblTitular.setText(texto);
                        lblTitular.setForeground(Color.CYAN);
                        lblTitular.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        lblTitular.setSize(lblTitular.getPreferredSize());

                        // Posicionar fuera del panel por la derecha
                        Container contenedor = lblTitular.getParent();
                        int entradaDesdeDerecha = (contenedor != null) ? contenedor.getWidth() + 20 : 10;
                        lblTitular.setLocation(entradaDesdeDerecha, lblTitular.getY());

                        // Eliminar listeners anteriores
                        for (MouseListener ml : lblTitular.getMouseListeners()) {
                            lblTitular.removeMouseListener(ml);
                        }

                        // Añadir clic para abrir enlace
                        if (enlace != null) {
                            lblTitular.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    try {
                                        Desktop.getDesktop().browse(new URI(enlace));
                                    } catch (Exception ex) {
                                        System.out.println("No se pudo abrir el enlace: " + ex.getMessage());
                                    }
                                }
                            });
                        }

                        indiceActual[0] = (indiceActual[0] + 1) % titulares.size();
                    }
                });

                cintaTimer.start();

            } catch (Exception ex) {
                lblTitular.setText("Error al cargar titulares.");
                ex.printStackTrace();
            }
        }
    }.execute();
}
 

    public V_Menu_Principal() {
        
          initComponents();
        
 
      
     
     NoticiasRSS fuente = new NoticiasRSS();
fuente.cargarTitularesRSS(this.getURL_RSS());
titulares = fuente.getTitulares();


iniciarCintaLED(lblTitular);

  
    }
    
 
    
    private void mostrarSiguienteTitular(JLabel lblCinta) {
    if (titulares == null || titulares.isEmpty()) {
        lblCinta.setText("No hay titulares disponibles.");
        lblCinta.setIcon(null);
        lblCinta.setCursor(Cursor.getDefaultCursor());
        return;
    }

    String[] titular = titulares.get(indiceActual);
    String texto = titular[0];
    String enlace = titular[1];

    lblCinta.setText(texto); // subrayado visual
    lblCinta.setForeground(Color.cyan);
    
    

    lblCinta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // Cargar ícono si quieres
    ImageIcon icono = new ImageIcon(getClass().getResource("/icons/news.png"));
    Image imagenEscalada = icono.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    lblCinta.setIcon(new ImageIcon(imagenEscalada));
 
    lblCinta.setVerticalAlignment(SwingConstants.CENTER);

    lblCinta.setIconTextGap(10);

    // Posicionar fuera del panel por la derecha
    Container contenedor = lblCinta.getParent();
    int entradaDesdeDerecha = (contenedor != null) ? contenedor.getWidth() + 20 : 10;
    lblCinta.setSize(lblCinta.getPreferredSize());
    lblCinta.setLocation(entradaDesdeDerecha, lblCinta.getY());

    // Añadir acción de clic
    for (MouseListener ml : lblCinta.getMouseListeners()) {
        lblCinta.removeMouseListener(ml); // evitar duplicados
    }

    lblCinta.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                Desktop.getDesktop().browse(new URI(enlace));
            } catch (Exception ex) {
                System.out.println("No se pudo abrir el enlace: " + ex.getMessage());
            }
        }
    });

    indiceActual = (indiceActual + 1) % titulares.size();
}

    
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbl_Descripcion = new javax.swing.JLabel();
        lbl_Nombre_Empresa = new javax.swing.JLabel();
        lbl_version = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTitular = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelContenedor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jButton1.setText("Ventas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Compras");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(391, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        lbl_Descripcion.setForeground(new java.awt.Color(192, 255, 192));
        lbl_Descripcion.setText("Descripcion");

        lbl_Nombre_Empresa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_Nombre_Empresa.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Nombre_Empresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Nombre_Empresa.setText("Nombre de Empresa o Negocio");

        lbl_version.setForeground(new java.awt.Color(255, 51, 255));
        lbl_version.setText("Monitux-POS v.1.8");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Nombre_Empresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_version, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lbl_Nombre_Empresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_version))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(null);

        lblTitular.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblTitular.setForeground(new java.awt.Color(128, 255, 255));
        lblTitular.setText("Bienvenido a Monitux-POS — Edición Java");
        lblTitular.setToolTipText("");
        jPanel3.add(lblTitular);
        lblTitular.setBounds(810, 0, 456, 40);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        panelContenedor.setBackground(new java.awt.Color(0, 102, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Logo.png"))); // NOI18N

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel1)
                .addContainerGap(242, Short.MAX_VALUE))
        );
        panelContenedorLayout.setVerticalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(panelContenedor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        
        new SwingWorker<V_Factura_Venta, Void>() {
    @Override
    protected V_Factura_Venta doInBackground() {
        // Esto se ejecuta fuera del EDT
        return new V_Factura_Venta();
    }

    @Override
    protected void done() {
        try {
            V_Factura_Venta form = get();
            abrirVentana(form); // esto sí va en el EDT
        } catch (Exception ex) {
            System.out.println("Error al abrir ventana: " + ex.getMessage());
        }
    }
}.execute();

        

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.black);
        
        
      
        //Bloque de variables de prueba
//*********************************************
        this.setAcceso("Administrador");
       this.setSecuencial_Empresa(1);
        this.setDireccion_Empresa("Juticalpa, Olancho");
        this.setSecuencial_Usuario(1);
        this.setTelefono_Empresa("642883288");
        this.setNombre_Empresa("One Click Solutions");
        this.setEmail("Empresa@gmail.com");
        
        this.setMaxTitularesPorCiclo(20);
       
//***************************************************        
       lbl_Nombre_Empresa.setText(this.getNombre_Empresa());
          
      
    }//GEN-LAST:event_formWindowOpened

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed


             
        new SwingWorker<V_Factura_Compra, Void>() {
    @Override
    protected V_Factura_Compra doInBackground() {
        // Esto se ejecuta fuera del EDT
        return new V_Factura_Compra();
    }

    @Override
    protected void done() {
        try {
            V_Factura_Compra form = get();
            abrirVentana(form); // esto sí va en el EDT
        } catch (Exception ex) {
            System.out.println("Error al abrir ventana: " + ex.getMessage());
        }
    }
}.execute();



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new V_Menu_Principal().setVisible(true));
    }

    
    
     //*****************************
    
    
    private JPanel activePanel = null;

public void abrirVentana(JPanel childPanel) {
    if (activePanel != null) {
        panelContenedor.remove(activePanel);
    }

    activePanel = childPanel;
    childPanel.setVisible(true);
    childPanel.setOpaque(true);
    childPanel.setBorder(null);

    panelContenedor.removeAll();
    panelContenedor.setLayout(new BorderLayout());
    panelContenedor.add(childPanel, BorderLayout.CENTER);

    panelContenedor.revalidate();
    panelContenedor.repaint();
}

    
    
    
    //****************************
    

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitular;
    private javax.swing.JLabel lbl_Descripcion;
    private javax.swing.JLabel lbl_Nombre_Empresa;
    private javax.swing.JLabel lbl_version;
    private javax.swing.JPanel panelContenedor;
    // End of variables declaration//GEN-END:variables
}
