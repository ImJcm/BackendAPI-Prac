package com.example.backendapiprac.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j(topic = "UseTimeAop")
@Aspect
@Component
public class UseTimeAop {

    @Pointcut("execution(* com.example.backendapiprac.controller.UserController.*(..))")
    private void user() {}

    @Pointcut("execution(* com.example.backendapiprac.controller.PostController.*(..))")
    private void post() {}

    @Pointcut("execution(* com.example.backendapiprac.controller.CommentController.*(..))")
    private void comment() {}

    @Around("user() || post() || comment()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            log.info("startTime:", startTime);
            Object output = joinPoint.proceed();
            return output;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("endTime:", startTime);

            long runTime = endTime - startTime;
            log.info("runTime:", runTime);
        }
    }

}
