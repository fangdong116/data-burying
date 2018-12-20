package com.qingyang.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qingyang
 * @date 2018/11/15.
 */
@Service
public class TestService {

    @Autowired
    CarMakeMapper carMakeMapper;


    public List<CarMakeEntity> select() {
        CarMakeEntity cm = new CarMakeEntity();
        cm.setMakeId(1);

        return carMakeMapper.select(cm);


    }
}
