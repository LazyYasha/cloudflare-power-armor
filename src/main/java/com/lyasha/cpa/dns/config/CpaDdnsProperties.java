package com.lyasha.cpa.dns.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "cpa.ddns")
@ConditionalOnProperty(name = "cpa.ddns.enabled", havingValue = "true")
public class CpaDdnsProperties {

    private boolean enabled;
    private String ipProvider;
    private List<DDnsTarget> targets;

    @Data
    public static class DDnsTarget {
        private String zoneId;
        private String type;
        private String name;
        private boolean proxied;
    }
}
