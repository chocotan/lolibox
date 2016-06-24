package io.loli.box;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.social.config.annotation.EnableSocial;

@SpringBootApplication
@EnableZuulProxy
@EnableJpaRepositories
@Import({MvcConfig.class, SecurityConfig.class})
@EnableSocial
@EntityScan
public class LoliboxApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoliboxApplication.class, args);
    }

    @Bean
    @Primary
    public Hashids hashids() {
        Hashids hashids = new Hashids(LoliboxApplication.class
                .toString());
        return hashids;
    }

    @Bean
    @ConditionalOnProperty(name = "register.invitation.seed")
    public Hashids invitationCodeHashIds(@Value("${register.invitation.seed}") String invitationSeed) {
        Hashids hashids = new Hashids(invitationSeed, 12);
        return hashids;
    }
}
