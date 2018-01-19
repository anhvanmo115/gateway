/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gateway.business.thread;

import com.alert.gateway.business.BaseBusiness;
import com.alert.gateway.message.FrameObject;
import com.alert.gateway.utils.DataUtil;
import com.viettel.mmserver.base.ProcessThreadMX;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author kid
 */
public class RoutingBusinessThread extends ProcessThreadMX{

    private static ApplicationContext ctx;
    public RoutingBusinessThread(String threadName) {
        super(threadName);
        ctx = new ClassPathXmlApplicationContext("../etc/application_context.xml");
    }

    @Override
    protected void process() {
        try {
            FrameObject frameObject = DataUtil.getRequestMessageQueue().take();
            //b1 tim business
            BaseBusiness bu = ctx.getBean("bussiness." + frameObject.getMessageType(), BaseBusiness.class);
            //decode request thanh objct tuong             
            bu.decode(frameObject);
            //b2 tao moi bu thread
            BusinessThread but = new BusinessThread("");
            
            //b3 dung pool de execute thread ư
        } catch (InterruptedException ex) {
            Logger.getLogger(RoutingBusinessThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
}
