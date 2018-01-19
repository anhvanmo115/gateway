/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alert.gateway.utils;

import java.util.List;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author LinhNV10
 */
public class ConvertDataType {

    private static long seq = 0;

    public static long getNextSeq() {
        seq++;
        if (seq > Long.MAX_VALUE) {
            seq = 1;
        }
        return seq;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    public static byte[] concatByte(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static byte[] concatByte(List<byte[]> byteArrays) {
        byte[] Result = new byte[0];
        for (byte[] bytes : byteArrays) {
            Result = ConvertDataType.concatByte(Result, bytes);
        }
        return Result;
    }
    
    public static long increase(long value, long increase){
        if(Long.MAX_VALUE-increase<=value){
            return 0;            
        }
        return value+increase;
    }
}
