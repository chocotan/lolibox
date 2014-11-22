package io.loli.box.startup;

import javax.json.stream.JsonGenerator;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

/**
 * Created by 叶 on 2014/11/13.
 */
public class JerseyBaseTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig().packages("io.loli.box.controller").register(MultiPartFeature.class)
            .register(JsonProcessingFeature.class).property(JsonGenerator.PRETTY_PRINTING, true);
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(MultiPartFeature.class);
    }
}
