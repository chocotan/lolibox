package io.loli.box.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by 叶 on 2014/11/13.
 */
@Path("/hello")
public class HelloJersey {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "hello";
    }
}
