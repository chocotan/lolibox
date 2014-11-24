package io.loli.box.startup;

import java.io.IOException;
import java.net.URI;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.ws.rs.core.UriBuilder;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import org.apache.jasper.servlet.JspServlet;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
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

    private LoliBoxConfig config = LoliBoxConfig.getInstance();

    private String port = config.getPort();
    private String address = config.getAddress();

    private static final String JERSEY_SERVLET_CONTEXT_PATH = "";
    private static final String JSP_CLASSPATH_ATTRIBUTE = "org.apache.catalina.jsp_classpath";
    private String schema = "http";

    private HttpServer server = null;

    public LoliBoxServer() {
    }

    public LoliBoxServer(String[] args) {
        readParams(args);
    }

    private void readParams(String[] args) {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("").description("A simple image hosting software");

        parser.addArgument("-p", "--port").metavar("PORT").type(Integer.class).help("Http port to listen on")
            .dest("port");

        parser.addArgument("-a", "--address").metavar("ADDRESS").type(String.class).help("Addresses to listen on")
            .dest("address");

        parser.addArgument("-e", "--email").metavar("EMAIL").type(String.class)
            .help("Admin email to show on the bottom of page").dest("email");

        parser.addArgument("-s", "--save").metavar("SAVE").type(String.class).help("Where images saved").dest("save");

        try {
            Namespace res = parser.parseArgs(args);
            Integer cport = res.getInt("port");
            if (cport != null) {
                port = String.valueOf(cport);
            }

            String caddress = res.getString("address");
            if (caddress != null) {
                address = String.valueOf(address);
            }

            String cemail = res.getString("email");
            if (cemail != null) {
                String email = String.valueOf(cemail);
                LoliBoxConfig.getInstance().setEmail(email);
            }

            String cpath = res.getString("save");
            if (cpath != null) {
                String savePath = String.valueOf(cpath);
                LoliBoxConfig.getInstance().setSavePath(savePath);
            }

        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }

    }

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
        server.getServerConfiguration().addHttpHandler(new LoliBoxStaticHttpHandler(config.getSavePath()), "/images");

        WebappContext context = new WebappContext("WebappContext", JERSEY_SERVLET_CONTEXT_PATH);

        // Initialize and register Jersey Servlet
        FilterRegistration registration = context.addFilter("ServletContainer", ServletContainer.class);
        registration.setInitParameter(JspMvcFeature.TEMPLATES_BASE_PATH, "/WEB-INF/jsp");
        // configure Jersey to bypass non-Jersey requests (static resources and
        // jsps)

        registration.setInitParameter("javax.ws.rs.Application", LoliBoxAppConfig.class.getName());
        registration.setInitParameter(ServletProperties.FILTER_STATIC_CONTENT_REGEX,
            "(/.*\\.(js|css))|(/.*\\.jsp)|(/WEB-INF/.*\\.jsp)|" + "(/WEB-INF/.*\\.jspf)|(/.*\\.html)|(/favicon\\.ico)|"
                + "(/robots\\.txt)");

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
        LoliBoxServer server = new LoliBoxServer(args);
        if (args.length == 1 && (("-h").equals(args[0]) || ("--help").equals(args[0]))) {
            return;
        }
        server.startAndWait();
    }

    public void stop() {
        server.shutdownNow();
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri(schema + "://" + address + "/").port(Integer.parseInt(port)).build();
    }
}