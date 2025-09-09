package com.monituxpos.Clases;



import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
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

import com.monituxpos.Ventanas.VisorPDFBox;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
    
    
    public static void registrarActividad(int secuencialUsuario, String descripcion, int secuencialEmpresa) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        Actividad actividad = new Actividad();
        actividad.setSecuencial_Usuario(secuencialUsuario);
        actividad.setSecuencial_Empresa(secuencialEmpresa);
        actividad.setDescripcion(descripcion);

        em.getTransaction().begin();
        em.persist(actividad);
        em.getTransaction().commit();

    } catch (Exception e) {
        e.printStackTrace(); // Puedes mostrar un JOptionPane si lo prefieres
    } finally {
        em.close();
        emf.close();
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

    
    
    public static void registrarMovimientoKardex(
    int secuencialProducto, double existencia,
    String descripcion, double cantidadUnidades,
    double costo, double venta,
    String movimiento, int secuencialEmpresa
) {
    try {
        if (cantidadUnidades < 0 || costo < 0 || venta < 0) {
            throw new IllegalArgumentException("Valores inválidos para movimiento de inventario.");
        }

        double saldoNuevo = movimiento.equals("Entrada")
            ? existencia + cantidadUnidades
            : existencia - cantidadUnidades;

        Kardex kardex = new Kardex();
        kardex.setSecuencial_Empresa(secuencialEmpresa);
        kardex.setSecuencial_Producto(secuencialProducto);
        kardex.setDescripcion(descripcion);
        kardex.setCantidad(cantidadUnidades);
        kardex.setCosto(costo);
        kardex.setVenta(venta);
        kardex.setMovimiento(movimiento);
        kardex.setSaldo(saldoNuevo);
        kardex.setCosto_Total(Math.round(cantidadUnidades * costo * 100.0) / 100.0);
        kardex.setVenta_Total(Math.round(cantidadUnidades * venta * 100.0) / 100.0);
        kardex.setFecha(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(kardex);
        em.getTransaction().commit();

        em.close();
        emf.close();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,"Error al registrar movimiento en Kardex:\n" + ex.getMessage());
    }
}

    
   

    public static void llenarComboCliente(JComboBox<String> combo,int secuencial_Empresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        List<Cliente> clientes = em.createQuery(
            "SELECT c FROM Cliente c WHERE c.Activo = true AND c.Secuencial_Empresa = :empresa", Cliente.class)
            .setParameter("empresa", secuencial_Empresa)
            .getResultList();

        for (Cliente c : clientes) {
            combo.addItem(c.getSecuencial() + " - " + c.getNombre());
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage());
    } finally {
        em.close();
        emf.close();
    }
}

    
    public static void llenarComboUsuario(JComboBox<String> combo, int secuencial_Empresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        List<Usuario> usuarios = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.Activo = true AND u.Secuencial_Empresa = :empresa", Usuario.class)
            .setParameter("empresa", secuencial_Empresa)
            .getResultList();

        for (Usuario u : usuarios) {
            combo.addItem(u.getSecuencial() + " - " + u.getNombre());
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar usuarios: " + e.getMessage());
    } finally {
        em.close();
        emf.close();
    }
}

    
    
    
    public static void llenarComboProveedor(JComboBox<String> combo, int secuencial_Empresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        List<Proveedor> proveedores = em.createQuery(
            "SELECT p FROM Proveedor p WHERE p.Activo = true AND p.Secuencial_Empresa = :empresa", Proveedor.class)
            .setParameter("empresa", secuencial_Empresa)
            .getResultList();

        for (Proveedor p : proveedores) {
            combo.addItem(p.getSecuencial() + " - " + p.getNombre());
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar proveedores: " + e.getMessage());
    } finally {
        em.close();
        emf.close();
    }
}

    
    
    
    
    public static void llenarComboClientePorTelefono(JComboBox<String> combo, JTextField campoTelefono, int secuencialEmpresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        String textoTelefono = campoTelefono.getText().trim().toLowerCase();

        List<Cliente> clientes = em.createQuery(
            "SELECT c FROM Cliente c WHERE c.Activo = true AND c.Secuencial_Empresa = :empresa AND LOWER(c.Telefono) LIKE :telefono",
            Cliente.class
        )
        .setParameter("empresa", secuencialEmpresa)
        .setParameter("telefono", "%" + textoTelefono + "%")
        .getResultList();

        for (Cliente item : clientes) {
            String texto = item.getSecuencial() + " - " + item.getNombre();
            combo.addItem(texto);
            combo.setSelectedItem(texto); // Selecciona el último encontrado
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al filtrar clientes: " + e.getMessage());
    } finally {
        em.close();
        emf.close();
    }
}

  public static void llenarComboProveedorPorTelefono(JComboBox<String> combo, String telefono, int secuencialEmpresa) {
    combo.removeAllItems(); // Limpiar combo

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        String textoTelefono = telefono.trim().toLowerCase();

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

        combo.setSelectedIndex(0); // Selecciona el primero si hay resultados

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al filtrar proveedores: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        em.close();
        emf.close();
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
            JOptionPane.showMessageDialog(null, "Error al enviar el correo: " + ex.getMessage(), "Correo", JOptionPane.ERROR_MESSAGE);
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
     
    
     
  
 //******************************    
     
     
     
     
    
}//Fin Clase
