package com.monituxpos.Clases;


import java.awt.Dimension;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.lowagie.text.Cell;
import com.lowagie.text.Row;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.Image;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.Hashtable;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;
import com.monituxpos.Clases.*;
import com.monituxpos.Ventanas.V_Menu_Principal;

import com.monituxpos.Ventanas.VisorPDFBox;
import jakarta.persistence.EntityTransaction;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class Util {


    //Generar Codigo de Barra
    public static BufferedImage generarCodigoBarra(int secuencial, String codigo, int secuencialEmpresa) {
        try {
            int width = 300;
            int height = 200;

            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    codigo,
                    BarcodeFormat.CODE_128,
                    width,
                    height,
                    hints
            );

            BufferedImage imagen = MatrixToImageWriter.toBufferedImage(bitMatrix, new MatrixToImageConfig());

            // Guardar la imagen (opcional)
            // Path path = Paths.get("Resources/BC/" + secuencialEmpresa + "-BC-" + secuencial + ".png");
            // MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return imagen;

        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
    //Fin Generar Codigo de Barra


    //Enviar Correo


    public static void EnviarCorreoConPdf(
            String remitente,
            String destinatario,
            String asunto,
            String cuerpo,
            String rutaPdf,
            String smtpServidor,
            int puerto,
            final String usuario,
            final String contraseña
    ) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpServidor);
        props.put("mail.smtp.port", String.valueOf(puerto));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contraseña);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);

            // Parte del cuerpo del mensaje
            MimeBodyPart cuerpoTexto = new MimeBodyPart();
            cuerpoTexto.setText(cuerpo);

            // Parte del archivo adjunto
            MimeBodyPart adjunto = new MimeBodyPart();
            DataSource source = new FileDataSource(rutaPdf);
            adjunto.setDataHandler(new DataHandler(source));
            adjunto.setFileName(source.getName());

            // Combinar partes
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoTexto);
            multipart.addBodyPart(adjunto);

            mensaje.setContent(multipart);

            // Enviar el mensaje
            Transport.send(mensaje);

            System.out.println("✅ Correo enviado exitosamente.");
        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar el correo: " + e.getMessage());
        }
    }


    //Fin Enviar Correo

    
    
    public static String abrirDialogoSeleccionFilename() {
    JFileChooser fileChooser = new JFileChooser();

    // Opcional: puedes establecer un filtro si lo necesitas
    // fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));

    int resultado = fileChooser.showOpenDialog(null); // null para que se centre en pantalla

    if (resultado == JFileChooser.APPROVE_OPTION) {
        File archivoSeleccionado = fileChooser.getSelectedFile();
        return archivoSeleccionado.getAbsolutePath(); // Solo el nombre del archivo
    } else {
        return "";
    }
}
    
    
    
    //Cargar Imagen Local
    
     public static Image cargarImagenLocal(String ruta) {
        if (ruta != null && !ruta.trim().isEmpty()) {
            try {
                BufferedImage original = ImageIO.read(new File(ruta));

                // Clonar la imagen
                BufferedImage clon = new BufferedImage(
                        original.getWidth(),
                        original.getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );
                clon.getGraphics().drawImage(original, 0, 0, null);

                return clon;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null; // Retorna null si la ruta es nula o vacía
        }
    }
    
    //Fin Cargar Imagen Local
    
 //Comprimir Imagen
     
       public static byte[] comprimirImagen(BufferedImage imagenOriginal, float calidad) {
        if (imagenOriginal == null) return null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {

            // Buscar el codec JPEG
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
            if (!writers.hasNext()) {
                throw new IllegalStateException("No se encontró el codec JPEG.");
            }

            ImageWriter writer = writers.next();
            writer.setOutput(ios);

            // Configurar parámetros de compresión
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(calidad / 100f); // Convertir de 0–100 a 0.0–1.0
            }

            // Escribir la imagen comprimida
            writer.write(null, new javax.imageio.IIOImage(imagenOriginal, null, null), param);
            writer.dispose();

            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error al comprimir la imagen", e);
        }
    } 
       
     
     
