package com.crossaisles.entities;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/entity")
public class UserResource {
    @Inject
    UserRepository userRepository;

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = userRepository.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        User user = userRepository.findById(id);
        return Response.ok(user).build();
    }

    @POST
    @Transactional
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) {
        userRepository.persist(user);
        return userRepository.isPersistent(user) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();

    }

    @PUT
    @Transactional
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, User updateUser) {
        Optional<User> userFound = userRepository.findByIdOptional(id);
        if (userFound.isPresent()) {
            if (Objects.nonNull(updateUser.getName())) {
                userFound.get().setName(updateUser.getName());
            }
            return userRepository.isPersistent(userFound.get()) ?
                    Response.ok().build() :
                    Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/users/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
         return userRepository.deleteById(id) ?
                 Response.noContent().build() :
                 Response.status(Response.Status.BAD_REQUEST).build();
    }
}
