package com.example.produit.produit.audit.aop;


import java.util.concurrent.Future;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.example.produit.produit.audit.Metric;
import com.example.produit.produit.core.SubSystemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;


@Component
@Aspect
public class AuditAdvie {

	private static final Logger metroLogger = LoggerFactory.getLogger("metrologie");
	private static final Logger logger = LoggerFactory.getLogger(AuditAdvie.class);
	
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
		
		saveMetric(metric);
		
		return result;
	}

	@Async("auditExecutor")
	public Future<Boolean> saveMetric(Metric metric) {
		logger.info("saving metric to mongodb...");
		try {
			subSystemService.saveMetric(metric);
		} catch (MongoException e) {
			logger.error("failed while saving metric to mongodb...", e);
			return new AsyncResult<Boolean>(false);
		}
		return new AsyncResult<Boolean>(true);
	}
	
}
