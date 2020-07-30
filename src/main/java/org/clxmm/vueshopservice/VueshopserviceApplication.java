package org.clxmm.vueshopservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("org.clxmm.vueshopservice.mapper")
public class VueshopserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueshopserviceApplication.class, args);
    }

}
