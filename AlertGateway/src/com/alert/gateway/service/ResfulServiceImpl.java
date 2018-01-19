package com.alert.gateway.service;

import com.alert.gateway.database.SafeOneDAO;
import com.alert.gateway.message.DevicePositionReportMessageObject;
import com.alert.gateway.netty.ChannelManager;
import com.alert.gateway.utils.Constants.PCCC_STATUS;
import com.alert.gateway.utils.MappingrequestDTO;
import com.alert.gateway.utils.DataUtil;
import com.alert.gateway.utils.DeviceObject;
import com.alert.gateway.utils.SmartLog;
import com.alert.gateway.utils.Utils.RespondCode;
import com.safe.gateway.service.dto.DeviceConfigSOAPObject;
import com.safe.gateway.service.dto.ReadConfigDTO;
import com.safe.gateway.service.dto.WriteConfigDTO;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author LinhNV10
 */
public class ResfulServiceImpl implements ResfulServiceInterface {

    Properties prop;
    private long timeout = 30000; //timeout 5s
    private static final Logger LOGGER = Logger.getLogger(ResfulServiceImpl.class.getSimpleName());

    public ResfulServiceImpl() {
        try {
            prop = new Properties();
            prop.load(new FileInputStream(ApplicationInterceptor.AUTHEN_CONFIG));
            timeout = Integer.parseInt(prop.getProperty("timeout").trim());
        } catch (IOException ex) {
            SmartLog.getInstance().logError(LOGGER, "File cau hinh app_acess bi loi" + ex);
        }
    }

    @Override
    public Response setConfigDevice(HttpServletRequest httpServletRequest, WriteConfigDTO deviceDTO) {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            //Doc thong tin cau hinh của thiet bi.
            ResponServiceDTO respondService = new ResponServiceDTO();            
            DeviceConfigSOAPObject message = new DeviceConfigSOAPObject();
            StringBuilder commandText = new StringBuilder("[,S2,");
            //Validate device ID
            String deviceId = deviceDTO.getDeviceID();
            if (deviceId == null) {
                SmartLog.getInstance().logInfo(LOGGER, "device ID la null");
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_ERROR_PARAMETER);
                return Response.ok(respondService).build();
            }

            Map<String, String> parameterConfig = deviceDTO.getParameter();
            if (parameterConfig != null) {
                for (String keyConfig : parameterConfig.keySet()) {
                    commandText.append(keyConfig.trim());
                    commandText.append("=");
                    commandText.append(parameterConfig.get(keyConfig.trim()));
                    commandText.append(",");
                }
            } else {
                SmartLog.getInstance().logInfo(LOGGER, "Tham so la  null");
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_ERROR_PARAMETER);
                return Response.ok(respondService).build();
            }
            //Kiem tra tinh trang online của thiet bi
