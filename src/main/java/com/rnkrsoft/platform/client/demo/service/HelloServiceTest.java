package com.rnkrsoft.platform.client.demo.service;

import com.rnkrsoft.platform.client.async.AsyncTask;
import com.rnkrsoft.platform.client.demo.domain.HelloRequest;
import com.rnkrsoft.platform.client.demo.domain.HelloResponse;
import com.rnkrsoft.platform.protocol.AsyncHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 * Created by rnkrsoft.com on 2019/1/24.
 */

@ContextConfiguration("classpath*:applicationContext-platform.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public class HelloServiceTest {

    @Autowired
    HelloService helloService;
    @Test
    public void testHelloSync() throws Exception {
        HelloRequest request = new HelloRequest();
        request.setName("test sync");
        HelloResponse response = helloService.hello(request);
        System.out.println(response);
//        Thread.sleep(60 * 1000);
    }

    @Test
    public void testHelloAsync() throws Exception {
        HelloRequest request = new HelloRequest();
        request.setName("test async");
        AsyncTask asyncTask = helloService.hello(request, new AsyncHandler<HelloResponse>() {
            @Override
            public void fail(String code, String desc, String detail) {
                System.out.println("--------------->" + desc);
            }

            @Override
            public void success(HelloResponse response) {
                System.out.println("--------------->" + response);
            }
        });
        asyncTask.get();
    }
}