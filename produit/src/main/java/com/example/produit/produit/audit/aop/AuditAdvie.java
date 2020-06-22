package com.example.produit.produit.audit.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.produit.produit.audit.model.Metric;
import com.example.produit.produit.audit.repository.AuditRepository;


@Aspect
@Component
public class AuditAdvie {


	@Autowired
	AuditRepository auditRepository;
	
	@Around("@annotation(audit)")
	public Object subSystemMetrologie(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {

		Metric metric =new Metric(); // need to set date begin + statTime
		Object result = joinPoint.proceed();
		Interval interval = new Interval(metric.getStartTime(), System.currentTimeMillis());
		metric.setResponseTime(interval.toDurationMillis());
		
		metric.setName(audit.value());
		metric.setOperation(joinPoint.getSignature().getName());
		metric.setFin(LocalDateTime.now().toDate());
		
		
		auditRepository.flush(metric);
		
		return result;
	}

}
