/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: SafeOneDAO.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.database;


import com.alert.gateway.message.DevicePositionReportMessageObject;
import com.alert.gateway.service.RestRequest;
import com.alert.gateway.utils.Constants;
import com.alert.gateway.utils.Constants.PCCC_STATUS;
import com.alert.gateway.utils.Constants.PCCC_WARNING_TYPE;
import static com.alert.gateway.utils.Constants.urlState;
import static com.alert.gateway.utils.Constants.urlStateHa;
import static com.alert.gateway.utils.Constants.urlWarning;
import static com.alert.gateway.utils.Constants.urlWarningHa;
import com.alert.gateway.utils.SmartLog;
import com.alert.gateway.utils.Utils;
import com.safe.gateway.service.dto.SynchronizeDataDTO;
import com.safe.gateway.service.dto.WarningObject;
import com.safe.gateway.service.dto.WarningUser;
import io.netty.channel.ChannelHandlerContext;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.bind.DatatypeConverter;
import org.apache.log4j.Logger;


/**
 *
 * @author hoahv5
 */
public class SafeOneDAO {

    /**
     * Danh sach thiet bi va trang thai Map<Imei,Status>
     */
    //public Map<String, Integer> lsDeviceStatus = new HashMap();
    /**
     * Luu socket va imei cua thiet bi dang co ket noi:
     * Map<ChannelHandlerContext,imei>
     */
//    private Map<ChannelHandlerContext, String> lsChannelDeviceConnected = new HashMap();
    static volatile SafeOneDAO instance;
    private static volatile Logger LOGGER;
    public static final String SQL_INSERT_TEMP_WARNING
            = "INSERT INTO "
            + "    tbl_tmp_warnings (id_device, port_number, is_warn, start_time,update_time, user_ack,warn_status)"
            + "     VALUES(?,?,?,?,?,?,?)";
    public static final String SQL_UPDATE_TEMP_WARNING
            = "UPDATE tbl_tmp_warnings "
            + "SET update_time=now(),warn_status=? "
            + "WHERE gid IN ("
            + "                 SELECT gid FROM tbl_tmp_warnings "
            + "                 WHERE id_device = (SELECT a.gid from tbl_device a WHERE a.code_device = ?) AND warn_status=? "
            + "                 ORDER BY start_time desc limit 1"
            + "             ); ";

    private static final String PORT_NUMBER = "1";
    private static final String SQL_CHECK_DEVICE
            = "SELECT count(*) as device_count "
            + "FROM tbl_tmp_device_status a JOIN tbl_device b ON a.id_device=b.gid "
            + "WHERE b.code_device=?;";
    private final String SMS_CONTENT = "";
    private final String SQL_INSERT_CALLING
            = "INSERT INTO tbl_call(gid,type,mobile_number,id_device,audio_file_name,created_time,state,resource) "
            + "VALUES(nextval('tbl_call_gid_seq'),1,?,?,?,now(),0,'TCPGATEWAY');";
    public static final String SQL_UPDATE_CANCEL_CALL = "UPDATE tbl_call SET state=? "
            + "WHERE id_device=? AND state=0;";
    //LinhNV10
    //Update tmp_message_log
    public static final String SQL_INSERT_TMP_MESSAGE_LOG
            = "INSERT INTO"
            + "    tbl_tmp_message_log (id_device, message_type, message_content, time_created, source, source_ip"
            + "    VALUES(?,?,?,?,?,?)";
    public static final String SQL_INSERT_SMS_HISTORY = "INSERT INTO "
            + "     tbl_sms_history(sms_type,sms_content,send_status,mobile,node_id,node_ip,sms_gateway_address,request_content,sms_template_id,service_code) "
            + " VALUES(?,?,?,?,?,?,?,?,?,?) RETURNING gid;";
    public static final String SQL_INSERT_CALL_HISTORY = "INSERT INTO "
            + "     tbl_call_history(service_code,request_content,send_status,call_gateway_address,mobile,node_id,node_ip,call_type) "
            + " VALUES(?,?,?,?,?,?,?,?) RETURNING gid;";

    public SafeOneDAO() {
        LOGGER = Logger.getLogger(SafeOneDAO.class.getSimpleName());
    }

    public static SafeOneDAO getInstance() {
        if (instance == null) {
            instance = new SafeOneDAO();
        }
        return instance;
    }

    public synchronized boolean getDeviceIdByChannel(ChannelHandlerContext ctx) {
        boolean ret = false;
        if (ctx != null) {

        }
        return ret;
    }

    public int getCurrentDeviceStatus(DevicePositionReportMessageObject message) {
        int ret = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        try {
            sql = "SELECT a.device_status "
                    + "FROM tbl_tmp_device_status a inner join tbl_device b on a.id_device = b.gid "
                    + "WHERE b.code_device=? limit 1;";
            connection = SafeOneConnectionPool.getInstance().getConnection();
            ps = connection.prepareCall(sql);
            ps.setString(1, message.getImei().trim());
            rs = ps.executeQuery();
            if (rs.next()) { // Co thiet bi tren he thong
                try {
                    ret = Integer.parseInt(rs.getObject("device_status").toString());
                } catch (NumberFormatException ex) {
                    SmartLog.getInstance().logError(LOGGER, ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            SmartLog.getInstance().logError(LOGGER, "getCurrentDeviceStatus->Exception when query DB with imei " + message.getImei() + ". " + ex);
            ret = 0;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    SmartLog.getInstance().logDebug(LOGGER, "Ignored " + e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    SmartLog.getInstance().logDebug(LOGGER, "Ignored " + e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    SmartLog.getInstance().logDebug(LOGGER, "Ignored " + e);
                }
            }
        }
        return ret;
    }
   //Lay danh sach thiet bị da duoc gan vao toa nha, key thiet bi de toi uu hieu nang
    public Map<String, String> getAllKeyDeviceMap() {
        String sql = "";
        Connection dbConnection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, String> listDeviceAesKey = null;
        try {
            listDeviceAesKey = new ConcurrentHashMap<>();
            sql = "SELECT a.code_device, a.aes_key FROM tbl_device a, tbl_settlements b where a.id_settlements=b.gid AND a.device_status=1;";
            dbConnection = SafeOneConnectionPool.getInstance().getConnection();
            ps = dbConnection.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs != null && rs.next()) {
                String ret1 = rs.getObject("code_device") != null ? rs.getObject("code_device").toString() : "";
                String ret2 = rs.getObject("aes_key") != null ? rs.getObject("aes_key").toString() : "";
                listDeviceAesKey.put(ret1, ret2);

            }
        } catch (SQLException e) {
            SmartLog.getInstance().logError(LOGGER, "get AES Key Error" + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    SmartLog.getInstance().logError(LOGGER, ex);
                }
                rs = null;
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    SmartLog.getInstance().logError(LOGGER, ex);
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    SmartLog.getInstance().logInfo(LOGGER, ex);
                }
            }

        }
        //doi convert thanh dang hexa
        return listDeviceAesKey;
    }
}
