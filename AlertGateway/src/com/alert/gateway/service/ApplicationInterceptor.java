/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alert.gateway.service;

import com.alert.gateway.utils.SmartLog;
import static com.alert.gateway.utils.Utils.SHA256Password;
import com.viettel.service.iso.ISOException;
import com.viettel.share.Util;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author LinhNV10
 */
public class ApplicationInterceptor extends AbstractInDatabindingInterceptor {

    private static final Logger LOGGER = Logger.getLogger(ApplicationInterceptor.class.getSimpleName());
    private Properties prop;
    public static final String AUTHEN_CONFIG = "../etc/service-authen.conf";
    String userName = "userName";
    String keyAuthen = "password";
    String timeout = "timeout";
    String whitelistIp = "whitelistIp";
    String salt = "salt";

    public ApplicationInterceptor(String phase) {
        super(Phase.USER_STREAM);
        prop = new Properties();
    }

    @Override
    public void handleMessage(Message inMessage) throws Fault {
        try {
            HttpServletRequest request = (HttpServletRequest) inMessage.get(AbstractHTTPDestination.HTTP_REQUEST);
            //String ip = request.getRemoteAddr();
            //String session = request.getHeader("sessionId");
            String requestURL = request.getRequestURL().toString();//+ "_" + request.getMethod();
            //String url = request.getPathInfo();
            //kiem tra Token, usernam cua ung dung
            SmartLog.getInstance().logInfo(LOGGER, "requestURL" + requestURL);
            //logDataService.info(inMessage.get);
            if (!isValidateApplication(request)) {
                respondService(HttpServletResponse.SC_UNAUTHORIZED, "application is not authorization", inMessage);
            }
        } catch (IOException ex) {
            SmartLog.getInstance().logInfo(LOGGER, "handleMessage exception" + ex);
        }
        SmartLog.getInstance().logInfo(LOGGER, "App Authen Sucessfull");
        //cap nhat lai session neu qua duoc toi day
    }

    //Kiem tra tinh chinh xac cua ung dung
    private boolean isValidateApplication(HttpServletRequest request){
        String ipApp = request.getRemoteAddr();
        String appUserName;
        String appPasword;
        String basic = request.getHeader("Authorization");
        //logDataService.info("ip = " + ipApp + "basicauthen = " + basic + "authentication" + "applicationCode = " + applicationCode);
        if ((basic == null) || (basic.length() < 6) || (!"Basic ".equalsIgnoreCase(basic.substring(0, 6)))) {
            SmartLog.getInstance().logInfo(LOGGER, "thieu ma ung dung hoac chua co basic authen");
            return false;
        } else {
            try {
                String credentials = Util.base64Decode(basic.substring(6));
                //info("Credentials: " + credentials);
                int p = credentials.indexOf(':');
                if (p != -1) {
                    //Lay danh sach usernam va pass tu client gui ve
                    appUserName = credentials.substring(0, p).trim();
                    appPasword = credentials.substring(p + 1).trim();
                    //lay userName va pass trong file config
                    prop.load(new FileInputStream(AUTHEN_CONFIG));
                    String appUserConfig = prop.getProperty(userName).trim();
                    String passUserConfig = prop.getProperty(keyAuthen).trim();
                    String whiteListIpConfig = prop.getProperty(whitelistIp).trim();
                    String saltConfig = prop.getProperty(salt).trim();
                    if (appUserConfig == null || passUserConfig == null || whiteListIpConfig == null || saltConfig == null) {
                        SmartLog.getInstance().logError(LOGGER, "Loi khong cau hinh du user,pass va whitelistIp");
                        return false;
                    }
                    //Kiem tra whitelistIp
                    if (!whiteListIpConfig.contains(ipApp)) {
                        SmartLog.getInstance().logError(LOGGER, "Authen fail vi khong cau hinh IP");
                        return false;
                    }
                    
                    if (appUserConfig.equals(appUserName)) {
                        String passwordSHA256_compare = SHA256Password(appPasword, saltConfig);
                        if (passwordSHA256_compare.equalsIgnoreCase(passUserConfig)) {
                            SmartLog.getInstance().logError(LOGGER, "Authen thanh cong vi co user va password");
                            return true;
                        }
                    }
                }
                return false;
            } catch (ISOException | NoSuchAlgorithmException | IOException ex) {
                SmartLog.getInstance().logError(LOGGER, "Authen thanh cong vi co user va password");
                SmartLog.getInstance().logError(LOGGER, ex);
            }
        }
        return false;
    }

    private void respondService(int errorCode, String msg, Message inMessage) throws IOException {
        InterceptorChain chain = inMessage.getInterceptorChain();
        HttpServletResponse response = (HttpServletResponse) inMessage.getExchange()
                .getInMessage().get(AbstractHTTPDestination.HTTP_RESPONSE);
        response.setStatus(errorCode);
        response.setContentType("application/json");
        response.getOutputStream().write(msg.getBytes());
        response.getOutputStream().flush();
        chain.abort();
    }

    public static void main(String[] args) {
        try {
            String passwordSHA256_compare = SHA256Password("eae6ac04102e046321e9ce79d085bf0bf8c4d1bc605899002c1b78c0b2d2199e", "PoPhpy");
            System.out.println("com.viettel.safe.rest.ApplicationInterceptor.main(): "+passwordSHA256_compare);
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(ApplicationInterceptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
