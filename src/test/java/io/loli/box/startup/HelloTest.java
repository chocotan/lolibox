package io.loli.box.startup;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 叶 on 2014/11/14.
 */
public class HelloTest extends JerseyBaseTest {

    @Test
    public void testHello() {
        String entity = target().path("hello").request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
        Assert.assertEquals("hello", entity);
    }
}
