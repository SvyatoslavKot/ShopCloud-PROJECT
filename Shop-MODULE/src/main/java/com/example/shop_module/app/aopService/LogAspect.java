package com.example.shop_module.app.aopService;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;


import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(* com.example.shop_module.service..*.*(..))")
        private void anyMethod(){
            System.out.println();
    }

    @Before("anyMethod()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("BEFORE LOG => " +joinPoint.toString());
        System.out.println("BEFORE ARGS LOG => " + joinPoint.getArgs());
    }
    @AfterReturning("anyMethod()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("AFTER LOG => " +joinPoint.toString());
        System.out.println("AFTER ARGS LOG => " + joinPoint.getArgs());
    }
    @AfterThrowing("anyMethod()")
    public void logAfterThrow(JoinPoint joinPoint) {
        System.out.println("AFTER TROWING LOG => " + joinPoint.toString());

    }
    @Around("anyMethod()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("AROUND BEFORE LOG => " + System.currentTimeMillis());
        Object value;
        try{
            value = pjp.proceed();
        }catch (Throwable t){
            System.out.println("Log arround after with error -> " + System.currentTimeMillis());
            return  null;
        }
        System.out.println("AROUND AFTER LOG => " + System.currentTimeMillis());
        System.out.println("AROUND AFTER LOG => lead time: " );
        return value;
    }
}
