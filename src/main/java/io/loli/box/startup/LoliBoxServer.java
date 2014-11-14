package io.loli.box.startup;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * Created by 叶 on 2014/11/12.
 */
public class LoliBoxServer {

    private String port = LoliBoxConfig.DEFAULT_PORT;
    private String address = LoliBoxConfig.DEFAULT_ADDRESS;
    private static String packages = "io.loli.box.controller";
    private String schema = "http";
    private volatile boolean stop = false;

    private HttpServer server = null;

    public LoliBoxServer address(String address) {
        this.address = address;
        return this;
    }

    public LoliBoxServer port(String port) {
        this.port = port;
        return this;
    }

    public void start() throws IOException {
        final ResourceConfig rc = new ResourceConfig().packages(packages);
        server = GrizzlyHttpServerFactory.createHttpServer(getBaseURI(), rc);
        server.getServerConfiguration().addHttpHandler(
                new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/web/"), "/web");
        server.start();
        System.in.read();
    }

    public void stop() {
        server.shutdownNow();
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri(schema + "://" + address + "/").port(Integer.parseInt(port)).build();
    }
}
