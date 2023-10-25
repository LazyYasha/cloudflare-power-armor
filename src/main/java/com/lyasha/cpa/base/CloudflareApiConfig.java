package com.lyasha.cpa.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CloudflareApiConfig {

    private String email;
    private String apiKey;
    private String apiToken;
}