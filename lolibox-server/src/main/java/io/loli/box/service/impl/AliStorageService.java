package io.loli.box.service.impl;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import io.loli.box.service.AbstractStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author choco
 */
@Component
@ConditionalOnProperty(name = "storage.type", havingValue = "aliyun")
@ConfigurationProperties(prefix = "storage.aliyun")
public class AliStorageService extends AbstractStorageService {

    private String url;
    private String key;
    private String secret;
    private String name;

    private OSSClient client = null;

    @PostConstruct
    public void init() {
        client = new OSSClient(url, key,
                secret);
    }

    @Override
    public String upload(InputStream is, String filename, String contentType, long length) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(length);
        meta.setContentType(contentType);
        meta.setCacheControl("max-age=" + 3600 * 1000 * 48);
        meta.setLastModified(new Date());
        client.putObject(name, "images/" + filename, is, meta);
        return filename;
    }

    @Override
    public void deleteFile(String fileName) {
        super.deleteFile(fileName);
        client.deleteObject(name, "images/" + fileName);
    }
}
