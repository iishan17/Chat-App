package com.example.chatme;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static final byte[] encryptionKey = {45, 113, 1, 67, -89, -34, -9, 10, -68, 17, 20, -53, 105, -115, -31, -79};
    private static final String initializationVector = "8119745113154120";
    private static final String cipherInstance = "AES/CBC/PKCS5Padding";

    public static String encrypt(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher mCipher = Cipher.getInstance(cipherInstance);
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        byte[] encryptedByte = null;
        mCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,new IvParameterSpec(initializationVector.getBytes()));
        try {
            encryptedByte = mCipher.doFinal(message.getBytes());
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(encryptedByte, Base64.DEFAULT);
    }

    public static String decrypt(String encryptedMessage) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher mDecipher = Cipher.getInstance(cipherInstance);
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        byte[] encryptedByte = Base64.decode(encryptedMessage, Base64.DEFAULT);
        mDecipher.init(Cipher.DECRYPT_MODE,secretKeySpec, new IvParameterSpec(initializationVector.getBytes()));
        byte[] decryptedMessage = null;


        try {
            decryptedMessage = mDecipher.doFinal(encryptedByte);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new String(decryptedMessage, "UTF-8") ;
    }


}
