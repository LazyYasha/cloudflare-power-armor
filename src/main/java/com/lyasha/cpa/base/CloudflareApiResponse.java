package com.lyasha.cpa.base;

import lombok.Data;

@Data
public class CloudflareApiResponse<T> {

    private boolean success;
    private T result;
}
