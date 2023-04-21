package com.crossaisles.entities;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/entities/todos")
public class TodoResource {
    @Inject
    TodoRepository todoRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodos() {
        List<Todo> todos = todoRepository.listAll();
        return Response.ok(todos).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        Optional<Todo> todo = todoRepository.findByIdOptional(id);
        if (todo.isPresent()) {
            return Response.ok(todo).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUserId(@PathParam("user_id") Long userId) {
        List<Todo> todo = todoRepository.list("user_id", userId);
            return Response.ok(todo).build();
    }

    @GET
    @Path("/{isCompleted}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByStatus(@PathParam("isCompleted") boolean isCompleted) {
        List<Todo> todo = todoRepository.list("isCompleted", true);
        return Response.ok(todo).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveTodo(Todo todo) {
        try {
            todoRepository.persist(todo);
            return Response.ok(todo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodo(@PathParam("id") Long id, Todo todo) {
        Optional<Todo> existingTodo = todoRepository.findByIdOptional(id);
        if (existingTodo.isPresent()) {
            if (Objects.nonNull(todo.getTitle())) {
                existingTodo.get().setTitle(todo.getTitle());
            }

            if (Objects.nonNull(todo.getBody())) {
                existingTodo.get().setBody(todo.getBody());
            }
            existingTodo.get().setCompleted(todo.isCompleted());

            try {
                todoRepository.persist(existingTodo.get());
                return Response.accepted(existingTodo).build();
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
