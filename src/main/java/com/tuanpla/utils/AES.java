/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.utils;

/**
 *
 * @author TUANPLA
 */
import java.nio.charset.Charset;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static final String ALGO = "AES";
//    private static final byte[] keyValue = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't','S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    static String keyStr = "TheBestSecretKey";

    public static String encrypt(String data, String keyVal) throws Exception {
        byte[] raw = keyVal.getBytes(Charset.forName("UTF-8"));
        Key key = new SecretKeySpec(raw, ALGO);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = BASE64Encoder.encodeLines(encVal);
        return encryptedValue;
    }

    public static String decrypt(String data, String keyVal) throws Exception {
        byte[] raw = keyVal.getBytes(Charset.forName("UTF-8"));
        Key key = new SecretKeySpec(raw, ALGO);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = BASE64Encoder.decodeLines(data);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    public static void main(String[] args) throws Exception {

        String Plain_Text = "mypassworda;sdka sljkdja;slk  asd asas dasd sd asda sdjdslkajdl askjdljasldjaslkdja;slkjdlaskjdlkasjdlkjas;dl";
        String passwordEnc = AES.encrypt(Plain_Text, keyStr);
        String passwordDec = AES.decrypt(passwordEnc, keyStr);

        System.out.println("Plain Text : " + Plain_Text);
        System.out.println("Encrypted Text : " + passwordEnc);
        System.out.println("Decrypted Text : " + passwordDec);
               
        
        /* Derive the key, given password and salt. */
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        String strPass = "TheBestSa12";
//        char[] password = strPass.toCharArray();
//        byte[] salt = {'T','u','a','n','D','u','n','g'};
//        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
//        SecretKey tmp = factory.generateSecret(spec);
//        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
//        /* Encrypt the message. */
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secret);
//        AlgorithmParameters params = cipher.getParameters();
//        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
//        byte[] ciphertext = cipher.doFinal("Hello, World!".getBytes("UTF-8"));
//        /* Decrypt the message, given derived key and initialization vector. */
////        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
//        String plaintext = new String(cipher.doFinal(ciphertext), "UTF-8");
//        System.out.println(plaintext);
    }
}
