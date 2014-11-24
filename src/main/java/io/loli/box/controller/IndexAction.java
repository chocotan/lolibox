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
    @GET
    @Path("meta")
    public Viewable meta() {
        return new Viewable("/meta");
    }

    @Produces({ "text/html" })
    @GET
    @Path("uploader")
    public Viewable uploader() {
        return new Viewable("/uploader");
    }

    @Produces({ "text/html" })
    @GET
    @Path("footer")
    public Viewable footer() {
        return new Viewable("/footer");
    }

    @Produces({ "text/html" })
    @GET
    @Path("top")
    public Viewable top() {
        return new Viewable("/top");
    }

    @Produces({ "text/html" })
    @POST
    public Viewable indexPost() {
        return index();
    }

}
