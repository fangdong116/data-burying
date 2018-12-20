package com.qingyang.test;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qingyang
 * @date 2018/11/15.
 */
@RestController("/test")
public class TestController {

    @Resource
    TestService testService;

    @RequestMapping(value =  "/hello")
    public String helloWorld(){
        return "helloWorld";
    }

    @RequestMapping(value =  "/select")
    public String select(){
        List<CarMakeEntity> s= testService.select();
        return JSON.toJSONString(s);
    }
}
