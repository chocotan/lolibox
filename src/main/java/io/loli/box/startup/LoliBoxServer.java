package io.loli.box.startup;

import java.io.IOException;
import java.net.URI;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.ws.rs.core.UriBuilder;

import org.apache.jasper.servlet.JspServlet;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.servlet.FilterRegistration;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

/**
 * Created by 叶 on 2014/11/12.
 */
public class LoliBoxServer {
    private LoliBoxConfig config = new LoliBoxConfig();

    private String port = config.getPort();
    private String address = config.getAddress();

    private static final String JERSEY_SERVLET_CONTEXT_PATH = "";
    private static final String JSP_CLASSPATH_ATTRIBUTE = "org.apache.catalina.jsp_classpath";
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
        server = GrizzlyHttpServerFactory.createHttpServer(getBaseURI());
        // Add CLStaticHttpHandler to show html files
        server.getServerConfiguration().addHttpHandler(
            new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/static/"), "/static");
        // Add StaticHttpHandler to show imgs
        server.getServerConfiguration().addHttpHandler(new StaticHttpHandler(config.getSavePath()), "/show");

        WebappContext context = new WebappContext("WebappContext", JERSEY_SERVLET_CONTEXT_PATH);

        // Initialize and register Jersey Servlet
        FilterRegistration registration = context.addFilter("ServletContainer", ServletContainer.class);
        registration.setInitParameter(JspMvcFeature.TEMPLATES_BASE_PATH, "/WEB-INF/jsp");
        // configure Jersey to bypass non-Jersey requests (static resources and
        // jsps)

        registration.setInitParameter("javax.ws.rs.Application", LoliBoxAppConfig.class.getName());
        registration.setInitParameter(ServletProperties.FILTER_STATIC_CONTENT_REGEX,
            "(/.*\\.(js|css))|(/.*\\.jsp)|(/WEB-INF/.*\\.jsp)|"
                + "(/WEB-INF/.*\\.jspf)|(/.*\\.html)|(/favicon\\.ico)|" + "(/robots\\.txt)");

        registration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), "/*");

        // Initialize and register JSP Servlet
        ServletRegistration jspRegistration = context.addServlet("JSPContainer", JspServlet.class.getName());
        jspRegistration.addMapping("/*");

        // Set classpath for Jasper compiler based on the current classpath
        context.setAttribute(JSP_CLASSPATH_ATTRIBUTE, System.getProperty("java.class.path"));

        context.deploy(server);
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
