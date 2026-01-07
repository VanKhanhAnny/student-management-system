package com.codewithanny.studentservice.aspects;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StudentServiceMetrics {

    private final MeterRegistry meterRegistry;

    public StudentServiceMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("execution(* com.codewithanny.studentservice.service.StudentService.getStudents(..))")
    public Object monitorGetStudents(ProceedingJoinPoint joinPoint) throws Throwable {
        meterRegistry.counter("custom.redis.cache.miss", "cache", "students")
                .increment();


        Object result =  joinPoint.proceed();
        return result;
    }
}
