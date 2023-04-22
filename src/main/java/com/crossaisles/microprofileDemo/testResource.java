package com.crossaisles.microprofileDemo;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test/{num}")
public class testResource {
    @ConfigProperty(name = "MULTIPLICATION_VALUE", defaultValue = "1")
    int MULTIPLICATION_VALUE;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getResult(@PathParam("num") int num) {
        return Response.ok(num*MULTIPLICATION_VALUE).build();
    }
}
