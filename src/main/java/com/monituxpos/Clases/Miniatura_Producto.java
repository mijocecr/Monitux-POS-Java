/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;



import com.monituxpos.Ventanas.V_Captura_Imagen;
import com.monituxpos.Ventanas.V_Factura_Venta;
import com.monituxpos.Ventanas.V_Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Miniatura_Producto extends JPanel {

    public JLabel cantidadLabel;
    public Producto producto;
    public JLabel imagenLabel;
    public JLabel codigoLabel;
    public JLabel marcaLabel;
    public JLabel precioLabel;
    public JPanel infoPanel;
    public ImageIcon imagenRedimensionada;
    public double cantidadSelecccion;
    public int cantidadSelecccionItem;
    public double unidadesAgregar;
    
    public double Cantidad;

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double Cantidad) {
        this.Cantidad = Cantidad;
    }
    
    
    public double unidadesRetirar;
    
    public int Secuencial_Usuario=1;//Cambiar Esto
    public int Secuencial_Empresa;
    public int Secuencial_Proveedor;
    public int Secuencial_Categoria;
    public String Codigo;
    public String Codigo_Fabricante;
    public String Marca;
    public String Codigo_Barra;
    public String Descripcion;
    public String Tipo;
public byte[] Imagen;
public boolean Expira;

    public boolean isExpira() {
        return Expira;
    }

    public void setExpira(boolean Expira) {
        this.Expira = Expira;
    }

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] Imagen) {
        this.Imagen = Imagen;
    }
    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
    
