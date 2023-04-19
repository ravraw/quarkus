package com.crossaisles.entities;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/entity")
public class UserResource {
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = User.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        User user = User.findById(id);
        return Response.ok(user).build();
    }

    @POST
    @Transactional
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) {
        User.persist(user);
        if (user.isPersistent()) {
            return Response.ok(URI.create(String.format("http://localhost:8080/entity/users/%s", user.id))).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Transactional
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response updateUser(@PathParam("id") Long id, User updateUser) {
        Optional<User> userFound = User.findByIdOptional(id);
        if (userFound.isPresent()) {
            if (Objects.nonNull(updateUser.getName())) {
                userFound.get().setName(updateUser.getName());
            }
            userFound.get().persist();
            if (userFound.get().isPersistent()) {
                return Response.ok(URI.create(String.format("http://localhost:8080/entity/users/%s", id))).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/users/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        boolean isDeleted = User.deleteById(id);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
