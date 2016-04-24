package io.loli.box;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ReverseProxyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReverseProxyApplication.class, args);
    }
}
