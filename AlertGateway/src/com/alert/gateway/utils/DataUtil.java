/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: DataUtil.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.utils;

import com.alert.gateway.message.FrameObject;
import io.netty.channel.Channel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class DataUtil {
private static Logger LOGGER;
    private static final ConcurrentHashMap<String, Channel> mapImeiChannelHandler = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, DeviceObject> mapRequestIdDevice = new ConcurrentHashMap<>(); //idDevice va TransactionId
    private static final ConcurrentHashMap<Integer,Object> currentRequest = new ConcurrentHashMap<>(); //luu RequestId
    private static final ConcurrentHashMap<String,byte[]> mapChanenelKey = new ConcurrentHashMap<>();
    private static final LinkedBlockingQueue<FrameObject> requestMessageQueue = new LinkedBlockingQueue<>( Integer.MAX_VALUE);
    public static ConcurrentHashMap<String, Channel> GetMapImeiChannelHandler() {
        return mapImeiChannelHandler;
    }
    
    public static ConcurrentHashMap<String, DeviceObject> GetMapRequestIdDevice() {
        return mapRequestIdDevice;
    }
    
    public static ConcurrentHashMap<Integer,Object> GetCurrentRequest() {
        return currentRequest;
    }
    
    public static ConcurrentHashMap<String,byte[]> GetMapChanenelKey() {
        return mapChanenelKey;
    }
    
    public static final boolean isWaitClientResponse = false;

    public static LinkedBlockingQueue<FrameObject> getRequestMessageQueue() {
        return requestMessageQueue;
    }
   
    public static byte[] convertFloatTo4bytes(float data) {
        int bits = Float.floatToIntBits(data);
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((bits >> 24) & 0xff);
        bytes[1] = (byte) ((bits >> 16) & 0xff);
        bytes[2] = (byte) ((bits >> 8) & 0xff);
        bytes[3] = (byte) (bits & 0xff);
        return bytes;
    }

    public static float convert4bytesToFloat(byte[] bytesArr) {
        int asInt = (bytesArr[0] & 0xFF) << 24
                | ((bytesArr[1] & 0xFF) << 16)
                | ((bytesArr[2] & 0xFF) << 8)
                | (bytesArr[3] & 0xFF);
        float f = Float.intBitsToFloat(asInt);
        return f;
    }

    public static byte[] convertIntTo2bytes(int data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xFF00);
        bytes[1] = (byte) (data & 0x00FF);
        return bytes;
    }

    public static int convert2BytesToInt(byte[] bytes) {
        return (bytes[0] & 0xFF00) | (bytes[1] & 0x00FF);
    }

    
//    public static int convert4BytesToInt()
    public static byte[] convertDateTo6bytes(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        byte[] bytes = new byte[6];
        bytes[0] = (byte) (cal.get(Calendar.YEAR) - 2000);
        bytes[1] = (byte) (cal.get(Calendar.MONTH));
        bytes[2] = (byte) (cal.get(Calendar.DAY_OF_MONTH));
        bytes[3] = (byte) (cal.get(Calendar.HOUR_OF_DAY));
        bytes[4] = (byte) (cal.get(Calendar.MINUTE));
        bytes[5] = (byte) (cal.get(Calendar.SECOND));
        return bytes;
    }

    public static Date convert6bytesToDate(byte[] bytesArr) {
        Calendar cal = Calendar.getInstance();
        cal.set(bytesArr[0] + 2000, bytesArr[1], bytesArr[2],
                bytesArr[3], bytesArr[4], bytesArr[5]);
        return cal.getTime();
    }

    public static StringBuilder convertHexToBinary(String hex) {
        int i = Integer.parseInt(hex, 10);
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(i));
        return sb.reverse();
    }

    public static StringBuilder convertIntegerToBinary(int i) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(i));
        return sb.reverse();
    }

    public static String toMysqlDateStr(Date date) {
        String dateForMySql;
        if (date == null) {
            dateForMySql = null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateForMySql = sdf.format(date);
        }

        return dateForMySql;
    }

    public static byte[] convertStringToByte(int byteLength, String content) {

        byte[] temp = content.getBytes();
        int tempLength = temp.length;

        if (tempLength >= byteLength) {
            return temp;
        }
        byte[] result = new byte[byteLength];
        for (int i = 0; i < byteLength; i++) {
            result[i] = i < tempLength ? temp[i] : -1;
        }

        return result;
    }

    public static byte[] convertIntTo4Bytes(int i) {
        byte[] result = new byte[4];
        result[3] = (byte) (i >> 24);
        result[2] = (byte) (i >> 16);
        result[1] = (byte) (i >> 8);
        result[0] = (byte) (i);
        return result;
    }

    public static int convert4BytesToInt(byte[] bytes) {
        int asInt = (bytes[0] & 0xFF)
                | ((bytes[1] & 0xFF) << 8)
                | ((bytes[2] & 0xFF) << 16)
                | ((bytes[3] & 0xFF) << 24);
        return asInt;
    }

    public static Date parseDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTime(sdf.parse(date));
