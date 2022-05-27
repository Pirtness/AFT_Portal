package web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "check-scheduler")
public class CheckSchedulerProperties {
    String serverName;
    String port;
    String runOnceApiUri;
    String runDemandChecksUri;
}
