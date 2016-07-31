package io.loli.box.oauth2;

import io.loli.box.util.FinalValueHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author choco
 */
@Configuration
@EnableAuthorizationServer
@Order(3)
@EntityScan("io.loli.box.oauth2")
@ConfigurationProperties(prefix = "oauth2")
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final Log logger = LogFactory
            .getLog(AuthorizationServerConfiguration.class);


    private List<OauthClient> clients;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired(required = false)
    private TokenStore tokenStore;

    @Autowired
    private DataSource ds;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // TODO Add JPA Builder
        JdbcClientDetailsServiceBuilder builder = clients
                .jdbc(ds);
        FinalValueHolder<ClientDetailsServiceBuilder> detailHolder = new FinalValueHolder<>(builder);
        this.clients.forEach(c -> detailHolder.setValue(detailHolder.getValue().withClient(c.getName()).secret(c.getSecret())
                .authorizedGrantTypes("password")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(Integer.MAX_VALUE).and()));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        if (this.tokenStore != null) {
            endpoints.tokenStore(this.tokenStore);
        }
        endpoints.authenticationManager(this.authenticationManager);
    }

    public static class OauthClient {
        private String name;
        private String secret;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public List<OauthClient> getClients() {
        return clients;
    }

    public void setClients(List<OauthClient> clients) {
        this.clients = clients;
    }
}
