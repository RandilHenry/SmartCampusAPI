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
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException>{
    
    @Override
    public Response toResponse(RoomNotEmptyException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error",  "Room cannot be deleted");
        body.put("reason", "Room '" + e.getRoomId() + "' still has "
                + e.getSensorCount()
                + " active sensor(s). Remove all sensors first.");
        body.put("status", 409);
        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(body).build();
    }
    
}
