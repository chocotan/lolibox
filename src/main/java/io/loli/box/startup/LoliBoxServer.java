package io.loli.box.startup;

import java.io.IOException;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Created by 叶 on 2014/11/12.
 */
public class LoliBoxServer {

    private LoliBoxConfig config = null;

    private static final String JERSEY_SERVLET_CONTEXT_PATH = "";

    private HttpServer server = null;

    public LoliBoxServer() {
    }

    public LoliBoxServer(String[] args) {
        config = LoliBoxConfig.getInstance(args);
    }

    public void start() throws IOException {
        server = new HttpServer();
        NetworkListener nl = new NetworkListener("Lolibox", config.getAddress(), Integer.parseInt(config.getPort()));
        server.addListener(nl);
        // Add CLStaticHttpHandler to show html files
        server.getServerConfiguration().addHttpHandler(
            new CLStaticHttpHandler(ClasspathHelper.class.getClassLoader(), "/static/"), "/static");
        // Add StaticHttpHandler to show imgs
        server.getServerConfiguration().addHttpHandler(new LoliBoxStaticHttpHandler(config.getSavePath()), "/images");

        WebappContext context = new WebappContext("WebappContext", JERSEY_SERVLET_CONTEXT_PATH);

        // Initialize and register Jersey Servlet
        ServletRegistration registration = context.addServlet("ServletContainer", ServletContainer.class);
        registration.addMapping("/*");

        registration.setInitParameter("javax.ws.rs.Application", LoliBoxAppConfig.class.getName());

        context.deploy(server);
        server.start();
    }

    public void startAndWait() throws IOException {
        start();
        System.in.read();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 1 && (("-h").equals(args[0]) || ("--help").equals(args[0]))) {
            return;
        }
        LoliBoxServer server = new LoliBoxServer(args);
        server.startAndWait();
    }

    public void stop() {
        server.shutdownNow();
    }

}