package io.loli.box.controller;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Named
@Template
@Path("/")
public class IndexAction {

    @GET
    @Produces({ "text/html" })
    public Viewable index() {
        return new Viewable("/index", this);
    }

}
