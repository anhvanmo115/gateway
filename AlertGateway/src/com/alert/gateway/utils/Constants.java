/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: Constants.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.utils;

import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class Constants {

    public static final String SAFE_SERVER_NAME = "SafeTCPServer";
    private static Logger LOGGER;

    public Constants() {
    }

    public interface ConfigPaths {

        public static final String LOG4J_PROPERTY = "../etc/log4j.properties";
        public static final String MTR_CORE_PROPERTY = "../etc/mtr_core.properties";
    }

    public interface PropertyName {

        public static final String CONTROLLER_ID = "vtracking.core.controllerId";
        public static final String C3P0_EXT_PREFIX = "c3p0Ext.";
        public static final String C3P0_PREFIX = "c3p0.";
        public static final String C3P0_USER = "c3p0.user";
        //public static final String C3P0_PASSWORD = "c3p0.password";
        public static final String C3P0_JDBC_URL = "c3p0.jdbcUrl";
    }

    public interface MstDivisionGroupCode {

        //Phân loại ngư�?i dùng
        public static final String PHAN_LOAI_NGUOI_DUNG = "001";
        //Phân loại phòng ban
        public static final String PHAN_LOAI_PHONG_BAN = "002";
        //Phân loại phương tiện PCCC
        public static final String PHAN_LOAI_PHUONG_TIEN_PCCC = "003";
        //Loại đơn vị chức năng
        public static final String LOAI_DON_VI_CHUC_NANG = "004";
        //Loại tài liệu
        public static final String LOAI_TAI_LIEU = "005";
        //Loại thiết bị
        public static final String LOAI_THIET_BI = "006";
        //Loại điểm lấy nước
        public static final String LOAI_DIEM_LAY_NUOC = "007";
        //Loại chức năng
        public static final String LOAI_CHUC_NANG = "008";
        //Xe cứu h�?a
        public static final String XE_CUU_HOA = "009";
        //Giới tính
        public static final String GIOI_TINH = "010";
        //Trạng thái
        public static final String TRANG_THAI = "011";
        //Kiểm tra IP
        public static final String KIEM_TRA_IP = "012";
        //Ch�?n khu vực địa lý
        public static final String CHON_KHU_VUC_DIA_LY = "013";
        //Trạng thái thiết bị
        public static final String TRANG_THAI_THIET_BI = "014";
        //Trạng thái giám sát thiết bị
        public static final String TRANG_THAI_GIAM_SAT_THIET_BI = "015";
        //Vị trí/ Chức vụ
        public static final String VI_TRI_CHUC_VU = "016";
        //Loại hình kinh doanh
        public static final String LOAI_HINH_KINH_DOANH = "017";
        //Quản lý đơn vị tổng Cục
        public static final String QUAN_LY_DON_VI_TONG_CUC = "018";
        //Công cụ, dụng cụ, trang bị
        public static final String CONG_CU_DUNG_CU_TRANG_BI = "019";
        //Nhân sự ứng cứu tại chỗ - chức vụ
        public static final String NHAN_SU_UNG_CUU_TAI_CHO_CHUC_VU = "006";
        //Chuyên môn nghiệp vụ
        public static final String CHUYEN_MON_NGHIEP_VU = "021";
        //Phân loại camera
        public static final String PHAN_LOAI_CAMERA = "022";
        //Phân loại giao lộ
        public static final String PHAN_LOAI_GIAO_LO = "023";
        //Phân loại đơn vị chức năng
        public static final String PHAN_LOAI_DON_VI_CHUC_NANG = "024";
        //Hành động
        public static final String HANH_DONG = "030";
        //�?ối tượng tương tác
        public static final String DOI_TUONG_TUONG_TAC = "031";
        //Vị trí việc làm
        public static final String VI_TRI_LAM_VIEC = "032";
        //Danh mục nhân viên
        public static final String DANH_MUC_NHAN_VIEN = "078";
    }

    public interface SQL_QUERIES {

        public static final String SQL_QUERY_INSERT_TRANSPORT_DATA = "call PKG_INSERT_TRANSPORT_DATA.PROC_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        public static final String SQL_QUERY_UPDATE_TRANSPORT_STATE = "call PKG_INSERT_TRANSPORT_DATA.PRO_UPDATE_TRANSPORT_STATE(?,?,?,?,?,?)";
        public static final String SQL_QUERY_INSERT_USERS_DEVICE = "call PRO_INSERT_USERS_DEVICE(?,?,?,?,?,?)";
        public static final String SQL_QUERY_UPDATE_USERS_DEVICE = "call UPDATE_USER_DEVICE_BY_PASS(?,?,?,?,?,?,?)";
        public static final String SQL_SMS_INSERT = "INSERT INTO message_sms "
                + " (MESSAGE_SMS_ID,"//1
                + " IMEI, TRANSPORT_ID, SMS_CREATE_DATE, SENDER_NUMBER,"//2,3,4,5
                + " CONTENT, CREATE_DATE)"//6,7
                + " VALUES (MESSAGE_SMS_SEQ.NEXTVAL,"//1
                + " ?, ?, ?, ?," //2  3  4  5
                + " ?, SYSDATE)";//6,7 
//        public static final String SQL_CHECK_DEVICE = "select d.device_code" +
//                       " from device d" +
//                  " left join connect_device cd on d.device_id = cd.device_id" +
//                  " left join transport t on d.device_id = t.device_id" +
//                      " where d.is_active =1" +
//                        " AND cd.is_active=1" +
//                        " AND t.is_active=1" +
//                        " AND d.device_code=?";  
        public static final String SQL_CHECK_DEVICE = "select t.transport_id, t.has_speeddometer, d.device_type_id "
                + " from device d"
                + " left join connect_device cd on d.device_id = cd.device_id"
                + " left join transport t on d.device_id = t.device_id"
                + " where d.is_active =1"
                + " AND cd.is_active=1"
                + " AND t.is_active=1"
                + " AND d.device_code=?";
//        public static final String SQL_INSERT_COUNT_CONNECTION = "INSERT INTO COUNT_CONNECTION (IMEI, CONNECT_DATE) values (?,sysdate)";

//        public static final String SQL_CHECK_DEVICE_V01 = "select imei,device_number,status from device_v01_log where imei = ? and status < 4";  
//        public static final String SQL_UPDATE_DEVICE_V01 = "update device_v01_log set status = ?, update_date = sysdate, ip = ?,port = ?, device_number= ? where imei = ?";  
//        public static final String SQL_CHECK_SEND_COMMAND = 
//                                          "select command "
//                                         + " from send_command "
//                                         + " where transport_id = ? "
//                                         + " and is_active = 1"
//                                         + " and status = 0 ";  
//        public static final String SQL_UPDATE_SEND_COMMAND = "update send_command set status = ? where transport_id = ?";
//        public static final String SQL_CHECK_RECONFIG_H1 = 
//                                          "select imei "
//                                         + " from reconfig_h1 "
//                                         + " where transport_id = ? ";  
//        public static final String SQL_INSERT_RECONFIG_H1 = "insert into reconfig_h1 (ID, TRANSPORT_ID, IMEI, CREATE_DATE) values (RECONFIG_H1_SEQ.NEXTVAL, ?, ?, sysdate)";
    }

    public interface MESSAGE_TYPE_ZTE {

        public static final String HEART_BEAT_TYPE = "T0";
        public static final String REGISTRATION_TYPE = "T1";
        public static final String DEVICE_PARAMETER_TYPE = "T2";
        public static final String POSITION_REPORT_TYPE = "T3";
        public static final String POWER_SUPPLY_TYPE = "T4";
        public static final String DOOR_TYPE = "T5";
        public static final String EMERGENCY_TYPE = "T6";
        public static final String LOW_BATTERY_TYPE = "T7";
        public static final String MOVEMENT_TYPE = "T8";
        public static final String ADDRESS_TYPE = "T9";
        public static final String REQUEST_ADDRESS_TYPE = "T10";
        public static final String ACTIVE_ANTI_THEFT_TYPE = "T12";
        public static final String DEACTIVE_ANTI_THEFT_TYPE = "T13";
        public static final String INTERNAL_DEVICE_TYPE = "T14";
        public static final String ENGINE_FUEL_TYPE = "T15";
        public static final String OVER_SPEED_TYPE = "T17";
        public static final String SEND_ANY_MESSAGE_TYPE = "T18";
        public static final String REV_SMS_MSG_TYPE = "T19";
        public static final String VIBRATION_TYPE = "T21";
        public static final String ACC_ILLEGAL_TYPE = "T25";
        public static final String SRV_SET_INTERNAL_DEVICE_PARAM_TYPE = "T30";
        public static final String SRV_REQUEST_POSITION_TYPE = "T31";
        public static final String SRV_DEVICE_REBOOT_TYPE = "T32";
        public static final String SRV_ACTIVE_ANTI_THEFT_TYPE = "T33";
        public static final String SRV_DEACTIVE_ANTI_THEFT_TYPE = "T34";
        public static final String SRV_QUERY_INTERNAL_DEVICE_PARAM_TYPE = "T35";
        public static final String SRV_DIS_ENA_FUEL_SUPPLY_TYPE = "T36";
        public static final String SRV_SEND_ANY_SMS_TYPE = "T37";
        public static final String SRV_REQUEST_CONNECTION = "T38";

        public static final String SRV_REQUEST_BALANCE_GPRS = "T39";
    }

    public interface MessageNotification {

        public static final String HAPPY_MESSAGE = "Đã gửi lệnh xuống thiết bị, vui lòng chờ trong giây lát";
        public static final String OFFLINE_MESSAGE = "Thiết bị không kết nối đến server, không gửi qua GPRS được, vui lòng thử với SMS";
        public static final String ADDRESS_MESSAGE = "Địa chỉ thiết bị: ";
        public static final String END_MESSAGE = "####";
        public static final String CHANGE_MESSAGE = "Thay đổi ";
        public static final String SUCCESS_MESSAGE = "Thành công";
        public static final String FAIL_MESSAGE = "Thất bại";
        public static final String ACTIVE_ANTI_THEFT_MESSAGE = "Bật chế độ chống trộm";
        public static final String DEACTIVE_ANTI_THEFT_MESSAGE = "Tắt chế độ chống trộm";
        public static final String T2_MESSAGE = "Thay đổi thông số: ";
        public static final String T10_MESSAGE = "Lấy vị trí: ";
        public static final String T12_T13_MESSAGE = "Chế độ chống trộm: ";
        public static final String T14_MESSAGE = "Lấy tham số: ";
        public static final String T18_MESSAGE = "Gửi tin nhắn: ";

    }

    public interface MESSAGE_TYPE_PCCC {

        public static final String DEVICE_WARNING_TYPE = "T";
        public static final String DEVICE_STATUS_TYPE = "Q";
        public static final String WARNING_CONTENT = "11111111|";
        public static final String WARNING_STATUS_CONTENT = "10000000|";
        public static final String NORMAL_STATUS_CONTENT = "00000000|";
    }

    public interface PCCC_STATUS {

        public static final int NOT_USE = 0;
        public static final int NORMAL = 1;
        public static final int LOST_PORT_CONNECTION = 2;
        public static final int LOST_DEVICE_CONNECTION = 3;
        public static final int WARNING = 4;
        public static final char NOT_USE_CHAR = '0';
        public static final char NORMAL_CHAR = '1';
        public static final char LOST_PORT_CONNECTION_CHAR = '2';
        public static final char WARNING_CHAR = '4';
    }

    public interface PCCC_WARNING_TYPE {

        public static final int SIMULATOR = 1;
        public static final int DEVICE = 2;
    }

    public interface FrameHelper {

        public static final String PACKAGE_END_MARK = "]";//.getBytes();
        public static final String PACKAGE_START_MARK = "[";//.getBytes();
        public static final String PACKAGE_SPLIT_MARK = ",";
        public static final String PACKAGE_END_MARK_LOGIN = "}";//.getBytes();
        public static final String PACKAGE_START_MARK_LOGIN = "{";//.getBytes();
    }

    public static final String serverAddress = Configurations.getInstance().getServerAddress();
    public static final String port = Configurations.getInstance().getPort();
    public static final String masterKeyAES = Configurations.getInstance().getMasterKeyAES();
    public static final String urlWarning = Configurations.getInstance().getUrlWarning();
    public static final String urlWarningHa = Configurations.getInstance().getUrlWarningHa();
    public static final String urlState = Configurations.getInstance().getUrlState();
    public static final String urlStateHa = Configurations.getInstance().getUrlStateHa();

//    static {
//        Properties sysProperties = new Properties();
//        InputStream is = null;
//        try {
//            is = new FileInputStream("../etc/mtr_config.conf");
//            sysProperties.load(is);
//            GPS_SPEED_CONFIG = Integer.parseInt(sysProperties.getProperty("device.gpsspeed"));
//            MIN_LATITUDE = Double.parseDouble(sysProperties.getProperty("device.latmin"));
//            MAX_LATITUDE = Double.parseDouble(sysProperties.getProperty("device.latmax"));
//            MIN_LONGTITUDE = Double.parseDouble(sysProperties.getProperty("device.lngmin"));
//            MAX_LONGTITUDE = Double.parseDouble(sysProperties.getProperty("device.lngmax"));
//            folderName = sysProperties.getProperty("folder", "E:\\logsw1v01");
//            serverAddress = sysProperties.getProperty("serverAddress", "");
//            port = sysProperties.getProperty("serverPort", "");
//            masterKeyAES = sysProperties.getProperty("masterKeyAES").trim();
//            urlWarning = sysProperties.getProperty("urlWarning").trim();
//
//        } catch (IOException | NumberFormatException ex) {
//            SmartLog.getInstance().logError(LOGGER, ex);
//
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException ex) {
//                    SmartLog.getInstance().logError(LOGGER, ex);
//                }
//            }
//        }
//    }
//    public interface SECURITY {
//        byte[] masterKeyAES = new byte[]{0x63,0x71,0x4F,0x46,0x6D,0x73,0x56,0x58,0x75,0x76,0x70,0x66,0x43,0x6F,0x5A,0x69};
//    }
}
