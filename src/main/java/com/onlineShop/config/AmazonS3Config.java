package com.onlineShop.config;

import com.onlineShop.security.S3Properties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Getter
public class AmazonS3Config {

    @Bean
    @Autowired
    public S3Client s3Client(S3Properties s3Properties) {
        return S3Client.builder()
                .region(Region.of(s3Properties.getRegionName()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey())))
                .build();
    }

}
