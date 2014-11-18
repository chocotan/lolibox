package io.loli.box.startup;

import java.io.IOException;

import javax.json.stream.JsonGenerator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Created by 叶 on 2014/11/14.
 */
public class GrizzlyBaseTest {
    private static LoliBoxServer server = null;

    protected Client client = null;

    @BeforeClass
    public static void beforeClass() throws IOException {
        server = new LoliBoxServer();
        server.start();
    }

    @AfterClass
    public static void afterClass() {
        server.stop();
    }

    @Before
    public void before() {
        client = ClientBuilder.newBuilder().register(MultiPartFeature.class).register(JsonProcessingFeature.class)
            .property(JsonGenerator.PRETTY_PRINTING, true).build();
    }


}
