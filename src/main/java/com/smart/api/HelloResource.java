package com.smart.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class HelloResource {

    @GET
    public String hello() {
        return "API is working!";
    }
}