package io.loli.box.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Template
@Path("/")
public class IndexAction {

    @Produces({ "text/html" })
    @GET
    public Viewable index() {
        return new Viewable("/index", this);
    }

    @Produces({ "text/html" })
    @POST
    public Viewable indexPost() {
        return index();
    }

}
