package com.deveradev.archeryanalyticsspring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("within(com.deveradev.archeryanalyticsspring.service.ArcheryRestService)")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("*** Calling method: " + methodName);
    }

//    @AfterReturning(pointcut = "execution(* com.example.service.ArcheryRestService+.findAllArchers(..))", returning = "archers")
//    public void logAfterFindAllArchers(List<Archer> archers) {
//        System.out.println("*** Found " + archers.size() + " archers.");
//    }
}