// Fin Comprimir Imagen     

    //Generar Codigo QR
    public static BufferedImage generarCodigoQR(int secuencial, String codigo, int secuencialEmpresa) {
        try {
            int width = 300;
            int height = 300;

            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    codigo,
                    BarcodeFormat.QR_CODE,
                    width,
                    height,
                    hints
            );

            BufferedImage imagen = MatrixToImageWriter.toBufferedImage(bitMatrix, new MatrixToImageConfig());

            // Guardar la imagen (opcional)
            // Path path = Paths.get("Resources/Imagenes/QR-" + secuencial + "-" + codigo + ".png");
            // MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return imagen;

        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
//Fin Generar Codigo QR
    
  
 public static void registrarActividad(
    int secuencialUsuario,
    String descripcion,
    int secuencialEmpresa
) {
    EntityManager em = null;

    try {
        em = MonituxDBContext.getEntityManager();

        if (em == null || !em.isOpen()) {
            throw new IllegalStateException("EntityManager no disponible.");
        }

        EntityTransaction tx = em.getTransaction();

        if (!tx.isActive()) {
            tx.begin();
        }

        Actividad actividad = new Actividad();
        actividad.setSecuencial_Usuario(secuencialUsuario);
        actividad.setSecuencial_Empresa(secuencialEmpresa);
        actividad.setDescripcion(descripcion);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        actividad.setFecha(LocalDateTime.now().format(formatter));

        em.persist(actividad);

        tx.commit();
        System.out.println("✅ Actividad registrada correctamente.");

    } catch (Exception e) {
        try {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } catch (Exception rollbackEx) {
            System.err.println("⚠️ Error al hacer rollback: " + rollbackEx.getMessage());
        }

        System.err.println("❌ Error al registrar actividad: " + e.getMessage());
        e.printStackTrace();

    } finally {
        if (em != null && em.isOpen()) {
            em.close(); // Cierre seguro
        }
    }
}

     
    public static void redimensionarImagenEnLabel(JLabel label, ImageIcon imagenOriginal, int ancho, int alto) {
    if (imagenOriginal != null) {
        Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
        label.setIcon(imagenRedimensionada);
    } else {
        label.setIcon(null); // o puedes asignar una imagen por defecto
    }
}
    
    public static BufferedImage cargarImagenDesdeUrl(String url) throws IOException {
    return ImageIO.read(new URL(url));
}

    public static String fechaActualCompleta() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
    return LocalDateTime.now().format(formatter);
}

 
    
 
    
    public static void registrarMovimientoKardex(
    int secuencialProducto, double existencia,
    String descripcion, double cantidadUnidades,
    double costo, double venta,
    String movimiento, int secuencialEmpresa
) {
    if (cantidadUnidades < 0 || costo < 0 || venta < 0) {
        throw new IllegalArgumentException("Valores inválidos para movimiento de inventario.");
    }

    EntityManager em = null;
    EntityTransaction tx = null;

    try {
        em = MonituxDBContext.getEntityManager();
        if (em == null || !em.isOpen()) {
            throw new IllegalStateException("EntityManager no disponible.");
        }

        tx = em.getTransaction();
        tx.begin();

        double saldoNuevo = movimiento.equalsIgnoreCase("Entrada")
            ? existencia + cantidadUnidades
            : existencia - cantidadUnidades;

        Producto producto = em.find(Producto.class, secuencialProducto);
        if (producto == null) {
            throw new IllegalStateException("Producto no encontrado: " + secuencialProducto);
        }

        Kardex kardex = new Kardex();
        kardex.setProducto(producto);
        kardex.setSecuencial_Empresa(secuencialEmpresa); // ✅ si el setter se llama así en tu clase
        kardex.setDescripcion(descripcion);
        kardex.setCantidad(cantidadUnidades);
        kardex.setCosto(costo);
        kardex.setVenta(venta);
        kardex.setMovimiento(movimiento);
        kardex.setSaldo(saldoNuevo);
        kardex.setCosto_Total(Math.round(cantidadUnidades * costo * 100.0) / 100.0);
        kardex.setVenta_Total(Math.round(cantidadUnidades * venta * 100.0) / 100.0);
        kardex.setFecha(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        em.persist(kardex);
        tx.commit();

        System.out.println("✅ Movimiento registrado en Kardex correctamente.");
    } catch (Exception e) {
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
        System.err.println("❌ Error al registrar movimiento en Kardex: " + e.getMessage());
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

    public static List<ProductoTopVR> obtenerTopProductosVendidos(int cantidadTop) {
    EntityManager em = MonituxDBContext.getEntityManager();
    List<ProductoTopVR> resultado = new ArrayList<>();

    try {
        String jpql = """
            SELECT k.producto, SUM(k.cantidad)
            FROM Kardex k
            WHERE k.movimiento = :mov AND k.secuencial_empresa = :empresa
            GROUP BY k.producto
            ORDER BY SUM(k.cantidad) DESC
        """;

        List<Object[]> resumen = em.createQuery(jpql, Object[].class)
            .setParameter("mov", "Salida")
            .setParameter("empresa", V_Menu_Principal.getSecuencial_Empresa())
            .setMaxResults(cantidadTop)
            .getResultList();

        for (Object[] fila : resumen) {
            Producto producto = (Producto) fila[0];
            Number total = (Number) fila[1]; // Evita errores de casting

            ProductoTopVR top = new ProductoTopVR(
                producto.getSecuencial(),
                producto.getDescripcion(),
                producto.getCodigo(),
                total.doubleValue(),
                producto.getPrecio_Venta(),
                producto.getSecuencial_Empresa()
            );

            resultado.add(top);
        }
    } catch (Exception ex) {
        System.err.println("❌ Error al obtener productos top vendidos: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    return resultado;
}

    
    public static boolean existenUsuariosPorEmpresa(int secuencialEmpresa) {
    MonituxDBContext.ensureEntityManagerFactoryReady();
    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        Long count = em.createQuery(
            "SELECT COUNT(u) FROM Usuario u WHERE u.Secuencial_Empresa = :secuencialEmpresa", Long.class)
            .setParameter("secuencialEmpresa", secuencialEmpresa)
            .getSingleResult();

        return count > 0;
    } catch (Exception e) {
        System.err.println("❌ Error al verificar usuarios por empresa: " + e.getMessage());
        return false;
    }
}

    
  
    
    
    
    public static double redondear(double valor) {
    return Math.round(valor * 100.0) / 100.0;
}


  public static void llenarComboCliente(JComboBox<String> combo, int secuencial_Empresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Cliente> clientes = em.createQuery(
            "SELECT c FROM Cliente c WHERE c.Activo = true AND c.Secuencial_Empresa = :empresa", Cliente.class)
            .setParameter("empresa", secuencial_Empresa)
            .getResultList();

        for (Cliente c : clientes) {
            combo.addItem(c.getSecuencial() + " - " + c.getNombre());
        }

        System.out.println("✅ Clientes cargados correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar clientes: " + e.getMessage());
        e.printStackTrace();
    }
}

    
 public static void seleccionar_Por_Secuencial(JComboBox<String> combo, String secuencialBuscado) {
    if (secuencialBuscado == null || secuencialBuscado.isEmpty()) {
        System.out.println("⚠️ Secuencial buscado es nulo o vacío.");
        return;
    }

    String normalizado = secuencialBuscado.trim().replaceFirst("^0+(?!$)", "");
    System.out.println("🔍 Buscando secuencial normalizado: " + normalizado);

    for (int i = 0; i < combo.getItemCount(); i++) {
        String item = combo.getItemAt(i);
        System.out.println("🔎 Item[" + i + "]: " + item);

        if (item != null) {
            String[] partes = item.split(" -", 2);
            if (partes.length > 0) {
                String secuencialItem = partes[0].trim().replaceFirst("^0+(?!$)", "");
                System.out.println("➡️ Comparando con: " + secuencialItem);
                if (secuencialItem.equals(normalizado)) {
                    combo.setSelectedIndex(i);
                    System.out.println("✅ Seleccionado: " + item);
                    return;
                }
            }
        }
    }

    System.out.println("❌ No se encontró coincidencia para: " + secuencialBuscado);
}

  
  
  
  public static void llenarComboProveedor(JComboBox<String> combo, int secuencial_Empresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Proveedor> proveedores = em.createQuery(
            "SELECT p FROM Proveedor p WHERE p.Activo = true AND p.Secuencial_Empresa = :empresa", Proveedor.class)
            .setParameter("empresa", secuencial_Empresa)
            .getResultList();

        for (Proveedor p : proveedores) {
            combo.addItem(p.getSecuencial() + " - " + p.getNombre());
        }

        System.out.println("✅ Proveedores cargados correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar proveedores: " + e.getMessage());
        e.printStackTrace();
    }
}
  
  public static void llenarComboEmpresa(JComboBox<String> combo) {
    combo.removeAllItems(); // Limpiar combo

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Empresa> empresas = em.createQuery(
            "SELECT e FROM Empresa e WHERE e.Activa = true", Empresa.class)
            .getResultList();

        for (Empresa e : empresas) {
            combo.addItem(e.getSecuencial() + " - " + e.getNombre());
        }

        System.out.println("Empresas cargadas correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar empresas: " + e.getMessage());
        e.printStackTrace();
    }
}

  

    
    public static void llenarComboUsuario(JComboBox<String> combo, int secuencial_Empresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Usuario> usuarios = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.Activo = true AND u.Secuencial_Empresa = :empresa", Usuario.class)
            .setParameter("empresa", secuencial_Empresa)
            .getResultList();

        for (Usuario u : usuarios) {
            combo.addItem(u.getSecuencial() + " - " + u.getNombre());
        }

        System.out.println("✅ Usuarios cargados correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar usuarios: " + e.getMessage());
        e.printStackTrace();
    }
}

    
  public static void llenarComboClientePorTelefono(JComboBox<String> combo, String telefono, int secuencialEmpresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManager em = null;

    try {
        em = MonituxDBContext.getEntityManager();
        if (em == null || !em.isOpen()) {
            throw new IllegalStateException("EntityManager no disponible.");
        }

        String textoTelefono = telefono.trim().toLowerCase();
        if (textoTelefono.isEmpty()) {
            System.out.println("⚠️ Campo teléfono vacío, no se aplica filtro.");
            return;
        }

        List<Cliente> clientes = em.createQuery(
            "SELECT c FROM Cliente c WHERE c.Activo = true AND c.Secuencial_Empresa = :empresa AND LOWER(c.Telefono) LIKE :telefono",
            Cliente.class
        )
        .setParameter("empresa", secuencialEmpresa)
        .setParameter("telefono", "%" + textoTelefono + "%")
        .getResultList();

        if (clientes.isEmpty()) {
            System.out.println("⚠️ No se encontraron clientes con ese teléfono.");
            return;
        }

        for (Cliente item : clientes) {
            String texto = item.getSecuencial() + " - " + item.getNombre();
            combo.addItem(texto);
        }

        // Seleccionar el primero si existe
        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0);
        }

        System.out.println("✅ Clientes filtrados por teléfono cargados correctamente.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "❌ Error al filtrar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

  public static void llenarComboProveedorPorTelefono(JComboBox<String> combo, String telefono, int secuencialEmpresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManager em = null;

    try {
        em = MonituxDBContext.getEntityManager();
        if (em == null || !em.isOpen()) {
            throw new IllegalStateException("EntityManager no disponible.");
        }

        String textoTelefono = telefono != null ? telefono.trim().toLowerCase() : "";
        if (textoTelefono.isEmpty()) {
            System.out.println("⚠️ Teléfono vacío, no se aplica filtro.");
            return;
        }

        List<Proveedor> proveedores = em.createQuery(
            "SELECT p FROM Proveedor p WHERE p.Activo = true AND p.Secuencial_Empresa = :empresa AND LOWER(p.Telefono) LIKE :telefono",
            Proveedor.class
        )
        .setParameter("empresa", secuencialEmpresa)
        .setParameter("telefono", "%" + textoTelefono + "%")
        .getResultList();

        if (proveedores.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "No se encontró ningún proveedor con ese número de teléfono.",
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Proveedor item : proveedores) {
            String texto = item.getSecuencial() + " - " + item.getNombre();
            combo.addItem(texto);
        }

        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0); // Selecciona el primero si hay resultados
        }

        System.out.println("✅ Proveedores filtrados por teléfono cargados correctamente.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "❌ Error al filtrar proveedores: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

    
    
     public static void EnviarCorreoConPdfBytes(
            String remitente,
            String destinatario,
            String asunto,
            String cuerpo,
            byte[] pdfBytes,
            String smtpHost,
            int smtpPort,
            String usuario,
            String contraseña) {

        try {
            // Configuración del servidor SMTP
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", String.valueOf(smtpPort));

            // Autenticación
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, contraseña);
                }
            });

            // Crear el mensaje
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);

            // Parte del cuerpo
            MimeBodyPart cuerpoTexto = new MimeBodyPart();
            cuerpoTexto.setText(cuerpo, "utf-8");

            // Parte del adjunto
            MimeBodyPart adjunto = new MimeBodyPart();
            DataSource fuente = new ByteArrayDataSource(pdfBytes, "application/pdf");
            adjunto.setDataHandler(new DataHandler(fuente));
            adjunto.setFileName("Factura.pdf");

            // Combinar partes
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoTexto);
            multipart.addBodyPart(adjunto);

            mensaje.setContent(multipart);

            // Enviar
            Transport.send(mensaje);

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Error al enviar el correo: " + ex.getMessage(), "Correo", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     
     
     
     public static String convertNumberToWords(long number) {
    if (number == 0) return "cero";

    if (number < 0) return "menos " + convertNumberToWords(-number);

    StringBuilder result = new StringBuilder();

    if (number >= 1_000_000_000) {
        long milesDeMillones = number / 1_000_000_000;
        result.append(convertNumberToWords(milesDeMillones)).append(" mil millones");
        number %= 1_000_000_000;
        if (number > 0) result.append(" ");
    }

    if (number >= 1_000_000) {
        long millones = number / 1_000_000;
        result.append(millones == 1 ? "un millón" : convertNumberToWords(millones) + " millones");
        number %= 1_000_000;
        if (number > 0) result.append(" ");
    }

    if (number >= 1000) {
        long miles = number / 1000;
        result.append(miles == 1 ? "mil" : convertNumberToWords(miles) + " mil");
        number %= 1000;
        if (number > 0) result.append(" ");
    }

    if (number >= 100) {
        int centena = (int) (number / 100);
        if (centena == 1 && number % 100 == 0) {
            result.append("cien");
        } else {
            String[] centenas = {
                "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos",
                "seiscientos", "setecientos", "ochocientos", "novecientos"
            };
            result.append(centenas[centena]);
        }
        number %= 100;
        if (number > 0) result.append(" ");
    }

    if (number >= 20) {
        int decena = (int) (number / 10);
        int unidad = (int) (number % 10);
        String[] decenas = {
            "", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta",
            "setenta", "ochenta", "noventa"
        };
        result.append(decenas[decena]);
        if (unidad > 0) result.append(" y ").append(convertNumberToWords(unidad));
    } else if (number > 0) {
        String[] unidades = {
            "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve",
            "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete",
            "dieciocho", "diecinueve"
        };
        result.append(unidades[(int) number]);
    }

    return result.toString().toUpperCase();
}

     
     
 //******************************
     


    public static void exportarJTableAExcel(JTable table, String nombreHoja, String nombreArchivoBase) {
        try (Workbook workbook = new XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(nombreHoja);

            // 🔠 Estilo de encabezado
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            TableModel model = table.getModel();

            // 🔠 Encabezados
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int col = 0; col < model.getColumnCount(); col++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(col);
                cell.setCellValue(model.getColumnName(col));
                cell.setCellStyle(headerStyle);
            }

            // 📋 Filas
            for (int row = 0; row < model.getRowCount(); row++) {
                org.apache.poi.ss.usermodel.Row dataRow = sheet.createRow(row + 1);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    dataRow.createCell(col).setCellValue(value != null ? value.toString() : "");
                }
            }

            // 💾 Guardar archivo
            String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String nombreArchivo = nombreArchivoBase + "." + timestamp + ".xlsx";
            String ruta = Paths.get(System.getProperty("user.home"), "Desktop", nombreArchivo).toString();

            try (FileOutputStream out = new FileOutputStream(ruta)) {
                workbook.write(out);
            }

          //  V_Menu_Principal.MSG.showMSG("Exportado correctamente a:\n" + ruta, "Excel generado");

        } catch (IOException ex) {
           // V_Menu_Principal.MSG.showMSG("Error al exportar a Excel: " + ex.getMessage(), "Error");
        }
    }

 
     
 //****************************
 


    
    
    
    
    
    
    //***************************
       public static JPanel crearGraficoComprasVsVentas(double compras, double ventas) {
        // Crear dataset circular
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Compras", compras);
        dataset.setValue("Ventas", ventas);

        // Crear gráfico circular
        JFreeChart chart = ChartFactory.createPieChart(
            "Compras vs Ventas", // Título
            dataset,
            true,   // Mostrar leyenda
            true,   // Tooltips
            false   // URLs
        );

        // Panel con el gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(798, 182)); // Tamaño personalizado

        return chartPanel;
    }
    
