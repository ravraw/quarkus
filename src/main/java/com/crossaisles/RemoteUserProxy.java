package com.crossaisles;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
@Path("/")
@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")
public interface RemoteUserProxy {
    @GET
    @Path("users")
    List<RemoteUser> getRemoteUsers();

    @GET
    @Path("/users/{id}")
    RemoteUser getById(@PathParam("id") String id);
}
