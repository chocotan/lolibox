package io.loli.box.startup;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 叶 on 2014/11/14. Test if index html can be found by url
 */
public class IndexHtmlTest extends JerseyBaseTest {

    @Test
    public void testHtml() {
        Response response = target(new LoliBoxConfig().getUrl()).path("/").request(MediaType.TEXT_HTML).get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testHello() {
        String entity = target(new LoliBoxConfig().getUrl()).path("hello").request(MediaType.TEXT_PLAIN_TYPE)
            .get(String.class);
        Assert.assertEquals("hello", entity);
    }
}
