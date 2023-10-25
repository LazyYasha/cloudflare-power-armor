package com.lyasha.cpa.base;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class CloudflareHttpApi {

    private static final String BASE_URL = "https://api.cloudflare.com/client/v4";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient httpClient;

    public CloudflareHttpApi(CloudflareApiConfig config) {
        this.httpClient = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    if (config.getApiToken() != null) {
                        var request = chain.request().newBuilder()
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + config.getApiToken())
                                .build();
                        return chain.proceed(request);
                    }
                    var request = chain.request().newBuilder()
                            .header("Content-Type", "application/json")
                            .header("X-Auth-Email", config.getEmail())
                            .header("X-Auth-Key", config.getApiKey())
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    public String get(String url) {
        var request = new Request.Builder()
                .url(BASE_URL + url)
                .get()
                .build();
        return handleRequest(request);
    }

    public String post(String url, String body) {
        var request = new Request.Builder()
                .url(BASE_URL + url)
                .post(okhttp3.RequestBody.create(body, JSON))
                .build();
        return handleRequest(request);
    }

    public String put(String url, String body) {
        var request = new Request.Builder()
                .url(BASE_URL + url)
                .put(okhttp3.RequestBody.create(body, JSON))
                .build();
        return handleRequest(request);
    }

    public String patch(String url, String body) {
        var request = new Request.Builder()
                .url(BASE_URL + url)
                .patch(okhttp3.RequestBody.create(body, JSON))
                .build();
        return handleRequest(request);
    }

    public String delete(String url) {
        var request = new Request.Builder()
                .url(BASE_URL + url)
                .delete()
                .build();
        return handleRequest(request);
    }

    private String handleRequest(Request request) {
        log.debug("Calling Cloudflare API: {}", request.url());
        try (var response = httpClient.newCall(request).execute()) {
            log.debug("Response code: {}", response.code());
            if (log.isDebugEnabled() && Objects.nonNull(response.body())) {
                log.debug("Response body: {}", response.body().string());
            }
            if (!response.isSuccessful()) {
                var errMsg = String.format("Call Cloudflare API %s failed. Response code: %s",
                        request.url(), response.code());
                throw new CloudflareApiException(errMsg);
            }
            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            var errMsg = String.format("Error calling Cloudflare API %s.", request.url());
            throw new CloudflareApiException(errMsg, e);
        }
    }
}
