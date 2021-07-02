package com.jorch.proyecto.firechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JORCH on 19/03/2017.
 */

public class EncryptHelper {
    private static MessageDigest messageDigest;
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    //ENCRIPT MESSAGE
    public static String encryptMesssage(String message){
        byte[] messageInBytes = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(message.getBytes());
            messageInBytes = messageDigest.digest();

        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        String resultado = bytesToHex(messageInBytes);
        return resultado;
    }
    //TRANFORM BYTES EN HEXA
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