//Kiem tra tinh trang online của thiet bi
            DevicePositionReportMessageObject msgDevice = new DevicePositionReportMessageObject();
            msgDevice.setImei(deviceDTO.getDeviceID().trim());
            int deviceStatus = SafeOneDAO.getInstance().getCurrentDeviceStatus(msgDevice);
            if (deviceStatus != PCCC_STATUS.NORMAL) {
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_BUSY);
                return Response.ok(respondService).build();
            }
            if (DataUtil.GetMapRequestIdDevice().isEmpty()) {
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_BUSY);
                return Response.ok(respondService).build();
            }

            //Lay RequestId
            Integer requestId = DataUtil.GetMapRequestIdDevice().get(deviceDTO.getDeviceID()).getRequestId();
            Boolean configStatus = DataUtil.GetMapRequestIdDevice().get(deviceDTO.getDeviceID()).getConfigStatus();
            if (Objects.equals(configStatus, Boolean.TRUE)) {
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_ISCONFIG);
                return Response.ok(respondService).build();
            } else {
                DeviceObject dObject = new DeviceObject();
                dObject.setRequestId(requestId);
                dObject.setConfigStatus(Boolean.TRUE);
                DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), dObject);
            }

            if (requestId == null) {
                SmartLog.getInstance().logInfo(LOGGER, "Thiet bi chua login");
                //Thiet bi chua login thi reject no ra
                respondService.setRespondStatus(RespondCode.REQUEST_FAIL);
                DeviceObject dObject = new DeviceObject();
                dObject.setRequestId(requestId);
                dObject.setConfigStatus(Boolean.FALSE);
                DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), dObject);
                return Response.ok(respondService).build();
            }
            requestId++;
            String strRequestId = ("000" + requestId.toString());

            String command = commandText.substring(0, commandText.length() - 1);
            command = command + "," + strRequestId.substring(strRequestId.length() - 3) + "]";

            message.setCommandText(command);
            message.setCommandName(deviceDTO.getCommand());
            //message.setCommandText("[,S2,time = 123442,123]");
            message.setImei(deviceDTO.getDeviceID());
            message.setType(0);
            //SetInternalDeviceParamMessageObject message = new SetInternalDeviceParamMessageObject();
            //luu lai RequestId
            ChannelManager.serverRequestDevice(message);
            //Doi ket qua tra ve tu Map
            respondService = ProcessConfig(deviceDTO.getDeviceID(), requestId);
            DeviceObject deviceObject = new DeviceObject();
            deviceObject.setRequestId(requestId);
            deviceObject.setConfigStatus(Boolean.FALSE);
            DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), deviceObject);

            DeviceObject dObject = new DeviceObject();
            dObject.setRequestId(requestId);
            dObject.setConfigStatus(Boolean.FALSE);
            DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), dObject);
            return Response.ok(respondService).build();
        } catch (Exception e) {
            LOGGER.error(e);

        }
        return Response.noContent().build();
    }

    @Override
    public Response readConfigDevice(HttpServletRequest httpServletRequest, ReadConfigDTO deviceDTO) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            //Doc thong tin cau hinh của thiet bi.
            SmartLog.getInstance().logInfo(LOGGER, "Read config Request" + deviceDTO.toString());
            ResponServiceDTO respondService = new ResponServiceDTO();            
            DeviceConfigSOAPObject message = new DeviceConfigSOAPObject();
            StringBuilder commandText = new StringBuilder("[,S14,");
            //Validate device ID
            String deviceId = deviceDTO.getDeviceID();
            if (deviceId == null) {
                SmartLog.getInstance().logInfo(LOGGER, "device ID la null");
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_ERROR_PARAMETER);
                return Response.ok(respondService).build();
            }

            List<String> parameterConfig = deviceDTO.getParameter();
            if (parameterConfig != null) {
                for (String keyConfig : parameterConfig) {
                    commandText.append(keyConfig.trim());
                    commandText.append(",");
                }
            } else {
                SmartLog.getInstance().logInfo(LOGGER, "Tham so la  null");
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_ERROR_PARAMETER);
                return Response.ok(respondService).build();
            }
            //Kiem tra tinh trang online của thiet bi
            DevicePositionReportMessageObject msgDevice = new DevicePositionReportMessageObject();
            msgDevice.setImei(deviceDTO.getDeviceID().trim());
            int deviceStatus = SafeOneDAO.getInstance().getCurrentDeviceStatus(msgDevice);
            if (deviceStatus != PCCC_STATUS.NORMAL) {
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_BUSY);
                return Response.ok(respondService).build();
            }
            if (DataUtil.GetMapRequestIdDevice().isEmpty()) {
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_BUSY);
                return Response.ok(respondService).build();
            }

            //Lay RequestId
            Integer requestId = DataUtil.GetMapRequestIdDevice().get(deviceDTO.getDeviceID()).getRequestId();
            Boolean configStatus = DataUtil.GetMapRequestIdDevice().get(deviceDTO.getDeviceID()).getConfigStatus();
            if (Objects.equals(configStatus, Boolean.TRUE)) {
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_ISCONFIG);
                return Response.ok(respondService).build();
            } else {
                DeviceObject dObject = new DeviceObject();
                dObject.setRequestId(requestId);
                dObject.setConfigStatus(Boolean.TRUE);
                DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), dObject);
            }

            if (requestId == null) {
                SmartLog.getInstance().logInfo(LOGGER, "Thiet bi chua login");
                //Thiet bi chua login thi reject no ra
                respondService.setRespondStatus(RespondCode.REQUEST_FAIL);
                DeviceObject dObject = new DeviceObject();
                dObject.setRequestId(requestId);
                dObject.setConfigStatus(Boolean.FALSE);
                DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), dObject);
                return Response.ok(respondService).build();
            }
            requestId++;
            String strRequestId = ("000" + requestId.toString());

            String command = commandText.substring(0, commandText.length() - 1);
            command = command + "," + strRequestId.substring(strRequestId.length() - 3) + "]";

            message.setCommandText(command);
            message.setCommandName(deviceDTO.getCommand());
            //message.setCommandText("[,S2,time = 123442,123]");
            message.setImei(deviceDTO.getDeviceID());
            message.setType(0);
            //SetInternalDeviceParamMessageObject message = new SetInternalDeviceParamMessageObject();
            //luu lai RequestId
            ChannelManager.serverRequestDevice(message);
            //Doi ket qua tra ve tu Map
            respondService = ProcessConfig(deviceDTO.getDeviceID(), requestId);
            DeviceObject deviceObject = new DeviceObject();
            deviceObject.setRequestId(requestId);
            deviceObject.setConfigStatus(Boolean.FALSE);
            DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), deviceObject);

            DeviceObject dObject = new DeviceObject();
            dObject.setRequestId(requestId);
            dObject.setConfigStatus(Boolean.FALSE);
            DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), dObject);
            return Response.ok(respondService).build();
        } catch (Exception e) {
            LOGGER.error(e);

        }
        return Response.noContent().build();
    }

    private ResponServiceDTO ProcessConfig(String deviceId, Integer requestId) {
        ResponServiceDTO respondService = new ResponServiceDTO();
        MappingrequestDTO respond = new MappingrequestDTO();
        respond.setRespondStatus(Boolean.FALSE);
        DataUtil.GetCurrentRequest().put(requestId, respond);
        long timeStartCurrent = System.currentTimeMillis();
        Boolean status = Boolean.TRUE;
        while (status) {
            status = Boolean.FALSE;
            try {
                if ((System.currentTimeMillis() - timeStartCurrent) > timeout) {
                    respondService.setRespondStatus(RespondCode.REQUEST_TIMEOUT);
                    //return Response.ok(respondService).build();
                    DeviceObject dObject = new DeviceObject();
                    dObject.setRequestId(requestId);
                    dObject.setConfigStatus(Boolean.FALSE);
                    DataUtil.GetMapRequestIdDevice().put(deviceId, dObject);
                    if (DataUtil.GetCurrentRequest().contains(requestId)) {
                        DataUtil.GetCurrentRequest().remove(requestId);
                    }
                    SmartLog.getInstance().logInfo(LOGGER, "request bi time out");
                    return respondService;
                }
                //Xu ly doi ban tin
                Thread.sleep(5);
                respond = (MappingrequestDTO) DataUtil.GetCurrentRequest().get(requestId);
                if (Objects.equals(respond.getRespondStatus(), Boolean.TRUE)) {
                    String[] respondData = (String[]) respond.getResponData();
                    if (respondData == null || respondData.length == 0) {
                        SmartLog.getInstance().logInfo(LOGGER, "Thiet bi gui phan hoi nhung phan Data lai null - loi khong xac dinh");
                        respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_UNDEFINE);
                        if (DataUtil.GetCurrentRequest().contains(requestId)) {
                            DataUtil.GetCurrentRequest().remove(requestId);
                        }
                        return respondService;
                        //return Response.ok(respondService).build();
                    }
                    //Gui phan hoi len
                    //Phan tich de phan hoi len theo Parameter
                    Map<String, String> valueConfig = new HashMap<>();
                    for (String keyParam : respondData) {
                        if (keyParam.contains("=")) {
                            String[] param = keyParam.split("=", 2);
                            //if ("1".equals(param[1])) {
                            valueConfig.put(param[0], param[1]);
                            //} else {
                            //valueConfig.put(param[0], param[1]);
                            //}
                        }
                    }

                    respondService.setRespondStatus(RespondCode.REQUEST_OK);
                    respondService.setResult(valueConfig);
                    DeviceObject deviceObject = new DeviceObject();
                    deviceObject.setRequestId(requestId);
                    deviceObject.setConfigStatus(Boolean.FALSE);
                    DataUtil.GetMapRequestIdDevice().put(deviceId, deviceObject);
                    if (DataUtil.GetCurrentRequest().contains(requestId)) {
                        DataUtil.GetCurrentRequest().remove(requestId);
                    }
                    return respondService;
                    //return Response.ok(respondService).build();
                }
                Thread.sleep(15);
                status = Boolean.TRUE;
            } catch (InterruptedException ex) {
              SmartLog.getInstance().logError(LOGGER,"Can't start Server");
               SmartLog.getInstance().logError(LOGGER, ex);
               Thread.currentThread().interrupt();
                status = Boolean.FALSE;
                //Logger.getLogger(ResfulServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_UNDEFINE);
                if (DataUtil.GetCurrentRequest().contains(requestId)) {
                    DataUtil.GetCurrentRequest().remove(requestId);
                }
                //return respondService;
            }
        }
        if (DataUtil.GetCurrentRequest().contains(requestId)) {
            DataUtil.GetCurrentRequest().remove(requestId);
        }
        return respondService;
    }

    public Response getLastMessage(HttpServletRequest httpServletRequest, ReadConfigDTO deviceDTO) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            //Doc thong tin cau hinh của thiet bi.
            ResponServiceDTO respondService = new ResponServiceDTO();
            DeviceConfigSOAPObject message = new DeviceConfigSOAPObject();
            StringBuilder commandText = new StringBuilder("[,S33,");