//******************************
       
       
        public static JPanel Grafico_CTAS_CP(double cobrar, double pagar) {
        // Dataset horizontal
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(cobrar, "Cuentas por Cobrar", "Estado");
        dataset.addValue(pagar, "Cuentas por Pagar", "Estado");

        // Crear gráfico de barras horizontales
        JFreeChart chart = ChartFactory.createBarChart(
            "Cuentas por Cobrar vs Pagar",             // Título
            "Tipo de Cuenta",                          // Eje X (categoría)
            "Monto (" + V_Menu_Principal.getMoneda_Empresa() + ")", // Eje Y (valor)
            dataset,
            PlotOrientation.HORIZONTAL,                // 👈 Orientación horizontal
            true, true, false
        );

        // Panel con el gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(271, 150)); // Ajusta según tu layout

        return chartPanel;
    }
       
       
       
//******************************       
       

   
    
  public static JPanel crearGraficoCuentasPorPagarVsCobrar(double totalPorPagar, double totalPorCobrar) {
    // Dataset de barras
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    dataset.addValue(totalPorPagar, "Por Pagar", "Cuentas");
    dataset.addValue(totalPorCobrar, "Por Cobrar", "Cuentas");

    // Crear gráfico de barras
    JFreeChart chart = ChartFactory.createBarChart(
        "a Pagar vs a Cobrar (Mes Actual)", // Título
        "Categoría",                               // Eje X
        "Monto (" + V_Menu_Principal.getMoneda_Empresa() + ")", // Eje Y
        dataset
    );

    // Reducir tamaño del título
    chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 14)); // tamaño más pequeño

    // Personalizar colores y estilo
    CategoryPlot plot = chart.getCategoryPlot();
    BarRenderer renderer = (BarRenderer) plot.getRenderer();

    // Colores modernos
    renderer.setSeriesPaint(0, new Color(0xFF, 0x6B, 0x6B)); // Rojo coral
    renderer.setSeriesPaint(1, new Color(0x51, 0xCF, 0x66)); // Verde esmeralda

    // Fondo oscuro y ejes claros
    plot.setBackgroundPaint(new Color(0x1E, 0x1E, 0x1E)); // gris muy oscuro
    plot.setDomainGridlinePaint(new Color(0x55, 0x55, 0x55));
    plot.setRangeGridlinePaint(new Color(0x55, 0x55, 0x55));

    chart.setBackgroundPaint(new Color(0x17, 0x17, 0x17));
    chart.getTitle().setPaint(Color.WHITE);
    chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.WHITE);
    chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.WHITE);
    chart.getCategoryPlot().getDomainAxis().setLabelPaint(Color.LIGHT_GRAY);
    chart.getCategoryPlot().getRangeAxis().setLabelPaint(Color.LIGHT_GRAY);

    // Panel con el gráfico
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(271, 130)); // Ajusta según tu layout

    return chartPanel;
}


  
  public static JPanel crearGraficoBarrasPorCategoria(int secuencialEmpresa) {
    EntityManager em = MonituxDBContext.getEntityManager();

    // Consulta: nombre de categoría y cantidad de productos
    List<Object[]> resultados = em.createQuery(
        "SELECT c.Nombre, COUNT(p) " +
        "FROM Categoria c LEFT JOIN Producto p ON p.Secuencial_Categoria = c.Secuencial " +
        "WHERE c.Secuencial_Empresa = :empresa " +
        "GROUP BY c.Nombre", Object[].class)
        .setParameter("empresa", secuencialEmpresa)
        .getResultList();

    em.close();

    // Dataset para gráfico de barras
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for (Object[] fila : resultados) {
        String nombreCategoria = (String) fila[0];
        Long cantidad = ((Number) fila[1]).longValue();
        dataset.addValue(cantidad, "Productos", nombreCategoria);
    }

    // Crear gráfico de barras verticales
    JFreeChart chart = ChartFactory.createBarChart(
        "Cantidad de Productos por Categoría",
        "Categoría",
        "Cantidad",
        dataset,
        PlotOrientation.VERTICAL,
        false, true, false
    );

    // Estilo visual oscuro
    chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 14));
    chart.setBackgroundPaint(new Color(0x17, 0x17, 0x17));
    chart.getTitle().setPaint(Color.WHITE);

    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(new Color(0x1E, 0x1E, 0x1E));
    plot.setDomainGridlinePaint(new Color(0x55, 0x55, 0x55));
    plot.setRangeGridlinePaint(new Color(0x55, 0x55, 0x55));

