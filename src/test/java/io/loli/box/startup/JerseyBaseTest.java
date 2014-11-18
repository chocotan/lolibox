package io.loli.box.startup;

import javax.json.stream.JsonGenerator;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 叶 on 2014/11/13.
 */
public class JerseyBaseTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig().packages("io.loli.box.controller").register(MultiPartFeature.class)
            .register(JsonProcessingFeature.class).property(JsonGenerator.PRETTY_PRINTING, true);
    }

    @Test
    public void test() {
        final String hello = target("hello").request().get(String.class);
        Assert.assertEquals("hello", hello);
    }

}
