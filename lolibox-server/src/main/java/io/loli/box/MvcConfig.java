package io.loli.box;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnProperty(name = "storage.type", havingValue = "filesystem")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Value(value = "${storage.filesystem.imgFolder}")
    private String imgFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 图片缓存 7 天
        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                "file:///" + imgFolder.replace("\\", "/"))
                .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS));
    }
}
