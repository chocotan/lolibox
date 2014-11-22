package io.loli.box.controller;

import io.loli.box.service.StorageService;
import io.loli.box.util.StatusBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

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

    private static Logger logger = Logger.getLogger(ImageAction.class.getName());

    @Path("upload")
    @POST
    @JSONP
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
            try {
                java.nio.file.Path path = java.nio.file.Paths.get(url.toURI());
                File f = path.toFile();
                String fileName = f.getName();
                urlStr = fileName;
            } catch (URISyntaxException e) {
                logger.warning("Invalid uri: " + e.getMessage());
            }
            return new StatusBean("success", "images/" + urlStr);
        } else {
            return new StatusBean("error", "Failed to upload");
        }
    }
}
