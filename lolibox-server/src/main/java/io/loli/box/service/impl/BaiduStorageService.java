package io.loli.box.service.impl;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;
import io.loli.box.service.AbstractStorageService;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author choco
 */
public class BaiduStorageService extends AbstractStorageService {
    @Value("${storage.baidu.url}")
    private String url;
    @Value("${storage.baidu.key}")
    private String key;
    @Value("${storage.baidu.secret}")
    private String secret;
    @Value("${storage.baidu.name}")
    private String name;

    private BosClient client;

    @PostConstruct
    public void init() {
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(key, secret));
        config.setEndpoint(url);
        client = new BosClient(config);

    }

    @Override
    public String upload(InputStream is, String filename, String contentType, long length) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(length);
        meta.setContentType(contentType);
        PutObjectResponse putObjectResponseFromInputStream = client.putObject(name, "images/" + filename, is, meta);
        return filename;
    }

    public void deleteFile(String fileName) {
        imgFileRepository.updateDeleteByShortName(fileName, true);
        client.deleteObject(name, "images/" + fileName);
    }

}
