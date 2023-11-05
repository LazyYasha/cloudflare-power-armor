package com.lyasha.cpa.dns.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lyasha.cpa.dns.api.DnsOperationApi;
import com.lyasha.cpa.dns.api.IpAddrApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CloudflareDnsService {

    private final DnsOperationApi dnsOperationApi;
    private final IpAddrApi ipAddrApi;

    public void updateDnsIp(String zoneId, String type, String name) throws JsonProcessingException {
        var dnsRecords = dnsOperationApi.getDnsRecords(zoneId);
        var currentIp = ipAddrApi.getPublicIpv4Addr();
        dnsRecords.stream()
                .filter(r -> r.getType().equals(type) && r.getName().equals(name))
                .findAny()
                .ifPresent(r -> {
                    log.debug("Current IP address: {}, DNS record IP address: {}", r.getContent(), r.getContent());
                    if (!r.getContent().equals(currentIp)) {
                        try {
                            dnsOperationApi.updateDnsRecord(r, currentIp);
                            log.info("Updated domain {} to new IP address {}", r.getName(), currentIp);
                        } catch (JsonProcessingException e) {
                            log.error("Failed to update domain {} to new IP address {}", r.getName(), currentIp);
                        }
                    }
                });
    }

}
