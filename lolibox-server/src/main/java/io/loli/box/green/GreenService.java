package io.loli.box.green;

/**
 * Created by chocotan on 2016/8/6.
 */
public interface GreenService {
    String asyncDetect(String url);

    float getCheckResult(String taskId);
}
