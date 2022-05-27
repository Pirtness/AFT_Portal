package core;

import dataBase.HealthCheckDataBaseApp;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(HealthCheckDataBaseApp.class)
@SpringBootApplication
public class HealthCheckCoreApp {
}