//            Map<String, String> parameterConfig = deviceDTO.getParameter();
//            if (parameterConfig != null) {
//                for (String keyConfig : parameterConfig.keySet()) {
//                    commandText.append(keyConfig.trim());
//                    commandText.append("=");
//                    commandText.append(parameterConfig.get(keyConfig.trim()));
//                    commandText.append(",");
//                }
//            }
            //Kiem tra tinh trang online của thiet bi
            DevicePositionReportMessageObject msgDevice = new DevicePositionReportMessageObject();
            msgDevice.setImei(deviceDTO.getDeviceID().trim());
            int deviceStatus = SafeOneDAO.getInstance().getCurrentDeviceStatus(msgDevice);
            if (deviceStatus != PCCC_STATUS.NORMAL) {
                respondService.setRespondStatus(RespondCode.REQUEST_DEVICE_BUSY);
                return Response.ok(respondService).build();
            }
            if (DataUtil.GetMapRequestIdDevice().isEmpty()) {
                return Response.noContent().build();
            }

            //Lay RequestId
            Integer requestId = DataUtil.GetMapRequestIdDevice().get(deviceDTO.getDeviceID()).getRequestId();
            if (requestId == null) {
                //Thiet bi chua login thi reject no ra
                respondService.setRespondStatus(RespondCode.REQUEST_FAIL);
                return Response.ok(respondService).build();
            }
            requestId++;
            String strRequestId = ("000" + requestId.toString());

            String command = commandText.substring(0, commandText.length() - 1);
            command = command + "," + strRequestId.substring(strRequestId.length() - 3) + "]";

            message.setCommandText(command);
            message.setCommandName(deviceDTO.getCommand());
            //message.setCommandText("[,S2,time = 123442,123]");
            message.setImei(deviceDTO.getDeviceID());
            message.setType(0);
            //SetInternalDeviceParamMessageObject message = new SetInternalDeviceParamMessageObject();
            //luu lai RequestId
            ChannelManager.serverRequestDevice(message);
            //Doi ket qua tra ve tu Map
            respondService = ProcessConfig(deviceDTO.getDeviceID(), requestId);
            DeviceObject deviceObject = new DeviceObject();
            deviceObject.setRequestId(requestId);
            deviceObject.setConfigStatus(Boolean.FALSE);
            DataUtil.GetMapRequestIdDevice().put(deviceDTO.getDeviceID(), deviceObject);
            return Response.ok(respondService).build();
        } catch (Exception e) {
            LOGGER.error(e);

        }
        return Response.noContent().build();

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
