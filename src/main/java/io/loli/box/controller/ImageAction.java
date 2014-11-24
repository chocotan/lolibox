package io.loli.box.controller;

import io.loli.box.service.AbstractStorageService;
import io.loli.box.service.StorageService;
import io.loli.box.util.StatusBean;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.JSONP;

@Path("/image")
public class ImageAction {
    @POST
    @JSONP
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public StatusBean upload(@FormDataParam("image") InputStream file,
        @FormDataParam("image") FormDataContentDisposition fileDisposition) {
        StorageService service = AbstractStorageService.getDefaultInstance();

        String url;
        try {
            url = service.upload(file, fileDisposition.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
            return new StatusBean("error", "Error:" + e.getMessage());
        }
        if (url != null) {
            return new StatusBean("success", "images/" + url);
        } else {
            return new StatusBean("error", "Failed to upload");
        }
    }
}
