package com.qingyang.test;

/**
 * @author qingyang
 * @date 2018/4/11.
 */
public class MyObj {
    private Integer id;
    private String name;

    public MyObj() {
    }

    public MyObj(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
