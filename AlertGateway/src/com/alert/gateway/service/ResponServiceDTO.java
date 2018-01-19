/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alert.gateway.service;

/**
 *
 * @author LinhNV10
 */
public class ResponServiceDTO {
    private Integer respondStatus;
    private Object result;

    public Integer getRespondStatus() {
        return respondStatus;
    }

    public void setRespondStatus(Integer respondStatus) {
        this.respondStatus = respondStatus;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
}
