package com.taxholic.cxf.service;

import java.io.UnsupportedEncodingException;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.taxholic.core.dao.CommonDao;
import com.taxholic.cxf.dto.Rest;
import com.taxholic.cxf.dto.RestList;


public class RestService {
	
	@Resource
	private CommonDao commonDao;
	
	/*
	@POST – This indicates that service receives only POST request.
	@Path – This is the path of webservice
	@Produces – Indicates the MIME type of response generated. In our case it is both application/xml and application/json.
	@Consumes – Indicates the MIME type of request which this service can consume 
	*/
	
	@POST
//	@GET
    @Path("/xml/roleList")
	@Produces("application/xml")
	@Consumes({"application/xml","application/x-www-form-urlencoded"})
    public Response roleXmlList(@FormParam("userId") String userId, @FormParam("password") String password) throws UnsupportedEncodingException {
		
		
		List<Rest> roleList = this.commonDao.getList("front.Cxf.getRestRoleList");
        RestList restList  = new RestList();
        restList.setRoleList(roleList);
        
        if(roleList == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }else{
            return Response.ok(restList).build();
        }

	}
	
//	@POST
	@GET
    @Path("/json/roleList")
	@Produces("application/json")
	@Consumes({"application/json","application/x-www-form-urlencoded"})
    public Response roleJsonList(@FormParam("userId") String userId, @FormParam("password") String password) throws UnsupportedEncodingException {
		
		
		List<Rest> roleList = this.commonDao.getList("front.Cxf.getRestRoleList");
        RestList restList  = new RestList();
        restList.setRoleList(roleList);
        
        for(Rest list  : roleList){
        	System.out.println("-->" + list.getRoleCd());
        }
        
        
        if(roleList == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }else{
            return Response.ok(restList).build();
        }

	}
}
