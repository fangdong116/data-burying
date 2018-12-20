package com.qingyang.test.aspect;

import com.qingyang.test.annotation.SqlHint;
import io.shardingsphere.api.HintManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author qingyang
 * @date 2018/11/16.
 */
@Aspect
@Component
public class HintAspect {


    @Before("@annotation(com.qingyang.test.annotation.SqlHint) && @annotation(sqlHint))")
    public void doBefore(SqlHint sqlHint){
        boolean routeMaster = sqlHint.routeMaster();
        if(routeMaster){
            HintManager.getInstance().setMasterRouteOnly();
        }
    }

    @After("@annotation(com.qingyang.test.annotation.SqlHint) && @annotation(sqlHint))")
    public void doReleaseLock(SqlHint sqlHint) {
        HintManager.getInstance().close();
    }
}
