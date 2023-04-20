package com.crossaisles.entities;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/entities/todos")
public class TodoResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodos() {
        List<Todo> todos = Todo.listAll();
        return Response.ok(todos).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        Optional<Todo> todo = Todo.findByIdOptional(id);
        if (todo.isPresent()) {
            return Response.ok(todo).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveTodo(Todo todo) {
        Todo.persist(todo);
        if (todo.isPersistent()) {
            return Response.ok(todo).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodo(@PathParam("id") Long id, Todo todo) {
        Optional<Todo> exsistingTodo = Todo.findByIdOptional(todo);
        if (exsistingTodo.isPresent()) {
            if (Objects.nonNull(exsistingTodo.get().getTitle())) {
                exsistingTodo.get().setTitle(todo.getTitle());
            }

            if (Objects.nonNull(exsistingTodo.get().getBody())) {
                exsistingTodo.get().setBody(todo.getBody());
            }
            if (Objects.nonNull(exsistingTodo.get().isCompleted())) {
                exsistingTodo.get().setCompleted(todo.isCompleted());
            }
            exsistingTodo.get().persist();
            if (exsistingTodo.get().isPersistent()) {
                return Response.accepted(exsistingTodo).build();

            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
