package com.qingyang.test;

import com.qingyang.test.annotation.SqlHint;

import java.util.List;

/**
 * @author qingyang
 * @date 2018/11/15.
 */
public interface CarMakeMapper {


    @SqlHint
    List<CarMakeEntity> select(CarMakeEntity cm);
}
