package com.example.shop_module.app.aopService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MessureAspect {

    @Pointcut("@annotation(com.example.shop_module.app.aopService.MeasureMethod)")
    private void annotationMethod(){

    }

    @Around(("annotationMethod()"))
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        long timeBefore = System.currentTimeMillis();
        Object value = pjp.proceed();
        long timeAfter = System.currentTimeMillis();
        System.out.println("LOG MEASURE = >" + pjp.toLongString() + " time millis " + (timeAfter - timeBefore));
        return value;
    }
}
