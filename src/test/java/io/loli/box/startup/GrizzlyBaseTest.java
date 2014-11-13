package io.loli.box.startup;

import io.loli.box.startup.LoliBoxConfig;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by 叶 on 2014/11/14.
 */
public class GrizzlyBaseTest {
    private static HttpServer server = null;

    @BeforeClass
    public static void beforeClass() throws IOException {
        final ResourceConfig rc = new ResourceConfig().packages("io.loli.box.controller");
        server = GrizzlyHttpServerFactory.createHttpServer(UriBuilder.fromUri(LoliBoxConfig.DEFAULT_URI).build(), rc);
        server.getServerConfiguration().addHttpHandler(
                new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/web/"), "/web");
        server.start();
    }

    @AfterClass
    public static void afterClass() {
        server.shutdownNow();
    }

    @Test
    public void testHello() {
        Client client = ClientBuilder.newClient();
        String entity = client.target(LoliBoxConfig.DEFAULT_URI)
                .path("hello")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);
        Assert.assertEquals("hello", entity);
    }
}

