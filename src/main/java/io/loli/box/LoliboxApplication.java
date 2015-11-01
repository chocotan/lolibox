package io.loli.box;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MvcConfig.class, SecurityConfig.class})
public class LoliboxApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoliboxApplication.class, args);
    }

}
