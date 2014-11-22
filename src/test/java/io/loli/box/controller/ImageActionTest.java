package io.loli.box.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import io.loli.box.startup.JerseyBaseTest;
import io.loli.box.startup.LoliBoxConfig;
import io.loli.box.util.FileUtil;
import io.loli.box.util.StatusBean;

import java.io.File;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.Test;

public class ImageActionTest extends JerseyBaseTest {

    @SuppressWarnings("resource")
    @Test
    public void testUpload() {
        // MediaType of the body part will be derived from the file.
        File file = new File(ImageAction.class.getResource("/test.jpg").getFile());
        final FileDataBodyPart filePart = new FileDataBodyPart("image", file);
        final MultiPart multipart = new FormDataMultiPart().bodyPart(filePart);
        final Response response = target(new LoliBoxConfig().getUrl()).path("image/upload")
            .request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(multipart, multipart.getMediaType()));
        assertEquals(200, response.getStatus());
        StatusBean result = response.readEntity(StatusBean.class);
        assertEquals("success", result.getStatus());
        assertNotNull(result.getMessage());
        String imgUrl = new LoliBoxConfig().getUrl() + result.getMessage();
        System.out.println(imgUrl);
        assertEquals(200, FileUtil.getUrlStatus(imgUrl));
    }
}