//    BarRenderer renderer = (BarRenderer) plot.getRenderer();
//    renderer.setSeriesPaint(0, new Color(0x00, 0x72, 0xB2)); // Azul real

BarRenderer renderer = (BarRenderer) plot.getRenderer();
renderer.setSeriesPaint(0, new Color(0xE6, 0x9F, 0x00)); // Naranja fuerte


    plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
    plot.getRangeAxis().setTickLabelPaint(Color.WHITE);
    plot.getDomainAxis().setLabelPaint(Color.LIGHT_GRAY);
    plot.getRangeAxis().setLabelPaint(Color.LIGHT_GRAY);

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(798, 200));

    return chartPanel;
}

  
  
 
  public static JPanel crearGraficoBarrasDiariasIngresosEgresos(int secuencialEmpresa) {
    EntityManager em = MonituxDBContext.getEntityManager();

    LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
    LocalDate finMes = inicioMes.plusMonths(1).minusDays(1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
    DateTimeFormatter diaFormatter = DateTimeFormatter.ofPattern("dd/MM");

    List<Ingreso> ingresos = em.createQuery(
        "SELECT i FROM Ingreso i WHERE i.Secuencial_Empresa = :empresa", Ingreso.class)
        .setParameter("empresa", secuencialEmpresa)
        .getResultList();

    List<Egreso> egresos = em.createQuery(
        "SELECT e FROM Egreso e WHERE e.Secuencial_Empresa = :empresa", Egreso.class)
        .setParameter("empresa", secuencialEmpresa)
        .getResultList();

    em.close();

    Map<String, Double> ingresosTotales = new TreeMap<>();
    Map<String, Double> ingresosManuales = new TreeMap<>();
    Map<String, Double> egresosTotales = new TreeMap<>();
    Map<String, Double> egresosManuales = new TreeMap<>();

    for (Ingreso ingreso : ingresos) {
        try {
            LocalDate fecha = LocalDateTime.parse(ingreso.getFecha().replace(" ", " "), formatter).toLocalDate();
            if (!fecha.isBefore(inicioMes) && !fecha.isAfter(finMes)) {
                String dia = fecha.format(diaFormatter);
                double monto = ingreso.getTotal();
                String tipo = ingreso.getTipo_Ingreso().toLowerCase();

                if (tipo.contains("manual")) {
                    ingresosManuales.merge(dia, monto, Double::sum);
                } else {
                    ingresosTotales.merge(dia, monto, Double::sum);
                }
            }
        } catch (Exception ignored) {}
    }

    for (Egreso egreso : egresos) {
        try {
            LocalDate fecha = LocalDateTime.parse(egreso.getFecha().replace(" ", " "), formatter).toLocalDate();
            if (!fecha.isBefore(inicioMes) && !fecha.isAfter(finMes)) {
                String dia = fecha.format(diaFormatter);
                double monto = egreso.getTotal();
                String tipo = egreso.getTipo_Egreso().toLowerCase();

                if (tipo.contains("manual")) {
                    egresosManuales.merge(dia, monto, Double::sum);
                } else {
                    egresosTotales.merge(dia, monto, Double::sum);
                }
            }
        } catch (Exception ignored) {}
    }

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    ingresosTotales.forEach((dia, monto) -> dataset.addValue(monto, "Ingresos", dia));
    ingresosManuales.forEach((dia, monto) -> dataset.addValue(monto, "Ingresos Manuales", dia));
    egresosTotales.forEach((dia, monto) -> dataset.addValue(monto, "Egresos", dia));
    egresosManuales.forEach((dia, monto) -> dataset.addValue(monto, "Egresos Manuales", dia));

    JFreeChart chart = ChartFactory.createBarChart(
        "Flujo Diario de Ingresos y Egresos",
        "Día",
        "Monto (" + V_Menu_Principal.getMoneda_Empresa() + ")",
        dataset,
        PlotOrientation.VERTICAL,
        true, true, false
    );

    chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 14));
    chart.setBackgroundPaint(new Color(0x17, 0x17, 0x17));
    chart.getTitle().setPaint(Color.WHITE);

    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(new Color(0x1E, 0x1E, 0x1E));
    plot.setDomainGridlinePaint(new Color(0x55, 0x55, 0x55));
    plot.setRangeGridlinePaint(new Color(0x55, 0x55, 0x55));

    plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
    plot.getRangeAxis().setTickLabelPaint(Color.WHITE);
    plot.getDomainAxis().setLabelPaint(Color.LIGHT_GRAY);
    plot.getRangeAxis().setLabelPaint(Color.LIGHT_GRAY);

  BarRenderer renderer = (BarRenderer) plot.getRenderer();

