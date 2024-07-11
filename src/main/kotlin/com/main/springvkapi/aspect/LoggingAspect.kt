package com.main.springvkapi.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Before("execution(* com.main.springvkapi..*(..))")
    fun logBefore(joinPoint: org.aspectj.lang.JoinPoint) {
        logger.info("Method ${joinPoint.signature.name} started with arguments: ${joinPoint.args.joinToString()}")
    }

    @AfterReturning(pointcut = "execution(* com.main.springvkapi..*(..))", returning = "result")
    fun logAfterReturning(joinPoint: org.aspectj.lang.JoinPoint, result: Any?) {
        logger.info("Method ${joinPoint.signature.name} finished with result: $result")
    }

    @Around("execution(* com.main.springvkapi..*(..))")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        val executionTime = System.currentTimeMillis() - start
        logger.info("Method ${joinPoint.signature.name} executed in $executionTime ms")
        return proceed
    }

    @AfterThrowing(pointcut = "execution(* com.main.springvkapi..*(..))", throwing = "exception")
    fun logAfterThrowing(joinPoint: org.aspectj.lang.JoinPoint, exception: Throwable) {
        logger.error("Method ${joinPoint.signature.name} threw exception: ${exception.message}", exception)
    }
}
