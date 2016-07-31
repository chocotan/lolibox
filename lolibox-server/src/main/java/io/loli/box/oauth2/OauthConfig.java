package io.loli.box.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @author choco
 */
@Configuration
public class OauthConfig {

    @Bean
    public TokenStore tokenStore(DataSource ds){
        return new JdbcTokenStore(ds);
    }

}
