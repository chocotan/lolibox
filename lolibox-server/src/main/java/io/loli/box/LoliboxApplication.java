package io.loli.box;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
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

    @Value("${storage.type}")
    private String storageType;


    @Bean
    public Hashids hashids() {
        Hashids hashids = new Hashids(LoliboxApplication.class
                .toString());
        return hashids;
    }
}
