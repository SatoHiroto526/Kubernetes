package com.example.resource;

import com.example.servie.Service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RequestScoped
@Path("api")
public class Resource {

    @Inject
    private Service service;

    @Path("msg")
	@Produces(MediaType.TEXT_PLAIN)
	@GET
    public String getMsg() {
        return "Hello Kubernetes!!!";
    }

    @Path("configMapEnv")
    @Produces(MediaType.TEXT_PLAIN)
	@GET
    public String getConfigMapEnv() {
        return service.getConfigMapEnv();
    }

    @Path("secretMountData")
    @Produces(MediaType.TEXT_PLAIN)
	@GET
    public String secretMountData() {
        return service.secretMountData();
    }

    
}
