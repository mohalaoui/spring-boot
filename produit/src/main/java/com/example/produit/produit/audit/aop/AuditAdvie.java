package com.example.produit.produit.audit.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.produit.produit.audit.Metric;
import com.example.produit.produit.core.SubSystemService;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
@Aspect
public class AuditAdvie {

	private static final Logger metroLogger = LoggerFactory.getLogger("metrologie");
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	SubSystemService subSystemService; 
	
	
	@Around("@annotation(audit)")
	public Object subSystemMetrologie(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
		// need to set date begin
		Metric metric =new Metric();
		
		long stratTime=System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime=System.currentTimeMillis();
		
		metric.setName(audit.value());
		metric.setDescription(joinPoint.getSignature().getName());
		metric.setFin(LocalDateTime.now().toDate());
		metric.setResponseTime(endTime-stratTime);
		
		metroLogger.info(objectMapper.writeValueAsString(metric));
		
		subSystemService.saveMetric(metric);
		
		return result;
	}

}
