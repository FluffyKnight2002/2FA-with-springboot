package org.authentication.two_factor_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TwoFactorAuthApplication {

    public static void main(String[] args) {

        SpringApplication.run(TwoFactorAuthApplication.class, args);

    }


}
