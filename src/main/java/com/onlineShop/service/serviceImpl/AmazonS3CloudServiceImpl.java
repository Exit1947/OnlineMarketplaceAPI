package com.onlineShop.service.serviceImpl;

import com.onlineShop.security.S3Properties;
import com.onlineShop.service.AmazonS3CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AmazonS3CloudServiceImpl implements AmazonS3CloudService {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    @Autowired
    public AmazonS3CloudServiceImpl(final S3Client s3Client, final S3Properties s3Properties) throws NullPointerException {
        this.s3Client = s3Client;
        this.s3Properties = s3Properties;
        if(Objects.nonNull(this.s3Properties.getBucketName()) && !this.s3Properties.getBucketName().isEmpty()) {
            if(!isBucketExist()) {
                createBucket();
            }
        }
        else{
            throw new NullPointerException("Set value for bucketName");
        }
    }

    @Override
    public void store(String fileName, byte[] uploadingFile) {
        PutObjectRequest request = PutObjectRequest
                .builder()
                .bucket(s3Properties.getBucketName())
                .key(fileName)
                .build();
        RequestBody requestBody = RequestBody.fromBytes(uploadingFile);
        s3Client.putObject(request, requestBody);
    }

    public void store(Map<String, byte[]> uploadingFiles) {
        for(Map.Entry<String, byte[]> entry : uploadingFiles.entrySet()) {
            store(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String get(String fileName) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(fileName)
                    .build();

            s3Client.headObject(headObjectRequest);

            return s3Properties.getImgGatewayUrl() + fileName;
        } catch (S3Exception e) {
            return null;
        }
    }

    @Override
    public void rename(String oldName, String newName) {
        s3Client.copyObject(request ->
                request
                        .sourceBucket(s3Properties.getBucketName())
                        .sourceKey(oldName)
                        .destinationBucket(s3Properties.getBucketName())
                        .destinationKey(newName));
    }

    @Override
    public void delete(String fileName) {
        s3Client.deleteObject(request -> request.bucket(s3Properties.getBucketName()).key(fileName));
    }

    private boolean isBucketExist() {
        try {
            s3Client.headBucket(request -> request.bucket(s3Properties.getBucketName()));
            return true;
        }
        catch(S3Exception e) {
            return false;
        }
    }

    private void createBucket() {
        s3Client.createBucket(request -> request.bucket(s3Properties.getBucketName()));
    }

}
