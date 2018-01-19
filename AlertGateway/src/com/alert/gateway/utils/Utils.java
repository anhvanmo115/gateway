/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: Utils.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author dungnq8
 */
public class Utils {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Utils.class.getSimpleName());
    public static final String DATE_VIETNAM = "dd/MM/yyyy: HH:mm:ss";

    public static String convertDateToString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String convertDateVietNam(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_VIETNAM);
        return dateFormat.format(date);
    }

//    public static Date convertStringToDate(String date, String pattern) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//        try {
//            return dateFormat.parse(date);
//        } catch (Exception e) {
//            SmartLog.getInstance().logError(LOGGER, "Ignored " + e);
//            return null;
//        }
//    }
    public static boolean isEmptyString(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }
        return false;
    }
    private static final String phoneRegex = "\\d{3,12}";
    // Hiện chỉ check gồm số và có từ 3 đến 12 ký tự

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isEmptyString(phoneNumber)) {
            return false;
        }

        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
    private static char[] SPECIAL_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
        'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
        'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
        'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
        'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
        'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
        'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
        'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
        'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
        'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
        'ữ', 'Ự', 'ự'};
    private static char[] REPLACEMENTS = {'A', 'A', 'A', 'A', 'E', 'E',
        'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a',
        'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y',
        'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A',
        'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a',
        'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E',
        'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e',
        'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
        'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
        'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U',
        'u', 'U', 'u'};

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
        if (index >= 0) {
            ch = REPLACEMENTS[index];
        }
        return ch;
    }

    public static String removeAccent(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }
    private static final String[] htmlChar = new String[]{"&", "<", ">", "'",
        "\""};
    private static final String[] htmlNameCode = new String[]{"&amp;",
        "&lt;", "&gt;", "&apos;", "&quot;"};

    public static String convertStringToHTMLCode(String strSource) {
        if (isNullOrEmpty(strSource)) {
            return "";
        }
        for (int i = 0; i < htmlChar.length; i++) {
            strSource = strSource.replaceAll(htmlChar[i], htmlNameCode[i]);
        }
        return strSource;
    }

    public static String convertHTMLCodeToString(String strSource) {
        if (isNullOrEmpty(strSource)) {
            return "";
        }
        for (int i = 0; i < htmlNameCode.length; i++) {
            strSource = strSource.replaceAll(htmlNameCode[i], htmlChar[i]);
        }
        return strSource;
    }

    public static boolean isNullOrEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    public static String getSafeFileName(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != '/' && c != '\\' && c != 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean checkExtentionFileUpload(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1).toLowerCase();
        }
        if (("jpg".equals(extension)) || ("png".equals(extension))
                || "gif".equals(extension) || "bmp".equals(extension)) {
            return true;
        }
        return false;
    }

    public static String getStringEmpty(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }
        return string;
    }

    public static String SHA256Password(String pass, byte[] salt) throws NoSuchAlgorithmException {
//        String password = pass != null && !pass.isEmpty() && pass.length() > 0 ? pass : "123456a@";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(pass.getBytes());
        md.update(salt);
        byte byteData[] = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

//        System.out.println("Hex format not salt: cb9a7f035d23c91b81d8f9981405d2e566267e4e10ef8ff0722d3d5a61611b52");
//        System.out.println("Hex format : " + hexString.toString());
        return hexString.toString();
    }

    public static String SHA256Password(String pass, String salt) throws NoSuchAlgorithmException {
//        String password = pass != null && !pass.isEmpty() && pass.length() > 0 ? pass : "123456a@";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(pass.getBytes());
        md.update(salt.getBytes());
        byte byteData[] = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

//        System.out.println("Hex format not salt: cb9a7f035d23c91b81d8f9981405d2e566267e4e10ef8ff0722d3d5a61611b52");
//        System.out.println("Hex format : " + hexString.toString());
        return hexString.toString();
    }

    public static String convertByteToString(byte[] array) {
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += Integer.toHexString((0x000000ff & array[i]) | 0xffffff00).substring(6);
        }
        return result;
    }

    //tim kiem mang trong mang
//    public static boolean IsContainsSubArray(byte[] Large_Array, byte[] Sub_Array) {
//        try {
//            int Large_Array_size, Sub_Array_size, k = 0;
//
//            Large_Array_size = Large_Array.length;
//            Sub_Array_size = Sub_Array.length;
//
//            if (Sub_Array_size > Large_Array_size) {
//                return false;
//            }
//            for (int i = 0; i < Large_Array_size; i++) {
//                if (Large_Array[i] == Sub_Array[k]) {
//                    k++;
//                } else {
//                    k = 0;
//                }
//                if (k == Sub_Array_size) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            SmartLog.getInstance().logError(LOGGER, "Ignored " + e);
//            
//        }
//        return false;
//    }
    public int findArray(byte[] largeArray, byte[] subArray) {
        if (subArray.length == 0) {
            return -1;
        }
        int limit = largeArray.length - subArray.length;
        next:
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j < subArray.length; j++) {
                if (subArray[j] != largeArray[i + j]) {
                    continue next;
                }
            }
            /* Sub array found - return its index */
            return i;
        }
        /* Return default value */
        return -1;
    }

    public String calcularCRC16(String trama) {
        int[] table = {
            0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
            0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
            0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
            0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
            0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
            0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
            0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
            0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
            0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
            0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
            0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
            0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
            0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
            0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
            0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
            0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
            0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
            0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
            0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
            0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
            0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
            0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
            0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
            0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
            0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
            0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
            0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
            0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
            0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
            0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
            0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
            0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040,};

        char[] chars = trama.toCharArray();
        int crc = 0x0000;
        for (char c : chars) {
            crc = (crc >>> 8) ^ table[(crc ^ c & 0x00ff) & 0xff];
        }
        String strCrc = "00000" + String.valueOf(crc);
        return strCrc.substring(strCrc.length() - 5);
    }

    public static class RespondCode {

        public static final Integer REQUEST_TIMEOUT = 1;
        public static final Integer REQUEST_FAIL = 0;
        public static final Integer REQUEST_DEVICE_BUSY = 2;
        public static final Integer REQUEST_DEVICE_UNDEFINE = 3;
        public static final Integer REQUEST_DEVICE_ERROR_PARAMETER = 4;
        public static final Integer REQUEST_DEVICE_ISCONFIG = 5;
        public static final Integer REQUEST_OK = 200;
        public static final Integer REQUEST_OK_PART = 201;
    }

    /**
     * @author hoahv5 get local ip address
     * @return
     */
    public static String getLocalIpAdress() {
        String ret = "";
        InetAddress ip;

        try {
            ip = InetAddress.getLocalHost();
            ret = ip.getHostAddress();

        } catch (UnknownHostException e) {
            SmartLog.getInstance().logError(LOGGER, e);
        }
        return ret;
    }
}
