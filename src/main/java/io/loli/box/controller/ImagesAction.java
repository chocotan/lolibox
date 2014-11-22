package io.loli.box.controller;

import io.loli.box.startup.LoliBoxConfig;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

@Named
@Path("/images")
@Singleton
public class ImagesAction {

    public static MimetypesFileTypeMap mineUtil = new MimetypesFileTypeMap();

    static {
        mineUtil.addMimeTypes("image/png png");
    }

    @GET
    @Path("/{image}")
    @Produces("image/*")
    public Response getImage(@PathParam("image") String image) {
        File f = new File(LoliBoxConfig.getInstance().getSavePath(), image);
        if (!f.exists()) {
            String mt = "image/png";
            f = new File(ImagesAction.class.getResource("/404.png").getFile());
            return Response.ok(f, mt).cacheControl(CacheControl.valueOf("max-age=86400")).status(404).build();
        }

        String mt = mineUtil.getContentType(f);
        return Response.ok(f, mt).cacheControl(CacheControl.valueOf("max-age=86400")).build();
    }

}
