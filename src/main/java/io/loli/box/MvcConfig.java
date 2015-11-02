package io.loli.box;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Value("${storage.filesystem.imgFolder}")
    private String imgFolder;

    @Value("${storage.type}")
    private String storageType;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if ("filesystem".equals(storageType))
            registry.addResourceHandler("/images/**").addResourceLocations(
                    "file://" + imgFolder);
    }
}