package com.alert.gateway.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

/**
 *
 * @author Duongpx2
 */
public class AesManager {

    private Logger logger = Logger.getLogger(AesManager.class.getName());
    private  AesManager instance;
    private  Cipher aesCipher;
    private final HashMap<String, byte[]> mapIpAddressVSKey = new HashMap();

    public AesManager(){
        try {
            aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            logger.error("INIT AES CIPHER FAILED -->", ex);
        }
    }
//    private AesManager() {
//        try {
//            aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
//            logger.error("INIT AES CIPHER FAILED -->", ex);
//        }
//    }

//    public static AesManager getInstance() {
//        if (instance == null) {
//            instance = new AesManager();
//        }
//        return instance;
//    }

    public byte[] encryptByte(byte[] toEncrypt, byte[] Key) {
        try {
            SecretKey secretKey = new SecretKeySpec(Key, "AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = aesCipher.doFinal(toEncrypt);
            return encrypted;
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            logger.error("AES ENCRYPT FAIL:", ex);
            //logger.error("AES ENCRYPT [" + ConvertDataType.toHexString(toEncrypt) + "] FAILED -->", ex);
        }
        return null;
    }

    public byte[] decryptByte(byte[] toDecrypt, byte[] Key) {
        try {
            SecretKey secretKey = new SecretKeySpec(Key, "AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = aesCipher.doFinal(toDecrypt);
            return decrypted;
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            logger.error("AES DECRYPT FAIL:", ex);
        }
        return null;
    }

    public byte[] encryptByteCut(byte[] toEncrypt, byte[] Key) {
        try {
            SecretKey secretKey = new SecretKeySpec(Key, "AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = aesCipher.doFinal(toEncrypt);
            byte[] result = new byte[16];
            System.arraycopy(encrypted, 0, result, 0, result.length);
            return result;
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            logger.error("AES ENCRYPT FAIL:", ex);
        }
        return null;
    }

    public byte[] decryptByteCut(byte[] toDecrypt, byte[] Key) {
        try {
            SecretKey secretKey = new SecretKeySpec(Key, "AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = aesCipher.doFinal(toDecrypt);
            byte[] result = new byte[16];
            System.arraycopy(decrypted, 0, result, 0, result.length);
            return result;
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            logger.error("AES DECRYPT FAIL:", ex);
        }
        return null;
    }

    public void addAddressVsKey(String adress, byte[] key) {
        try {
            this.mapIpAddressVSKey.put(adress, key);
        } catch (Exception ex) {
            logger.error("AES ERROR KEY:", ex);
        }
    }

    public byte[] getKeyOfAddress(String adress) {
        return this.mapIpAddressVSKey.get(adress);
    }

    public void removeKeyByAddress(String adress) {
        try {
            synchronized (this.mapIpAddressVSKey) {
                this.mapIpAddressVSKey.remove(adress);
            }
        } catch (Exception ex) {
            logger.error("AES REMOVE KEY:", ex);
        }
    }

    public String deCryptAesString(String inputHex, String key) {
        if(inputHex == null){
            return null;
        }
        byte[] input = DatatypeConverter.parseHexBinary(inputHex);
        byte[] result = decryptByte(input, key.getBytes());
        return new String(result);
    }

    public String enCryptAesString(String input, String key) {
        byte[] result = encryptByte(input.getBytes(), key.getBytes());
        return Hex.encodeHexString(result);
    }

}
