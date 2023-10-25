package com.lyasha.cpa.dns.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyasha.cpa.base.CloudflareApiResponse;
import com.lyasha.cpa.base.CloudflareHttpApi;
import com.lyasha.cpa.dns.domain.DnsRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

@RequiredArgsConstructor
@Slf4j
public class CloudflareDnsService {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(SNAKE_CASE)
            .disable(FAIL_ON_UNKNOWN_PROPERTIES);
    private final CloudflareHttpApi httpApi;

    public List<DnsRecord> getDnsRecords(String zoneId) throws JsonProcessingException {
        String url = "/zones/" + zoneId + "/dns_records";
        return objectMapper.readValue(httpApi.get(url), new TypeReference<CloudflareApiResponse<List<DnsRecord>>>() {}).getResult();
    }

    public DnsRecord createDnsRecord(String zoneId, DnsRecord dnsRecord) throws JsonProcessingException {
        String url = "/zones/" + zoneId + "/dns_records";
        return objectMapper.readValue(httpApi.post(url, objectMapper.writeValueAsString(dnsRecord)), new TypeReference<CloudflareApiResponse<DnsRecord>>() {}).getResult();
    }

    public void updateDnsRecord(String zoneId, String dnsRecordId, String content, boolean proxied) throws JsonProcessingException {
        String url = "/zones/" + zoneId + "/dns_records/" + dnsRecordId;
        httpApi.put(url, objectMapper.writeValueAsString(new DnsRecord() {{
            setContent(content);
            setProxied(proxied);
        }}));
    }

    public void deleteDnsRecord(String zoneId, String dnsRecordId) {
        String url = "/zones/" + zoneId + "/dns_records/" + dnsRecordId;
        httpApi.delete(url);
    }
}
