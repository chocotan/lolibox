package io.loli.box.green;

import io.loli.box.dao.ImgFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by chocotan on 2016/8/6.
 */
@Configuration
@ConditionalOnProperty(name = "admin.green.enabled", havingValue = "aliyun")
public class AliGreenConfiguration {

    @Bean
    public GreenService greenService() {
        return new AliGreenService();
    }

    @Autowired
    ImgFileRepository imgFileRepository;

    @Bean
    public Object aliGreenJob(GreenService greenService) {
        return new Object() {
            @Scheduled(fixedRate = 60000)
            public void submitTask() {
                LocalDateTime time = LocalDateTime.now();
                Date end = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
                time.minusDays(1);
                Date start = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
                imgFileRepository.findByGreenStatus(0, start, end).forEach(img -> {
                    try {

                        String taskId = greenService.asyncDetect("/images/" + img.getShortName());
                        imgFileRepository.updateTaskIdById(taskId, img.getId());
                        imgFileRepository.updateGreenStatusById(1, img.getId());
                    } catch (Throwable throwable) {
                        imgFileRepository.updateGreenStatusById(2, img.getId());
                    }
                });
            }

            @Scheduled(fixedRate = 120000)
            public void getResult() {
                LocalDateTime time = LocalDateTime.now();
                Date end = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
                time.minusDays(1);
                time.minusMinutes(60);
                Date start = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
                imgFileRepository.findByGreenStatus(1, start, end).forEach(img -> {
                    try {
                        float checkResult = greenService.getCheckResult(img.getGreenTaskId());
                        boolean porn = checkResult > 90;
                        imgFileRepository.updateGreenStatusById(porn ? 3 : 5, img.getId());
                        imgFileRepository.updateGreenPointById(checkResult, img.getId());
                    } catch (Throwable throwable) {
                        imgFileRepository.updateGreenStatusById(4, img.getId());
                    }
                });
            }
        };
    }
}
