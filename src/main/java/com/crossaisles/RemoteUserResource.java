package com.crossaisles;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/remote-users")
public class RemoteUserResource {
    Logger remoteUserLogger = Logger.getLogger("RemoteUserResource.class");
    @Inject
    @RestClient
    RemoteUserProxy remoteUserProxy;

    @GET
    @Retry(maxRetries = 4)
    @Fallback(fallbackMethod = "getRemoteUsersFallback")
    public Response getRemoteUsers() {
        remoteUserLogger.info("trying to fetch remote users");
        List<RemoteUser> remoteUsers = remoteUserProxy.getRemoteUsers();
        return Response.ok(remoteUsers).build();
    }

    public Response getRemoteUsersFallback() {
        return Response.ok("We our upgrading our systems current to serve you better").build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        RemoteUser remoteUser = remoteUserProxy.getById(id);
        return Response.ok(remoteUser).build();
    }
}
