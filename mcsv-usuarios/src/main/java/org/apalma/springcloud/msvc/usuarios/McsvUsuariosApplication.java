package org.apalma.springcloud.msvc.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class McsvUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(McsvUsuariosApplication.class, args);
    }

}
