package com.lyasha.cpa.dns.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class IpAddrApi {

    private static final OkHttpClient ipClient = new OkHttpClient();

    public String getPublicIpv4Addr() {
        return getIpv4AddrByCloudflareTraceAPI();
    }

    protected static String getIpv4AddrByCloudflareTraceAPI() {
        var newRequest = new Request.Builder()
                .url("https://1.1.1.1/cdn-cgi/trace")
                .build();
        try (var response = ipClient.newCall(newRequest).execute()) {
            var body = response.body();
            if (body == null) {
                return null;
            }
            var lines = body.string().split("\n");
            for (var line : lines) {
                if (line.startsWith("ip=")) {
                    return line.substring(3);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
