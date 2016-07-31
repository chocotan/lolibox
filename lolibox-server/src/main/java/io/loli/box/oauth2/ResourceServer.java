package io.loli.box.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/image/oauthUpload")
                .authorizeRequests().anyRequest().access("#oauth2.hasScope('write')");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {

        resources.resourceId("lolibox");
    }

}