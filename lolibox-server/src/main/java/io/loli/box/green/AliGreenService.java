package io.loli.box.green;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.green.model.v20160621.ImageDetectionRequest;
import com.aliyuncs.green.model.v20160621.ImageDetectionResponse;
import com.aliyuncs.green.model.v20160621.ImageResultRequest;
import com.aliyuncs.green.model.v20160621.ImageResultResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chocotan on 2016/8/6.
 */
@ConfigurationProperties(prefix = "admin.green.aliyun")
public class AliGreenService implements GreenService {
    private String key;
    private String secret;
    private String greenUrlPrefix;


    private static final Logger logger = LoggerFactory.getLogger(AliGreenService.class);

    IClientProfile profile;
    IAcsClient client;

    @PostConstruct
    public void init() {
        profile = DefaultProfile.getProfile("cn-qingdao", key, secret);
        client = new DefaultAcsClient(profile);
    }

    @Override
    public String asyncDetect(String url) {
        url = greenUrlPrefix + url;
        //请替换成你自己的accessKeyId、secret
        ImageDetectionRequest imageDetectionRequest = new ImageDetectionRequest();
        imageDetectionRequest.setAsync(true);
        imageDetectionRequest.setScenes(Arrays.asList("porn"));
        imageDetectionRequest.setImageUrls(Arrays.asList(url));
        try {
            ImageDetectionResponse imageDetectionResponse = client.getAcsResponse(imageDetectionRequest);
            logger.info("Detecting image... {}^^^^{}^^^^{}", url,imageDetectionResponse.getCode(), imageDetectionResponse.getMsg());
            if ("Success".equals(imageDetectionResponse.getCode())) {
                List<ImageDetectionResponse.ImageResult> imageResults = imageDetectionResponse.getImageResults();
                if (imageResults != null && imageResults.size() > 0) {
                    for (ImageDetectionResponse.ImageResult imageResult : imageResults) {
                        String taskId = imageResult.getTaskId();
                        return taskId;
                    }
                }
            } else {
                logger.error("Ali green detect error, reason: {}", imageDetectionResponse.getMsg());
            }
        } catch (Throwable e) {
            logger.error("Ali green detect error, reason: {}", ExceptionUtils.getStackTrace(e));

        }
        throw new RuntimeException("Ali green detect error");
    }

    @Override
    public float getCheckResult(String taskId) {
        ImageResultRequest imageResultRequest = new ImageResultRequest();
        imageResultRequest.setTaskId(taskId);
        try {
            ImageResultResponse imageResultResponse = client.getAcsResponse(imageResultRequest);
            logger.info("Getting image detection result... {}^^^^{}", imageResultResponse.getCode(), imageResultResponse.getMsg());
            if ("Success".equals(imageResultResponse.getCode())
                    && "TaskProcessSuccess".equals(imageResultResponse.getStatus())) {
                ImageResultResponse.ImageResult imageResult = imageResultResponse.getImageResult();
                ImageResultResponse.ImageResult.PornResult pornResult = imageResult.getPornResult();
                if (pornResult != null) {
                    Integer label = pornResult.getLabel();
                    Float rate = pornResult.getRate();
                    return rate;
                }
                imageResult.getOcrResult();
                imageResult.getIllegalResult();
            } else {
                logger.error("Failed to get image check result");
            }
        } catch (ClientException e) {
            logger.error("Failed to get image check result");
            throw new RuntimeException(e);
        }
        logger.error("Failed to get image check result");
        throw new RuntimeException("Failed to get image check result");
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

    public String getGreenUrlPrefix() {
        return greenUrlPrefix;
    }

    public void setGreenUrlPrefix(String greenUrlPrefix) {
        this.greenUrlPrefix = greenUrlPrefix;
    }
}
