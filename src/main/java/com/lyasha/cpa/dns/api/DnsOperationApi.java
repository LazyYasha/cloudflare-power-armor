package com.lyasha.cpa.dns.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyasha.cpa.base.CloudflareApiResponse;
import com.lyasha.cpa.base.CloudflareHttpApi;
import com.lyasha.cpa.dns.domain.DnsRecord;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

@RequiredArgsConstructor
public class DnsOperationApi {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(SNAKE_CASE)
            .disable(FAIL_ON_UNKNOWN_PROPERTIES);

    private final CloudflareHttpApi httpApi;

    public List<DnsRecord> getDnsRecords(String zoneId) throws JsonProcessingException {
        String url = "/zones/" + zoneId + "/dns_records";
        return objectMapper.readValue(httpApi.get(url), new TypeReference<CloudflareApiResponse<List<DnsRecord>>>() {
        }).getResult();
    }

    public DnsRecord createDnsRecord(DnsRecord dnsRecord) throws JsonProcessingException {
        String url = "/zones/" + dnsRecord.getZoneId() + "/dns_records";
        return objectMapper.readValue(httpApi.post(url,
                objectMapper.writeValueAsString(dnsRecord)), new TypeReference<CloudflareApiResponse<DnsRecord>>() {
        }).getResult();
    }

    public void updateDnsRecord(DnsRecord dnsRecord, String content) throws JsonProcessingException {
        String url = "/zones/" + dnsRecord.getZoneId() + "/dns_records/" + dnsRecord.getId();
        dnsRecord.setContent(content);
        httpApi.put(url, objectMapper.writeValueAsString(dnsRecord));
    }

    public void deleteDnsRecord(String zoneId, String dnsRecordId) {
        String url = "/zones/" + zoneId + "/dns_records/" + dnsRecordId;
        httpApi.delete(url);
    }
}
