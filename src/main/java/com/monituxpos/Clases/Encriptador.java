/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encriptador {

    private static final byte[] CLAVE = "0123456789abcdef".getBytes(StandardCharsets.UTF_8); // 16 bytes
    private static final byte[] IV = "abcdef0123456789".getBytes(StandardCharsets.UTF_8);    // 16 bytes

    public static String encriptar(String texto) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(CLAVE, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] textoBytes = texto.getBytes(StandardCharsets.UTF_8);
            byte[] textoEncriptado = cipher.doFinal(textoBytes);

            return Base64.getEncoder().encodeToString(textoEncriptado);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String desencriptar(String textoEncriptado) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(CLAVE, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] textoBytes = Base64.getDecoder().decode(textoEncriptado);
            byte[] textoDesencriptado = cipher.doFinal(textoBytes);

            return new String(textoDesencriptado, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
