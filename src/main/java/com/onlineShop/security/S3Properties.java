package com.onlineShop.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class S3Properties {

    @Value("${s3.access-key}")
    private String accessKey;

    @Value("${s3.secret-key}")
    private String secretKey;

    @Value("${s3.region-name}")
    private String regionName;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @Value("${s3.public-image-gateway-url}")
    private String imgGatewayUrl;

}
