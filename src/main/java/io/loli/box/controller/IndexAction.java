package io.loli.box.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/")
public class IndexAction {

    @GET
    public Response index() {
        return Response.temporaryRedirect(UriBuilder.fromUri("/web/index.html").build()).build();
    }
}
