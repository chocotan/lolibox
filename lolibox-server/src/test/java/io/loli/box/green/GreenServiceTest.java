package io.loli.box.green;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by chocotan on 2016/8/6.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GreenServiceTest {

    @Autowired
    private GreenService greenService;

    @Test
    public void testAliGreenService(){
//        String taskId = greenService.asyncDetect("http://b1.loli.io/images/qz4A.png");
        System.out.println(greenService.getCheckResult("1bd6da86-ee75-4a3e-9547-e61dd3f340d9-1470923891267"));
    }

}
