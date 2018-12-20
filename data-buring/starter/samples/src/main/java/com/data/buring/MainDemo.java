package com.data.buring;


import com.xlh.DataBuryingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Proxy;

/**
 * @author qingyang
 * @date 2018/12/19.
 */
@SpringBootApplication
public class MainDemo implements CommandLineRunner {

    @Value("${data.buring.exchange}")
    private String host;

    @Autowired
    DataBuryingTemplate dataBuryingTemplate;

    public static void main(String[] args){


        System.out.println(args instanceof Object);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println(host);

//        dataBuryingTemplate.sendMessage("hello");
    }
}
