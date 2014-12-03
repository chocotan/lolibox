package io.loli.box.controller;

import io.loli.box.startup.LoliBoxConfig;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;

import org.glassfish.jersey.server.mvc.Template;

/**
 * Action for index page "/" and "index.html"
 * 
 * @author choco
 *
 */
@Template
@Path("/")
public class IndexAction {

    /**
     * Template engine to generate html
     */
    private static JetEngine engine = JetEngine.create();

    /**
     * Default encoding
     */
    private static final String encoding = "UTF-8";

    @Produces("text/html; charset=" + encoding)
    @GET
    public String index() {
        JetTemplate template = engine.getTemplate("/html/fullIndex.html");
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("email", LoliBoxConfig.getInstance().getEmail());
        StringWriter writer = new StringWriter();
        template.render(context, writer);
        return writer.toString();
    }

    /**
     * Index page for http method POST
     * <p>
     * Some browsers like QQBrowser will send post request while change to
     * chrome model from IE model
     * 
     * @return Generated html of index page
     */
    @Produces("text/html; charset=" + encoding)
    @POST
    public String indexPost() {
        return index();
    }

    @Produces("text/html; charset=" + encoding)
    @GET
    @Path("index.html")
    public String indexHtml() {
        return index();
    }
}
