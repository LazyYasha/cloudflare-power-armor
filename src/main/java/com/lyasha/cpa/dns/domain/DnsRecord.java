package com.lyasha.cpa.dns.domain;

import lombok.Data;

@Data
public class DnsRecord {

    private String id;
    private String zoneId;
    private String zoneName;
    private String name;
    private String type;
    private String content;
    private boolean proxiable;
    private boolean proxied;
    private int ttl;
    private boolean locked;
    private String comment;
    private String createdOn;
    private String modifiedOn;
}
