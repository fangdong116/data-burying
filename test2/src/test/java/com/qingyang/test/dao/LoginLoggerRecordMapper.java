package com.qingyang.test.dao;

import java.util.Map;

/**
 * Created by qingyang on 2018/2/6.
 */
public interface LoginLoggerRecordMapper {
    Integer getLoginCount(Map<String, Object> param);
}
