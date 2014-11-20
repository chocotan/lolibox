package io.loli.box.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Viewable;

@Path("/")
public class IndexAction {

    @GET
    @Produces({ "text/html" })
    public Viewable index(@Context HttpServletRequest request, @Context HttpServletResponse response)
        throws ServletException, IOException {
        return new Viewable("/index");
    }
}
