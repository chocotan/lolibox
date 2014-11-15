package io.loli.box.startup;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by 叶 on 2014/11/12.
 */
public class LoliBoxServer {
    private LoliBoxConfig config = new LoliBoxConfig();

    private String port = config.getPort();
    private String address = config.getAddress();

    private static String packages = "io.loli.box.controller";
    private String schema = "http";

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
    }

    public void startAndWait() throws IOException {
        start();
        System.in.read();
    }

    public static void main(String[] args) throws IOException {
        LoliBoxServer server = new LoliBoxServer();
        server.startAndWait();
    }

    public void stop() {
        server.shutdownNow();
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri(schema + "://" + address + "/").port(Integer.parseInt(port)).build();
    }
}