//        calendar.add(Calendar.HOUR, -7);
        return calendar.getTime();
    }

    public static String createDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String replaceString(float lat, float lng) {

//        if (lat == 0f || lng == 0) {
//            return "Không xác định được vị trí, kiểm tra lại anten GPS.";
//        }
//
//        BufferedReader in = null;
        String address = null;
//
//        try {
////            URL yahoo = new URL("http://125.235.40.22/VTMapService/geoprocessing?f=getmultiaddr&k=a682ac669c8884e3697fa1d907e2de8&pt=" + String.valueOf(lat) + "," + String.valueOf(lng));
////            URL yahoo = new URL("http://viettelmaps.vn/VTMapService/geoprocessing?f=getmultiaddr&d=6e44.6e5a.6e56.6e45.6e43.6e5a.6e58.6e43.6e58.6e45.6e19.6e41.6e59.&k=c75bfb90e6db0b1b6ab5c356b6e28dc4&pt=" + String.valueOf(lat) + "," + String.valueOf(lng));
//            //URL yahoo = new URL("http://203.190.170.250/VTMapService/geoprocessing?f=getmultiaddr&d=6e44.6e5a.6e56.6e45.6e43.6e5a.6e58.6e43.6e58.6e45.6e19.6e41.6e59.&k=c75bfb90e6db0b1b6ab5c356b6e28dc4&pt=" + String.valueOf(lat) + "," + String.valueOf(lng));
////            URL yahoo = new URL("http://125.235.40.22/VTMapService/geoprocessing?f=getaddr&k=c75bfb90e6db0b1b6ab5c356b6e28dc4&d=6e44.6e5a.6e56.6e45.6e43.6e5a.6e58.6e43.6e58.6e45.6e19.6e41.6e59&pt=" + String.valueOf(lat) + "," + String.valueOf(lng));
//            
////            System.out.println("" + yahoo.toURI());
//            
//            URLConnection yc = yahoo.openConnection();
//            in = new BufferedReader(
//                    new InputStreamReader(
//                            yc.getInputStream()));
//            address = in.readLine();
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                SmartLog.getInstance().logError(LOGGER, e);
//            }
//        }
//
//        if (address == null || address.isEmpty()) {
//            return "Không lấy được địa chỉ từ bản đồ!";
//        }
//
//
//        int index1 = address.indexOf("location");
//        address = address.substring(index1);
//        int index2 = address.indexOf("address");
//        address = address.substring(index2 + 10);
//        int index3 = address.indexOf("}");
//        address = address.substring(0, index3 - 1);
//        
        return address;
    }

    public static boolean checkString(String s) {
        return s != null && !s.isEmpty();
    }

    public static String createFileName(String imei, String phoneNumber) {
        StringBuilder sb = new StringBuilder("");
        if (imei.startsWith("6614")) {
            sb.append("V01_");
        } else {
            sb.append("W1_");
        }
        sb.append(imei).append("_").append(phoneNumber);
        return sb.toString();
    }

    public static boolean createFile(Logger logger, String folder, String fileName) {
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            boolean mkdir = folderFile.mkdir();

            if (!mkdir) {
                logger.error("Can't mkdir");
                return false;
            }
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(folder + "\\" + fileName + ".txt");
            return true;
        } catch (FileNotFoundException e) {
            logger.error(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return false;
    }
}
