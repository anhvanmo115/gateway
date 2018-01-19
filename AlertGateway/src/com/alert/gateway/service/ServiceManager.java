/*
 * @Meter Digital Project
 * @ServiceManager	version 1.0	08-06-2016
 *
 * Copyright 2016 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.alert.gateway.service;

import com.alert.gateway.database.SafeOneDAO;
import com.alert.gateway.utils.SafeConfig;
import com.viettel.mmserver.base.ProcessThreadMX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.log4j.Logger;
//import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

/**
 *
 * @author hoahv5
 */
public class ServiceManager extends ProcessThreadMX {

    static Logger LOG = Logger.getLogger(ServiceManager.class.getSimpleName());
    private static volatile ServiceManager instance;
    private Properties prop;
    private Server server;
    private String urlServiceBase;
    protected Map<String, String> mapDeviceKey;
    protected volatile List<String> listDevice;

    public ServiceManager(String threadName) {
        super(ServiceManager.class.getSimpleName());
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager(ServiceManager.class.getSimpleName());
        }
        return instance;
    }

    public synchronized List<String> getListDevice() {
        return listDevice;
    }

    public synchronized void setListDevice(List<String> listDevice) {
        this.listDevice = listDevice;
    }

    public Map<String, String> getMapDeviceKey() {
        return mapDeviceKey;
    }

    public void setMapDeviceKey(Map<String, String> mapDeviceKey) {
        this.mapDeviceKey = mapDeviceKey;
    }

    public String getUrlServiceBase() {
        return urlServiceBase;
    }

    public void setUrlServiceBase(String urlServiceBase) {
        this.urlServiceBase = urlServiceBase;
    }
    private List filterService;

    public List getFilterService() {
        return filterService;
    }

    public void setFilterService(List filterService) {
        this.filterService = filterService;
    }

    //khoi tao ket noi db
    public boolean init() {
        //lay danh sach thiet bi
        mapDeviceKey = SafeOneDAO.getInstance().getAllKeyDeviceMap();
        if (!mapDeviceKey.isEmpty() && mapDeviceKey.size() > 0) {
            listDevice = new ArrayList<>(mapDeviceKey.keySet());
        }
        prop = new Properties();
        return true;
    }

    private boolean initWebService() throws IOException {
        //urlServiceBase = prop.getProperty("base_url").trim();
        //urlServiceBase = "http://0.0.0.0:8813/pcccService/";
        urlServiceBase = SafeConfig.getInstance().getWebServiceAddress();
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        ApplicationInterceptor appInterceptor = new ApplicationInterceptor("Authen");
        //sf.getInInterceptors().add(new SSLInterceptor());
        sf.getInInterceptors().add(appInterceptor);

        sf.setProvider(new JacksonJaxbJsonProvider());
        sf.setResourceClasses(ResfulServiceImpl.class);
        sf.setAddress(urlServiceBase);
        ResfulServiceImpl etcdResource = new ResfulServiceImpl();

        sf.setResourceProvider(ResfulServiceImpl.class, new SingletonResourceProvider(etcdResource));
        try {
            server = sf.create();
        } catch (Exception e) {
            LOG.error(e);
        }
        return true;
    }

    @Override
    public void start() {
        registerAgent();
        initAll();
        if (server != null) {
            if (server.isStarted() == false) {
                server.start();
            }
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            server.stop();
        }
    }

    @Override
    public void restart() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInfor() {
        return "DataService is running";
    }

    public void initAll() {
        try {
            logger.info("start..");
            init();
            initWebService();
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    @Override
    protected void process() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void registerAgent() {
        try {
            LOG.info("DataServiceManager register Agent");
            registerAgent("DataServiceManager:type=DataServiceManager");
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException ex) {
            LOG.error("Exception when registerAgent:", ex);
        }
    }

    @Override
    public void unregisterAgent() {
        try {
            LOG.info("DataServiceManager Unregister Agent");
            unregisterAgent("DataServiceManager:type=DataServiceManager");
        } catch (MalformedObjectNameException | InstanceNotFoundException | MBeanRegistrationException ex) {
            LOG.error("Exception when UnregisterAgent:", ex);
        }
    }

}
