package com.lyasha.cpa.dns.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CloudflareDnsProperties {

    private List<DDnsTarget> DDnsTargets;

    @Data
    @AllArgsConstructor
    public static class DDnsTarget {
        private String zoneId;
        private String type;
        private String name;
        private boolean proxied;
    }
}
