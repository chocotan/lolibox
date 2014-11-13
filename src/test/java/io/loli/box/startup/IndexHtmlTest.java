package io.loli.box.startup;

import io.loli.box.startup.LoliBoxConfig;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by 叶 on 2014/11/14.
 * Test if index html can be found by url
 */
public class IndexHtmlTest extends GrizzlyBaseTest {

    @Test
    public void testHtml() {
        Client client = ClientBuilder.newClient();
        Response response = client.target(LoliBoxConfig.DEFAULT_URI)
                .path("web/index.html")
                .request(MediaType.TEXT_HTML).get();
        Assert.assertEquals(HttpStatus.OK_200.getStatusCode(), response.getStatus());
    }
}
