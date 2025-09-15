package com.cursosdedesarrollo.springbootejemplosaop.aspectos;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductServiceAspect {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceAspect.class);

    // Pointcut para todos los métodos públicos en ProductService
    @Pointcut("execution(public * com.cursosdedesarrollo.springbootejemplosaop.services.ProductService.*(..))")
    public void productServiceMethods() {}

    @Before("productServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("➡️ Antes de ejecutar: {} con args={}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "productServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("✅ Después de ejecutar: {} -> resultado={}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    @AfterThrowing(pointcut = "productServiceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.error("💥 Excepción en {}: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage());
    }

    @After("productServiceMethods()")
    public void logAfterFinally(JoinPoint joinPoint) {
        log.info("🔚 Finalizó ejecución de {}", joinPoint.getSignature().toShortString());
    }
}

