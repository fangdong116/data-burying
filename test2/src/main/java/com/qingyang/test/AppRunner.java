package com.qingyang.test;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author qingyang
 * @date 2018/11/13.
 */
@Component
public class AppRunner implements ApplicationRunner{
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String a = "ab";
        String b = new String("ab");
        System.out.println(a == b);
    }
}
