package io.loli.box.startup;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by 叶 on 2014/11/14.
 */
public class GrizzlyBaseTest {
    private static LoliBoxServer server = null;

    @BeforeClass
    public static void beforeClass() throws IOException {
        server = new LoliBoxServer();
        server.start();
    }

    @AfterClass
    public static void afterClass() {
        server.stop();
    }

    @Test
    public void testHello() {
        Client client = ClientBuilder.newClient();
        String entity = client.target(new LoliBoxConfig().getUrl()).path("hello").request(MediaType.TEXT_PLAIN_TYPE)
            .get(String.class);
        Assert.assertEquals("hello", entity);
    }
}
