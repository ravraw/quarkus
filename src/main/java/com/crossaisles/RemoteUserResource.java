package com.crossaisles;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/remote-users")
public class RemoteUserResource {
    @Inject
    @RestClient
    RemoteUserProxy remoteUserProxy;
    @GET
    public Response getRemoteUsers() {
        List<RemoteUser> remoteUsers = remoteUserProxy.getRemoteUsers();
        return Response.ok(remoteUsers).build();
    }
}
