package com.cursosdedesarrollo.springbootejemplosaop.aspectos;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// Uso: mide el tiempo de ejecución de métodos en servicios y controladores.
@Aspect
@Component
public class LogTimingAspect {
    @Around("execution(public * com.cursosdedesarrollo.springbootejemplosaop.services..*(..)) || execution(public * com.cursosdedesarrollo.springbootejemplosaop.controllers..*(..))")
    public Object logTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        long end = System.currentTimeMillis();
        String methodName = pjp.getSignature().toShortString();
        System.out.println("Execution time of " + methodName + " : " + (end - start) + " ms");
        return retVal;
    }
}
