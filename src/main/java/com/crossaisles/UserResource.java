package com.crossaisles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Path("/users")
public class UserResource {
    List<User> users = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(users).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDto userDto) {
        User newUser = new User(userDto.name, userDto.age);
        users.add(newUser);
        return Response.accepted(newUser).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response updateUser(@PathParam("id") String id, UserDto userDto) {
        AtomicReference<Optional<User>> currentUser = new AtomicReference<>(Optional.empty());
        AtomicReference<Response> response = new AtomicReference<>(Response.status(Response.Status.BAD_REQUEST).build());
        users = users.stream().map(u -> {
            if (Objects.equals(u.getId(), id)) {
                u.setName(userDto.name);
                u.setAge(userDto.age);
                currentUser.set(Optional.of(u));
                response.set(Response.ok(u).build());
            }
            return u;
        }).collect(Collectors.toList());
        return response.get();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") String id) {
        Optional<User> user = users.stream().filter(u -> Objects.equals(u.getId(), id)).findFirst();
        if (user.isPresent()) {
            users.remove(user.get());
            return Response.ok(users).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
