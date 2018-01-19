/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gateway.business.thread;

import com.alert.gateway.business.BaseBusiness;
import com.viettel.mmserver.base.ProcessThreadMX;

/**
 *
 * @author kid
 */
public class BusinessThread extends ProcessThreadMX{

    private BaseBusiness baseBusiness;
    
    public BusinessThread(String threadName) {
        super(threadName);
    }

    public BusinessThread(BaseBusiness baseBusiness, String threadName) {
        super(threadName);
        this.baseBusiness = baseBusiness;
    }
    /**
     * process business
     */
    @Override
    protected void process() {
        baseBusiness.processRequest();
    }

    public void setBaseBusiness(BaseBusiness baseBusiness) {
        this.baseBusiness = baseBusiness;
    }
    
}