// Ingresos
renderer.setSeriesPaint(0, new Color(0x00, 0xA8, 0x4A)); // Verde intenso
renderer.setSeriesPaint(1, new Color(0x66, 0xD9, 0x8F)); // Verde claro brillante

// Egresos
renderer.setSeriesPaint(2, new Color(0xC70039));         // Rojo intenso
renderer.setSeriesPaint(3, new Color(0xFF6F61));         // Rojo coral claro
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(798, 200));

    return chartPanel;
}

  
  
  
  
 public static JPanel crearGraficoBarrasHorizontalesComprasVsVentasMesActual(int secuencialEmpresa) {
    EntityManager em = MonituxDBContext.getEntityManager();

    LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
    LocalDate finMes = inicioMes.plusMonths(1).minusDays(1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));

    List<Compra> compras = em.createQuery(
        "SELECT c FROM Compra c WHERE c.Secuencial_Empresa = :empresa", Compra.class)
        .setParameter("empresa", secuencialEmpresa)
        .getResultList();

    List<Venta> ventas = em.createQuery(
        "SELECT v FROM Venta v WHERE v.Secuencial_Empresa = :empresa", Venta.class)
        .setParameter("empresa", secuencialEmpresa)
        .getResultList();

    em.close();

    double totalCompras = 0.0;
    double totalVentas = 0.0;

    for (Compra compra : compras) {
        try {
            LocalDate fecha = LocalDateTime.parse(compra.getFecha().replace(" ", " "), formatter).toLocalDate();
            if (!fecha.isBefore(inicioMes) && !fecha.isAfter(finMes)) {
                totalCompras += compra.getGran_Total();
            }
        } catch (Exception ignored) {}
    }

    for (Venta venta : ventas) {
        try {
            LocalDate fecha = LocalDateTime.parse(venta.getFecha().replace(" ", " "), formatter).toLocalDate();
            if (!fecha.isBefore(inicioMes) && !fecha.isAfter(finMes)) {
                totalVentas += venta.getGran_Total();
            }
        } catch (Exception ignored) {}
    }

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(totalCompras, "Compras", "Mes Actual");
    dataset.addValue(totalVentas, "Ventas", "Mes Actual");

    // Crear gráfico de barras horizontales
    JFreeChart chart = ChartFactory.createBarChart(
        "Comparativa de Compras y Ventas",
        "Monto (" + V_Menu_Principal.getMoneda_Empresa() + ")",
        "Periodo",
        dataset,
        PlotOrientation.HORIZONTAL,
        true, true, false
    );

    // Estilo visual oscuro
    chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 14));
    chart.setBackgroundPaint(new Color(0x17, 0x17, 0x17));
    chart.getTitle().setPaint(Color.WHITE);

    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(new Color(0x1E, 0x1E, 0x1E));
    plot.setDomainGridlinePaint(new Color(0x55, 0x55, 0x55));
    plot.setRangeGridlinePaint(new Color(0x55, 0x55, 0x55));

    plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
    plot.getRangeAxis().setTickLabelPaint(Color.WHITE);
    plot.getDomainAxis().setLabelPaint(Color.LIGHT_GRAY);
    plot.getRangeAxis().setLabelPaint(Color.LIGHT_GRAY);

    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(0x00, 0x72, 0xB2)); // Azul real
    renderer.setSeriesPaint(1, new Color(0x00, 0x9E, 0x73)); // Verde bosque para ventas

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(798, 200));

    return chartPanel;
}

  
  
  
  
 //******************************

