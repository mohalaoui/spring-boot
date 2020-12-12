package com.example.audit.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * NOT USED
  
   another way to do AOP
   
   create annotation ex: Audit
   
   must add in configuration:
   	
   	@Bean
    public Advisor advisor() {
       AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();    
       pointcut.setExpression("@annotation(com.example.produit.produit.audit.aop.Audit)");
       return new DefaultPointcutAdvisor(pointcut, new MetricInterceptor());
    }

 */

public class MetricInterceptor implements MethodInterceptor{

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		logger.info("before : {}", invocation.getMethod().getName());
		Object proceed = invocation.proceed();
		logger.info("after : {}", invocation.getMethod().getName());
		
		return proceed;
	}

}
