/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.*;
/**
 *
 * @author Randil Henry
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable>{
    
    private static final Logger LOGGER =
            Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable e) {
        
        LOGGER.log(Level.SEVERE, "Unhandled exception caught by global mapper", e);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error",   "Internal Server Error");
        body.put("message", "An unexpected error occurred. Please contact the administrator.");
        body.put("status",  500);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(body).build();
    }
}
