/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.exception.RoomNotEmptyException;
import com.smartcampus.model.Room;
import com.smartcampus.store.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 *
 * @author Randil Henry
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoom {
    
    private final DataStore store = DataStore.getInstance();
    
    @GET
    public Response getAllRooms() {
        return Response.ok(new ArrayList<>(store.getRooms().values())).build();
    }
    
    @POST
    public Response createRoom(Room room, @Context UriInfo uriInfo) {
        if (room.getId() == null || room.getId().isBlank()) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error",  "Room ID is required");
            error.put("status", 400);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error).build();
        }
        if (store.getRooms().containsKey(room.getId())) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error",  "A room with this ID already exists");
            error.put("roomId", room.getId());
            error.put("status", 409);
            return Response.status(Response.Status.CONFLICT)
                    .entity(error).build();
        }
        store.getRooms().put(room.getId(), room);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(room.getId()).build();
        return Response.created(location).entity(room).build();
    }
    
    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = store.getRooms().get(roomId);
        if (room == null) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error",  "Room not found");
            error.put("roomId", roomId);
            error.put("status", 404);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error).build();
        }
        return Response.ok(room).build();
    }
    
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = store.getRooms().get(roomId);
        if (room == null) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error", "Room not found");
            error.put("roomId", roomId);
            error.put("status", 404);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error).build();
        }

        long activeSensorCount = room.getSensorIds().stream()
                .map(id -> store.getSensors().get(id))
                .filter(s -> s != null && "ACTIVE".equalsIgnoreCase(s.getStatus()))
                .count();

        if (activeSensorCount > 0) {
            throw new RoomNotEmptyException(roomId, (int) activeSensorCount);
        }
        store.getRooms().remove(roomId);

        return Response.noContent().build();
    }
}