//
//    public static JPanel crearGraficoCircularOperaciones(
//        double ventasContado,
//        double ventasCredito,
//        double comprasContado,
//        double comprasCredito
//    ) {
//        // Dataset circular
//        DefaultPieDataset dataset = new DefaultPieDataset();
//        dataset.setValue("Ventas Contado", ventasContado);
//        dataset.setValue("Ventas Crédito", ventasCredito);
//        dataset.setValue("Compras Contado", comprasContado);
//        dataset.setValue("Compras Crédito", comprasCredito);
//
//        // Crear gráfico circular
//        JFreeChart chart = ChartFactory.createPieChart(
//            "Distribución de Operaciones",
//            dataset,
//            true,   // leyenda
//            true,   // tooltips
//            false   // URLs
//        );
//
//        // Panel con el gráfico
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new Dimension(320, 146)); // Ajusta según tu layout
//
//        return chartPanel;
//    }
//    
//    
    
    
//   
//    public static JPanel crearGraficoCircularOperaciones(
//    double ventasContado,
//    double ventasCredito,
//    double comprasContado,
//    double comprasCredito
//) {
//    DefaultPieDataset dataset = new DefaultPieDataset();
//    dataset.setValue("Ventas Contado", ventasContado);
//    dataset.setValue("Ventas Crédito", ventasCredito);
//    dataset.setValue("Compras Contado", comprasContado);
//    dataset.setValue("Compras Crédito", comprasCredito);
//
//    JFreeChart chart = ChartFactory.createPieChart(
//        "Distribución de Operaciones (Mes Actual)",
//        dataset,
//        true,   // leyenda
//        true,   // tooltips
//        false   // URLs
//    );
//
//    // 🎨 Fondo general del gráfico
//    chart.setBackgroundPaint(new Color(30, 30, 30)); // gris oscuro
//
//    // 🎨 Estilo del título
//    chart.getTitle().setPaint(new Color(224, 224, 224)); // blanco suave
//    chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 14));
//
//    // 🎨 Fondo y estilo del área de dibujo
//    PiePlot plot = (PiePlot) chart.getPlot();
//    plot.setBackgroundPaint(new Color(44, 44, 44)); // fondo del plot
//    plot.setOutlineVisible(false);
//    plot.setLabelBackgroundPaint(new Color(60, 60, 60));
//    plot.setLabelPaint(new Color(240, 240, 240)); // texto claro
//    plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
//
//    // 🎨 Colores modernos por categoría
//    plot.setSectionPaint("Ventas Contado", new Color(33, 150, 243));    // azul moderno
//    plot.setSectionPaint("Ventas Crédito", new Color(76, 175, 80));     // verde suave
//    plot.setSectionPaint("Compras Contado", new Color(255, 235, 59));   // amarillo vibrante
//    plot.setSectionPaint("Compras Crédito", new Color(244, 67, 54));    // rojo moderno
//
//    // 🎨 Panel contenedor con fondo oscuro
//    ChartPanel chartPanel = new ChartPanel(chart);
//    chartPanel.setPreferredSize(new Dimension(320, 146));
//    chartPanel.setBackground(new Color(30, 30, 30)); // fondo del panel
//
//    return chartPanel;
//}
//
//    
    
    
public static JPanel crearGraficoCircularOperaciones(
    double ventasContado,
    double ventasCredito,
    double comprasContado,
    double comprasCredito
) {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Ventas Contado", ventasContado);
    dataset.setValue("Ventas Crédito", ventasCredito);
    dataset.setValue("Compras Contado", comprasContado);
    dataset.setValue("Compras Crédito", comprasCredito);

    JFreeChart chart = ChartFactory.createPieChart(
        "Distribución de Operaciones (Mes Actual)",
        dataset,
        true,   // leyenda
        true,   // tooltips
        false   // URLs
    );

    // 🎨 Fondo general del gráfico
    chart.setBackgroundPaint(new Color(30, 30, 30)); // gris oscuro

    // 🎨 Estilo del título
    chart.getTitle().setPaint(new Color(224, 224, 224)); // blanco suave
    chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 14));

    // 🎨 Fondo y estilo del área de dibujo
    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setBackgroundPaint(new Color(44, 44, 44)); // fondo del plot
    plot.setOutlineVisible(false);
    plot.setLabelBackgroundPaint(new Color(60, 60, 60));
    plot.setLabelPaint(new Color(240, 240, 240)); // texto claro
    plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

    // 🎨 Colores por tipo y modalidad
    plot.setSectionPaint("Compras Contado", new Color(0x00, 0x72, 0xB2));   // Azul real
    plot.setSectionPaint("Compras Crédito", new Color(0x64, 0xB5, 0xF6));   // Azul claro

    plot.setSectionPaint("Ventas Contado", new Color(0x00, 0x9E, 0x73));    // Verde bosque
    plot.setSectionPaint("Ventas Crédito", new Color(0x81, 0xC7, 0x84));    // Verde suave

    // 🎨 Panel contenedor con fondo oscuro
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(306, 182));
    chartPanel.setBackground(new Color(30, 30, 30)); // fondo del panel

    return chartPanel;
}


     //**************************
     
     public static <T extends Component> T clonarControl(T controlOriginal) {
    try {
        @SuppressWarnings("unchecked")
        T nuevoControl = (T) controlOriginal.getClass().getDeclaredConstructor().newInstance();

        for (Field field : controlOriginal.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            // Evitar campos internos del sistema si es necesario
            if (!field.getName().equals("peer")) {
                try {
                    Object valor = field.get(controlOriginal);
                    field.set(nuevoControl, valor);
                } catch (IllegalAccessException e) {
                    // Ignorar campos no accesibles
                }
            }
        }

        return nuevoControl;

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

     
     
     
    
     
 public static void llenar_Combo_Categoria(JComboBox<String> comboCategoria, int secuencialEmpresa) {
    comboCategoria.removeAllItems(); // Limpiar combo

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Categoria> categorias = em.createQuery(
            "SELECT c FROM Categoria c WHERE c.Secuencial_Empresa = :empresa", Categoria.class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        for (Categoria c : categorias) {
            comboCategoria.addItem(c.getSecuencial() + " - " + c.getNombre());
        }

        System.out.println("✅ Categorías cargadas correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar categorías: " + e.getMessage());
        e.printStackTrace();
    }
}

    
    
    
    //***************************
     
    
}//Fin Clase
