package io.loli.box.controller;

import io.loli.box.service.StorageService;
import io.loli.box.util.StatusBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class ImageAction {

    @Path("upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public StatusBean upload(@FormDataParam("image") InputStream file,
        @FormDataParam("image") FormDataContentDisposition fileDisposition) {
        StorageService service = StorageService.getDefaultInstance();

        URL url;
        try {
            url = service.upload(file, fileDisposition.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
            return new StatusBean("error", "Error:" + e.getMessage());
        }
        if (url != null) {
            String urlStr = null;
            // TODO
            return new StatusBean("success", urlStr);
        } else {
            return new StatusBean("error", "Failed to upload");
        }

    }
}
