/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.exception.SensorUnavailableException;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.store.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author Randil Henry
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {
    
    private final String sensorId;
    private final DataStore store = DataStore.getInstance();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }
    
    @GET
    public Response getReadings() {
        Sensor sensor = store.getSensors().get(sensorId);
        if (sensor == null) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error",    "Sensor not found");
            error.put("sensorId", sensorId);
            error.put("status",   404);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error).build();
        }
        return Response.ok(store.getReadingsForSensor(sensorId)).build();
    }
    
    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = store.getSensors().get(sensorId);
        if (sensor == null) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error",    "Sensor not found");
            error.put("sensorId", sensorId);
            error.put("status",   404);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error).build();
        }
        
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())
                || "OFFLINE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException(sensorId);
        }

        SensorReading newReading = new SensorReading(reading.getValue());


        sensor.setCurrentValue(newReading.getValue());

        store.getReadingsForSensor(sensorId).add(newReading);
        return Response.status(Response.Status.CREATED)
                .entity(newReading).build();
    }
}
