package io.loli.box.controller;

import io.loli.box.startup.LoliBoxConfig;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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

    @GET
    public Response index() {
        JetTemplate template = engine.getTemplate("/html/fullIndex.html");
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("email", LoliBoxConfig.getInstance().getEmail());
        context.put("cdnHost", LoliBoxConfig.getInstance().getCdnHost());
        context.put("httpsHost", LoliBoxConfig.getInstance().getHttpsHost());
        StringWriter writer = new StringWriter();
        template.render(context, writer);
        return Response.ok(writer.toString(), "text/html;charset=UTF-8")
                .build();
    }

    /**
     * Index page for http method POST
     * <p>
     * Some browsers like QQBrowser will send post request while change to
     * chrome model from IE model
     * 
     * @return Generated html of index page
     */
    @POST
    public Response indexPost() {
        return index();
    }

    @GET
    @Path("index.html")
    public Response indexHtml() {
        return index();
    }
}
