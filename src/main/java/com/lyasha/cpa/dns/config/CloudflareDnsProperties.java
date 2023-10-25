package com.lyasha.cpa.dns.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CloudflareDnsProperties {

    private List<DnsRecord> dnsRecords;

    @Data
    @AllArgsConstructor
    public static class DnsRecord {
        private String zoneId;
        private String type;
        private String name;
        private boolean proxied;
    }
}
