package shb.slc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SlcFWSchedularLogAspect {
    Logger logger= LoggerFactory.getLogger(SlcFWSchedularLogAspect.class);

    @Around("execution(* shb.slc.service..*.*(..))")
    public Object loggingService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        logger.info("start - " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + " / " + proceedingJoinPoint.getSignature().getName() + "/ target : " + proceedingJoinPoint.getTarget().toString());
        Object result = proceedingJoinPoint.proceed();
        logger.info("finished - " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + " / " + proceedingJoinPoint.getSignature().getName() + "/ target : " + proceedingJoinPoint.getTarget().toString());
        return result;
    }

    @Around("execution(* shb.slc.controller..*.*(..))")
    public Object loggingController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        logger.info("start - " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + " / " + proceedingJoinPoint.getSignature().getName() + "/ target : " + proceedingJoinPoint.getTarget().toString());
        Object result = proceedingJoinPoint.proceed();
        logger.info("finished - " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + " / " + proceedingJoinPoint.getSignature().getName() + "/ target : " + proceedingJoinPoint.getTarget().toString());
        return result;
    }



}
