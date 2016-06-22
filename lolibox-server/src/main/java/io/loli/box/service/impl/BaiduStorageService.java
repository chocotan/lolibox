package io.loli.box.service.impl;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;
import io.loli.box.service.AbstractStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author choco
 */

@Component
@ConditionalOnProperty(name = "storage.type", havingValue = "baidu")
@ConfigurationProperties(prefix = "storage.baidu")
public class BaiduStorageService extends AbstractStorageService {
    private String url;
    private String key;
    private String secret;
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
