package com.smart.api.resource;

import com.smart.api.exception.RoomNotEmptyException;
import com.smart.api.model.Room;
import com.smart.api.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    @POST
    public Response createRoom(Room room) {
        DataStore.rooms.put(room.getId(), room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}")
                    .build();
        }

        return Response.ok(room).build();
    }

   @DELETE
   @Path("/{roomId}")
   public Response deleteRoom(@PathParam("roomId") String roomId) {

        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Room not found!\"}")
                .build();
        }
        
   
        boolean hasSensors = DataStore.sensors.values()
                .stream()
                .anyMatch(sensor -> roomId.equals(sensor.getRoomId()));

        if (hasSensors) {
            throw new RoomNotEmptyException("Room cannot be deleted because it has sensors");
        }

        DataStore.rooms.remove(roomId);

        return Response.ok("{\"message\":\"Room deleted successfully\"}")
                .build();
    }
}