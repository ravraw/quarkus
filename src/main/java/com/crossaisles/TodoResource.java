package com.crossaisles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/todos")
public class TodoResource {
    List<String> todoList = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getTodos() {
        return Response.ok(todoList).build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postTodo(String todo) {
        todoList.add(todo);
        return Response.accepted(todo).build();
    }

    @PUT
    @Path("/{oldTodo}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response putTodo(@PathParam("oldTodo") String oldTodo, @QueryParam("newTodo") String newTodo) {
        todoList = todoList.stream().map(todo -> todo.equals(oldTodo) ? newTodo : todo).collect(Collectors.toList());
        return Response.ok(todoList).build();
    }

    @DELETE
    @Path("/{todo}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTodo(@PathParam("todo") String todo) {
        System.out.printf(todo);
        boolean isRemoved = todoList.remove(todo);
        if (isRemoved) {
            return Response.ok(todoList).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
