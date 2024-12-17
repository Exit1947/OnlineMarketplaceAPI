package com.onlineShop.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface AmazonS3CloudService {

    void store(String fileName, byte[] uploadingFile);

    void store(Map<String, byte[]> uploadingFiles);

    String get(String fileName);

    void delete(String fileName);

    void rename(String oldName, String newName);

}
