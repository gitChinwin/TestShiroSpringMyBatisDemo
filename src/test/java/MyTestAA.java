import cn.chinwin.demo.service.ITestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Chinwin on 2017/9/6.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-base.xml","/spring-db.xml"})
public class MyTestAA {


    @Resource(name = "testServiceImpl")
    private ITestService testService;


    @Test
    public void test11() {
        System.out.println("线程1.。。。");
        testService.test1();
        System.out.println("end...");
        System.out.println("end...");

    }


    @Test
    public void test22() {
        System.out.println("线程2.。。。");
        testService.test2();
        System.out.println("end...");
        System.out.println("end...");

    }



}
