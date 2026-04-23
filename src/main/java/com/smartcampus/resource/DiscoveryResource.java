/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Randil Henry
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {
    
    @GET
    public Response discover(@Context UriInfo uriInfo){
        String base = uriInfo.getBaseUri().toString();
        
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("api",     "Smart Campus Sensor & Room Management API");
        response.put("version", "1.0");
        response.put("contact", "admin@smartcampus.ac.uk");
        
        Map<String, String> links = new LinkedHashMap<>();
        links.put("rooms",   base + "rooms");
        links.put("sensors", base + "sensors");
        response.put("resources", links);

        return Response.ok(response).build();
    }
    
}
