package io.loli.box.startup;

import javax.json.stream.JsonGenerator;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.servlet.ServletProperties;

/**
 * AppConfig for jersey
 * 
 * @author choco
 *
 */
public class LoliBoxAppConfig extends ResourceConfig {

    private static String packages = "io.loli.box.controller";

    public LoliBoxAppConfig() {
        this.packages(packages).register(MultiPartFeature.class).register(JsonProcessingFeature.class)
            .property(JsonGenerator.PRETTY_PRINTING, true).register(JacksonFeature.class)
            .property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/static/.*(js|css|swf|ico|png)(\\?.*)*")
            .property(ServerProperties.TRACING, TracingConfig.ALL.name()).register(LoggingFilter.class);

    }
}
