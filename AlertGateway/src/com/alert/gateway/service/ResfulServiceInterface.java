/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alert.gateway.service;

import com.safe.gateway.service.dto.ReadConfigDTO;
import com.safe.gateway.service.dto.WriteConfigDTO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author LinhNV10
 */
public interface ResfulServiceInterface {
    //Notify
    @POST
    @Path("/readDeviceConfig")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response readConfigDevice(@Context HttpServletRequest httpServletRequest,ReadConfigDTO deviceDTO);
    //Notify
    @POST
    @Path("/setDeviceConfig")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response setConfigDevice(@Context HttpServletRequest httpServletRequest,WriteConfigDTO deviceDTO);
//    @POST
//    @Path("/readLastMessage")
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getLastMessage(@Context HttpServletRequest httpServletRequest,ReadConfigDTO deviceDTO);
}
