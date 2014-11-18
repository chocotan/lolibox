package io.loli.box.startup;

import java.io.IOException;
import java.net.URI;

import javax.json.stream.JsonGenerator;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
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
        rc.register(MultiPartFeature.class).register(JsonProcessingFeature.class)
            .property(JsonGenerator.PRETTY_PRINTING, true);
        server = GrizzlyHttpServerFactory.createHttpServer(getBaseURI(), rc);
        // Add CLStaticHttpHandler to show html files
        server.getServerConfiguration().addHttpHandler(
            new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/web/"), "/web");

        // Add StaticHttpHandler to show imgs
        server.getServerConfiguration().addHttpHandler(new StaticHttpHandler(config.getSavePath()), "/show");
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
