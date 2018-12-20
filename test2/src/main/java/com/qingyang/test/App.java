package com.qingyang.test;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.qingyang.test")
@PropertySource(value = "a.properties")
@MapperScan("com.qingyang.test")
public class App
{
    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main( String[] args )
    {
        for (int i = 0; i < 10; i++){
            new Thread(){
                @Override
                public void run() {
                    tl.set("test");
                }
            }.start();
        }
        while(true){}
    }

}