public String Fecha_Caducidad;

    public String getFecha_Caducidad() {
        return Fecha_Caducidad;
    }

    public void setFecha_Caducidad(String Fecha_Caducidad) {
        this.Fecha_Caducidad = Fecha_Caducidad;
    }
    public int Secuencial;

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }
    
    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int Secuencial_Empresa) {
        this.Secuencial_Empresa = Secuencial_Empresa;
    }

    public int getSecuencial_Proveedor() {
        return Secuencial_Proveedor;
    }

    public void setSecuencial_Proveedor(int Secuencial_Proveedor) {
        this.Secuencial_Proveedor = Secuencial_Proveedor;
    }

    public int getSecuencial_Categoria() {
        return Secuencial_Categoria;
    }

    public void setSecuencial_Categoria(int Secuencial_Categoria) {
        this.Secuencial_Categoria = Secuencial_Categoria;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getCodigo_Fabricante() {
        return Codigo_Fabricante;
    }

    public void setCodigo_Fabricante(String Codigo_Fabricante) {
        this.Codigo_Fabricante = Codigo_Fabricante;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getCodigo_Barra() {
        return Codigo_Barra;
    }

    public void setCodigo_Barra(String Codigo_Barra) {
        this.Codigo_Barra = Codigo_Barra;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public Double getPrecio_Venta() {
        return Precio_Venta;
    }

    public void setPrecio_Venta(Double Precio_Venta) {
        this.Precio_Venta = Precio_Venta;
    }

    public Double getPrecio_Costo() {
        return Precio_Costo;
    }

    public void setPrecio_Costo(Double Precio_Costo) {
        this.Precio_Costo = Precio_Costo;
    }

    public Double getExistencia_Minima() {
        return Existencia_Minima;
    }

    public void setExistencia_Minima(Double Existencia_Minima) {
        this.Existencia_Minima = Existencia_Minima;
    }
    public Double Precio_Venta;
    public Double Precio_Costo;
    public Double Existencia_Minima;
    
    
    
    
    public Miniatura_Producto(Producto producto,boolean es_Compra) {
        this.producto = producto;
        setLayout(new BorderLayout());

        int ancho = 120;
        int alto = 110;

        try {
            byte[] datosImagen = producto.getImagen();
            if (datosImagen != null && datosImagen.length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(datosImagen);
                BufferedImage bufferedImage = ImageIO.read(bis);
                Image imagenEscalada = bufferedImage.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                imagenRedimensionada = new ImageIcon(imagenEscalada);
            } else {
                ImageIcon iconoPorDefecto = new ImageIcon(getClass().getResource("/icons/no-image-icon-10.png"));
                Image imagenEscalada = iconoPorDefecto.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                imagenRedimensionada = new ImageIcon(imagenEscalada);
            }
        } catch (Exception e) {
            e.printStackTrace();
            imagenRedimensionada = new ImageIcon(new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB));
        }

        imagenLabel = new JLabel(imagenRedimensionada);
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenLabel.setPreferredSize(new Dimension(ancho, alto));

        codigoLabel = new JLabel(producto.getCodigo());
        codigoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        codigoLabel.setFont(new Font("Arial", Font.BOLD, 12));

        marcaLabel = new JLabel("Marca: " + producto.getMarca());
        if(es_Compra==false){
        precioLabel = new JLabel("Precio: " + producto.getPrecio_Venta());
        }else{
        precioLabel = new JLabel("Precio: " + producto.getPrecio_Costo());
        }
        
        cantidadLabel = new JLabel("Stock: " + producto.getCantidad() + "   [Min: " + producto.getExistencia_Minima() + "]");

        infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.add(codigoLabel);
        infoPanel.add(marcaLabel);
        infoPanel.add(precioLabel);
        infoPanel.add(cantidadLabel);
        infoPanel.setBackground(Color.white);

        add(imagenLabel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        actualizarColor(producto.getCantidad());
    }

    public Miniatura_Producto() {
       
    }

    public void actualizarColor(double cantidadActual) {
        if ("Servicio".equalsIgnoreCase(producto.getTipo())) {
            setBackground(Color.WHITE);
            repaint();
            return;
        }

        double existenciaMinima = producto.getExistencia_Minima();

        if (cantidadActual < existenciaMinima) {
            setBackground(new Color(255, 102, 102));
            infoPanel.setBackground(new Color(255, 102, 102));
        } else if (cantidadActual > existenciaMinima) {
            setBackground(new Color(144, 238, 144));
            infoPanel.setBackground(new Color(144, 238, 144));
        } else {
            setBackground(new Color(255, 255, 153));
            infoPanel.setBackground(new Color(255, 255, 153));
        }

        repaint();
    }

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int Secuencial_Usuario) {
        this.Secuencial_Usuario = Secuencial_Usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    // ✅ Método que faltaba
    public void setCantidadSelecccion(double cantidad) {
        this.cantidadSelecccion = cantidad;
    }

    // Métodos existentes no modificados
    public JLabel getImagenLabel() { return imagenLabel; }
    public void setImagenLabel(JLabel imagenLabel) { this.imagenLabel = imagenLabel; }
    public JLabel getCodigoLabel() { return codigoLabel; }
    public void setCodigoLabel(JLabel codigoLabel) { this.codigoLabel = codigoLabel; }
    public JLabel getMarcaLabel() { return marcaLabel; }
    public void setMarcaLabel(JLabel marcaLabel) { this.marcaLabel = marcaLabel; }
    public JLabel getCantidadLabel() { return cantidadLabel; }
    public void setCantidadLabel(JLabel cantidadLabel) { this.cantidadLabel = cantidadLabel; }
    public JLabel getPrecioLabel() { return precioLabel; }
    public void setPrecioLabel(JLabel precioLabel) { this.precioLabel = precioLabel; }
    public JPanel getInfoPanel() { return infoPanel; }
    public void setInfoPanel(JPanel infoPanel) { this.infoPanel = infoPanel; }
    public ImageIcon getImagenRedimensionada() { return imagenRedimensionada; }
    public void setImagenRedimensionada(ImageIcon imagenRedimensionada) { this.imagenRedimensionada = imagenRedimensionada; }
    public int getCantidadSelecccion() { return (int) cantidadSelecccion; }
    public void setCantidadSelecccionItem(int cantidadSelecccionItem) { this.cantidadSelecccionItem = cantidadSelecccionItem; }
    public String comentario;
    
    
    
    public String getComentario() {
    String respuesta = "";

    // Mostrar cuadro de diálogo para ingresar comentario
    respuesta = JOptionPane.showInputDialog(null, "Escriba el comentario asociado a: " + this.producto.getCodigo(), "Comentario...", JOptionPane.PLAIN_MESSAGE);

    if (respuesta != null) {
        respuesta = respuesta.trim();
        this.comentario=respuesta;
    }

    // Mostrar mensaje de confirmación
    JOptionPane.showMessageDialog(null, respuesta, "Comentario", JOptionPane.INFORMATION_MESSAGE);

    
    return respuesta;
}

    
    
    
    public void Agregar_Comentario(String comentario) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager entityManager = emf.createEntityManager();

    try {
        entityManager.getTransaction().begin();

        Comentario comentarioFiltrado = entityManager.createQuery(
            "SELECT c FROM Comentario c WHERE c.Secuencial_Producto = :producto AND c.Secuencial_Empresa = :empresa",
            Comentario.class
        )
        .setParameter("producto", this.producto.getSecuencial())
        .setParameter("empresa", this.producto.getSecuencial_Empresa())
        .getResultStream()
        .findFirst()
        .orElse(null);

        if (comentarioFiltrado != null) {
            comentarioFiltrado.setContenido(comentario);
        } else {
            Comentario nuevoComentario = new Comentario();
            nuevoComentario.setSecuencial_Producto(this.producto.getSecuencial());
            nuevoComentario.setContenido(comentario);
            nuevoComentario.setSecuencial_Empresa(this.producto.getSecuencial_Empresa());

            entityManager.persist(nuevoComentario);
            System.out.println("Comentario agregado.");
        }

        entityManager.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
        entityManager.getTransaction().rollback();
    } finally {
        entityManager.close();
        emf.close();
    }
}

    
    
    
    
    public String cargarComentario() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager entityManager = emf.createEntityManager();

    String comentario = "";

    try {
        Comentario comentarioFiltrado = entityManager.createQuery(
            "SELECT c FROM Comentario c WHERE c.Secuencial_Producto = :producto AND c.Secuencial_Empresa = :empresa",
            Comentario.class
        )
        .setParameter("producto", this.producto.getSecuencial())
        .setParameter("empresa", this.producto.getSecuencial_Empresa())
        .getResultStream()
        .findFirst()
        .orElse(null);

        if (comentarioFiltrado != null) {
            comentario = comentarioFiltrado.getContenido();
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        entityManager.close();
        emf.close();
    }

    return comentario;
}

    
    
    public void actualizarProductoAgregarUnidades() {
    if ("Servicio".equalsIgnoreCase(this.producto.getTipo())) {
        JOptionPane.showMessageDialog(null, "No se pueden agregar unidades a un servicio.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager entityManager = emf.createEntityManager();

    try {
        entityManager.getTransaction().begin();

        Producto producto = entityManager.createQuery(
            "SELECT p FROM Producto p WHERE p.Secuencial = :secuencial AND p.Secuencial_Empresa = :empresa",
            Producto.class
        )
        .setParameter("secuencial", this.producto.getSecuencial())
        .setParameter("empresa", this.producto.getSecuencial_Empresa())
        .getResultStream()
        .findFirst()
        .orElse(null);

        if (producto != null) {
            Util.registrarMovimientoKardex(
                producto.getSecuencial(),
                producto.getCantidad(),
                producto.getDescripcion(),
                getUnidadesAgregar(),
                producto.getPrecio_Costo(),
                producto.getPrecio_Venta(),
                "Entrada",
                this.producto.getSecuencial_Empresa()
            );

            producto.setCantidad(producto.getCantidad() + unidadesAgregar);
            entityManager.merge(producto);
            entityManager.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "Se han agregado " + unidadesAgregar + " unidades al producto: " + this.producto.getCodigo(), "Agregar Unidades", JOptionPane.INFORMATION_MESSAGE);
            

            Util.registrarActividad(
                this.Secuencial_Usuario,
                "Ha agregado " + unidadesAgregar + " unidades al producto: " + this.producto.getCodigo(),
                this.producto.getSecuencial_Empresa()
            );
        } else {
            entityManager.getTransaction().rollback();
        }

    } catch (Exception e) {
        e.printStackTrace();
        entityManager.getTransaction().rollback();
    } finally {
        entityManager.close();
        emf.close();
    }
}

    
   
    
    
    
    public double getUnidadesAgregar() {
    String respuesta = JOptionPane.showInputDialog(
        null,
        "Escriba la cantidad en números de unidades a Agregar",
        "Agregar Unidades",
        JOptionPane.PLAIN_MESSAGE
    );

    if (respuesta != null) {
        respuesta = respuesta.trim();

        try {
            int numero = Integer.parseInt(respuesta);
            unidadesAgregar = numero;
            return unidadesAgregar;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error: Solo se permiten números.",
                "Agregar Unidades",
                JOptionPane.ERROR_MESSAGE
            );
            return 0;
        }
    } else {
        JOptionPane.showMessageDialog(
            null,
            "Error: Solo se permiten números.",
            "Agregar Unidades",
            JOptionPane.ERROR_MESSAGE
        );
        return 0;
    }
}

    
    
    
    public void actualizarProductoRetirarUnidades() {
    if ("Servicio".equalsIgnoreCase(this.producto.getTipo())) {
        JOptionPane.showMessageDialog(null, "No se pueden retirar unidades a un servicio.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager entityManager = emf.createEntityManager();

    try {
        entityManager.getTransaction().begin();

        Producto producto = entityManager.createQuery(
            "SELECT p FROM Producto p WHERE p.Secuencial = :secuencial AND p.Secuencial_Empresa = :empresa",
            Producto.class
        )
        .setParameter("secuencial", this.producto.getSecuencial())
        .setParameter("empresa", this.producto.getSecuencial_Empresa())
        .getResultStream()
        .findFirst()
        .orElse(null);

        if (producto != null) {
            Util.registrarMovimientoKardex(
                producto.getSecuencial(),
                producto.getCantidad(),
                producto.getDescripcion(),
                getUnidadesRetirar(),
                producto.getPrecio_Costo(),
                producto.getPrecio_Venta(),
                "Salida",
                this.producto.getSecuencial_Empresa()
            );

            producto.setCantidad(producto.getCantidad() - unidadesRetirar);
            entityManager.merge(producto);
            entityManager.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "Se han retirado " + unidadesRetirar + " unidades al producto: " + this.producto.getCodigo(), "Retirar Unidades", JOptionPane.INFORMATION_MESSAGE);

            Util.registrarActividad(
                this.Secuencial_Usuario,
                "Ha retirado " + unidadesRetirar + " unidades al producto: " + this.producto.getCodigo(),
                this.producto.getSecuencial_Empresa()
            );
        } else {
            entityManager.getTransaction().rollback();
        }

    } catch (Exception e) {
        e.printStackTrace();
        entityManager.getTransaction().rollback();
    } finally {
        entityManager.close();
        emf.close();
    }
}

    
    
    public double getUnidadesRetirar() {
    String respuesta = JOptionPane.showInputDialog(
        null,
        "Escriba la cantidad en números de unidades a Retirar",
        "Retirar Unidades",
        JOptionPane.PLAIN_MESSAGE
    );

    if (respuesta != null) {
        respuesta = respuesta.trim();

        try {
            int numero = Integer.parseInt(respuesta);
            unidadesRetirar = numero;
            return unidadesRetirar;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error: Solo se permiten números.",
                "Retirar Unidades",
                JOptionPane.ERROR_MESSAGE
            );
            return 0;
        }
    } else {
        JOptionPane.showMessageDialog(
            null,
            "Error: Solo se permiten números.",
            "Retirar Unidades",
            JOptionPane.ERROR_MESSAGE
        );
        return 0;
    }
}

    
    
    
   public void actualizarImagenLocal() {
    try {
        // Seleccionar archivo
        String rutaSeleccionada = Util.abrirDialogoSeleccionFilename(); // Método personalizado con JFileChooser

        if (rutaSeleccionada == null || rutaSeleccionada.trim().isEmpty()) {
            return;
        }

        // Cargar imagen original desde archivo
        File archivo = new File(rutaSeleccionada);
        BufferedImage imagenOriginal = ImageIO.read(archivo);

        if (imagenOriginal == null) {
            JOptionPane.showMessageDialog(null, "Formato de imagen no soportado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Clonar imagen para evitar bloqueo del archivo
        BufferedImage clon = new BufferedImage(
            imagenOriginal.getWidth(),
            imagenOriginal.getHeight(),
            BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g2d = clon.createGraphics();
        g2d.drawImage(imagenOriginal, 0, 0, null);
        g2d.dispose();

        // Mostrar imagen redimensionada en el JLabel
        Image imagenEscalada = clon.getScaledInstance(imagenLabel.getWidth(), imagenLabel.getHeight(), Image.SCALE_SMOOTH);
        imagenLabel.setIcon(new ImageIcon(imagenEscalada));

        // Convertir imagen clonada a byte[] en formato PNG (sin compresión personalizada)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(clon, "png", baos);
        byte[] imagenSinCompresion = baos.toByteArray();

        // Guardar en base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
        EntityManager em = emf.createEntityManager();

        Producto producto = em.createQuery(
            "SELECT p FROM Producto p WHERE p.Secuencial = :secuencial", Producto.class)
            .setParameter("secuencial", this.producto.getSecuencial())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (producto != null) {
            producto.setImagen(imagenSinCompresion);
            

            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "Imagen actualizada con éxito", "Listo", JOptionPane.INFORMATION_MESSAGE);
        }

        em.close();
        emf.close();

    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al actualizar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    public void actualizarImagenWeb() {
    try {
        // Solicitar URL al usuario
        String url = JOptionPane.showInputDialog(null, "Pega la URL de la imagen:", "Imagen desde la web", JOptionPane.PLAIN_MESSAGE);

        if (url == null || url.trim().isEmpty()) {
            return;
        }

        // Cargar imagen desde la web
        BufferedImage imagenWeb = Util.cargarImagenDesdeUrl(url); // Método personalizado que devuelve BufferedImage

        if (imagenWeb != null) {
            // Clonar imagen para evitar bloqueo
            BufferedImage clon = new BufferedImage(imagenWeb.getWidth(), imagenWeb.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = clon.createGraphics();
            g2d.drawImage(imagenWeb, 0, 0, null);
            g2d.dispose();

            // Mostrar imagen redimensionada en JLabel
            Image imagenEscalada = clon.getScaledInstance(imagenLabel.getWidth(), imagenLabel.getHeight(), Image.SCALE_SMOOTH);
            imagenLabel.setIcon(new ImageIcon(imagenEscalada));

            // Convertir imagen clonada a byte[] en formato PNG
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(clon, "png", baos);
            byte[] imagenBytes = baos.toByteArray();

            // Guardar en base de datos
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
            EntityManager em = emf.createEntityManager();

            Producto producto = em.createQuery(
                "SELECT p FROM Producto p WHERE p.Secuencial = :secuencial", Producto.class)
                .setParameter("secuencial", this.producto.getSecuencial())
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (producto != null) {
                producto.setImagen(imagenBytes);
                

                em.getTransaction().begin();
                em.merge(producto);
                em.getTransaction().commit();

                JOptionPane.showMessageDialog(null, "Imagen actualizada con éxito", "Listo", JOptionPane.INFORMATION_MESSAGE);
            }

            em.close();
            emf.close();
        }

    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al cargar la imagen desde la web.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al actualizar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    
    public void actualizarImagenCamara() {
    try {
        
        imagenLabel.setIcon(null); // Limpiar imagen actual

        // Abrir ventana de captura
        V_Captura_Imagen ventanaCamara = new V_Captura_Imagen(this.producto.getSecuencial(), this.producto.getCodigo());
        ventanaCamara.setModal(true);
        ventanaCamara.setVisible(true);

        // Obtener imagen capturada
        BufferedImage imagenCapturada = V_Captura_Imagen.getImagen();

        if (imagenCapturada != null) {
            // Mostrar imagen redimensionada en JLabel
            Image imagenEscalada = imagenCapturada.getScaledInstance(
                imagenLabel.getWidth(),
                imagenLabel.getHeight(),
                Image.SCALE_SMOOTH
            );
            imagenLabel.setIcon(new ImageIcon(imagenEscalada));

            // Convertir imagen a byte[] en formato PNG
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagenCapturada, "png", baos);
            byte[] imagenBytes = baos.toByteArray();

            // Guardar en base de datos
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
            EntityManager em = emf.createEntityManager();

            Producto producto = em.createQuery(
                "SELECT p FROM Producto p WHERE p.Secuencial = :secuencial", Producto.class)
                .setParameter("secuencial", this.producto.getSecuencial())
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (producto != null) {
                producto.setImagen(imagenBytes);

                em.getTransaction().begin();
                em.merge(producto);
                em.getTransaction().commit();

                JOptionPane.showMessageDialog(null, "Imagen actualizada con éxito", "Listo", JOptionPane.INFORMATION_MESSAGE);
            }

            em.close();
            emf.close();
        }

    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al procesar la imagen capturada.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al actualizar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

 

   
  
    
   
    
    
    
    
    
}//Fin de Clase
