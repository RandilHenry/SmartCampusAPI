/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author Randil Henry
 */
@Provider
public class SensorUnavailableMapper implements ExceptionMapper<SensorUnavailableException>{
    
    @Override
    public Response toResponse(SensorUnavailableException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error",  "Sensor unavailable");
        body.put("reason", "Sensor '" + e.getSensorId()
                + "' is under MAINTENANCE and cannot record readings.");
        body.put("status", 403);
        return Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity(body).build();
    }
    
}
