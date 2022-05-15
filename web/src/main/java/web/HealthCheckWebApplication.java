package web;


import core.HealthCheckCoreApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(HealthCheckCoreApp.class)
@SpringBootApplication(scanBasePackages = "web")
public class HealthCheckWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthCheckWebApplication.class, args);
    }
}
