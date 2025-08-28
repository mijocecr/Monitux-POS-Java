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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

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


    public static void enviarCorreoConPdf(
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

    
    
    
    
    
    
    
}//Fin Clase
