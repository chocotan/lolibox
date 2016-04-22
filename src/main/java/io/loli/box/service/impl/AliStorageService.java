package io.loli.box.service.impl;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import io.loli.box.service.AbstractStorageService;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author choco
 */
public class AliStorageService extends AbstractStorageService {

    @Value("${storage.aliyun.url}")
    private String url;
    @Value("${storage.aliyun.key}")
    private String key;
    @Value("${storage.aliyun.secret}")
    private String secret;
    @Value("${storage.aliyun.name}")
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
