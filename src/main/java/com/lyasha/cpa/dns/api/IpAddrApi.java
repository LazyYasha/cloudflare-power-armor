package com.lyasha.cpa.dns.api;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@RequiredArgsConstructor
public class IpAddrApi {

    private static final OkHttpClient ipClient = new OkHttpClient();
    private static final String CLOUDFLARE_PROVIDER = "Cloudflare";
    private static final String IP3322_PROVIDER = "3322";

    private final String ipProvider;

    public String getPublicIpv4Addr() {
        return switch (ipProvider) {
            case IP3322_PROVIDER -> getIpv4AddrBy3322API();
            case CLOUDFLARE_PROVIDER -> getIpv4AddrByCloudflareTraceAPI();
            default -> getIpv4AddrByCloudflareTraceAPI();
        };
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

    protected static String getIpv4AddrBy3322API() {
        var newRequest = new Request.Builder()
                .url("https://ip.3322.net/")
                .build();
        try (var response = ipClient.newCall(newRequest).execute()) {
            var body = response.body();
            if (body == null) {
                return null;
            }
            return body.string();
        } catch (Exception e) {
            return null;
        }
    }
}
