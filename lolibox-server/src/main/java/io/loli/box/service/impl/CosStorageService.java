package io.loli.box.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import io.loli.box.service.AbstractStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯云cos存储服务
 */
@Component
@ConditionalOnProperty(name = "storage.type", havingValue = "cos")
@ConfigurationProperties(prefix = "storage.cos")
public class CosStorageService extends AbstractStorageService {

    private String url;
    private String key;
    private String secret;
    private String name;
    private String region;
    private COSClient client;

    @PostConstruct
    public void init() {
        COSCredentials cred = new BasicCOSCredentials(key, secret);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        client = new COSClient(cred, clientConfig);
    }


    @Override
    public String upload(InputStream is, String filename, String contentType, long length) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(length);
        metadata.setContentType(contentType);
        client.putObject(name, "images/" + filename, is, metadata);
        return filename;
    }


    public void deleteFile(String fileName) {
        super.deleteFile(fileName);
        client.deleteObject(name, "images/" + key);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public COSClient getClient() {
        return client;
    }

    public void setClient(COSClient client) {
        this.client = client;
    }
}
