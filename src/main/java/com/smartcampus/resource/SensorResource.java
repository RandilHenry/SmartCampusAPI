/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.model.Sensor;
import com.smartcampus.store.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 *
 * @author Randil Henry
 */
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {
    
    private final DataStore store = DataStore.getInstance();
    
    @GET
    public Response getAllSensors(@QueryParam("type") String type) {
        List<Sensor> result = new ArrayList<>(store.getSensors().values());

        if (type != null && !type.isBlank()) {
            result = result.stream()
                    .filter(s -> s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }
        return Response.ok(result).build();
    }
    
    @POST
    public Response registerSensor(Sensor sensor, @Context UriInfo uriInfo) {
        
        if (sensor.getRoomId() == null ||
                !store.getRooms().containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException(sensor.getRoomId());
        }
        if (sensor.getId() == null || sensor.getId().isBlank()) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error",  "Sensor ID is required");
            error.put("status", 400);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error).build();
        }
        if (store.getSensors().containsKey(sensor.getId())) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error",    "A sensor with this ID already exists");
            error.put("sensorId", sensor.getId());
            error.put("status",   409);
            return Response.status(Response.Status.CONFLICT)
                    .entity(error).build();
        }
        store.getSensors().put(sensor.getId(), sensor);
        
        store.getRooms().get(sensor.getRoomId())
                .getSensorIds().add(sensor.getId());

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(sensor.getId()).build();
        return Response.created(location).entity(sensor).build();
    }
    
    @GET
    @Path("/{sensorId}")
    public Response getSensorById(@PathParam("sensorId") String sensorId) {
        Sensor sensor = store.getSensors().get(sensorId);
        if (sensor == null) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error",    "Sensor not found");
            error.put("sensorId", sensorId);
            error.put("status",   404);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error).build();
        }
        return Response.ok(sensor).build();
    }
    
    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(
            @PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}
