package io.loli.box.service.impl;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import io.loli.box.service.AbstractStorageService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
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
@ConditionalOnProperty(name = "storage.type", havingValue = "qiniu")
@ConfigurationProperties(prefix = "storage.qiniu")
public class QiniuStorageService extends AbstractStorageService {


    private String url;
    private String key;
    private String secret;
    private String name;

    private Mac mac;

    @PostConstruct
    private void init() {
        mac = new Mac(key, secret);
    }

    @Override
    public String upload(InputStream is, String filename, String contentType, long length) throws IOException {
        PutPolicy putPolicy = new PutPolicy(name);
        String uptoken = null;
        try {
            uptoken = putPolicy.token(mac);
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PutExtra extra = new PutExtra();
        if (StringUtils.isNotBlank(contentType)) {
            extra.mimeType = contentType;
        }
        PutRet ret = IoApi.Put(uptoken, "images/" + filename, is, extra);
        return ret.getKey();
    }

    @Override
    public void deleteFile(String filename) {
        super.deleteFile(filename);
        RSClient client = new RSClient(mac);
        client.delete(name, "images/" + filename);
    }
}
