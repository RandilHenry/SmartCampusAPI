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
public class LinkedResourceNotFoundMapper implements ExceptionMapper<LinkedResourceNotFoundException>{
    
    @Override
    public Response toResponse(LinkedResourceNotFoundException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error",  "Unprocessable Entity");
        body.put("reason", "The roomId '" + e.getRoomId()
                + "' does not exist. Register the room before assigning sensors to it.");
        body.put("status", 422);

        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity(body).build();
    }
}
