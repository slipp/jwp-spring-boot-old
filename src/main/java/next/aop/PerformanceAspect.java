package next.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class PerformanceAspect {
	private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);
	
	@Pointcut("within(next.service..*) || within(next.dao..*)")
    private void anyPublicOperation() {}
	
	@Before("anyPublicOperation()")
	public void logging(JoinPoint jp) {
		Object[] args = jp.getArgs();
		log.debug("Class : {}", jp.getTarget().getClass());
		for (Object arg : args) {
			log.debug("Argument : {}", arg);
		}
	}
	
	@Around("anyPublicOperation()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch stopWatch = new StopWatch();
	    stopWatch.start();
        Object retVal = pjp.proceed();
        stopWatch.stop();
        log.debug("Class : {}, Execution Time : {}", pjp.toShortString(), stopWatch.getTotalTimeMillis());
        return retVal;
    }
}
