package com.example.produit.produit.audit.repository;

import static com.example.produit.produit.audit.constant.MetricConstants.X_REQUEST_ID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.produit.produit.audit.model.Metric;
import com.example.produit.produit.audit.runtime.ExecutionContext;
import com.example.produit.produit.audit.runtime.ExecutionContextHolder;
import com.example.produit.produit.core.SubSystemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuditRepository {
	
	private static final Logger metroLogger = LoggerFactory.getLogger("metrologie");
	
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	SubSystemService subSystemService; 
	
	@Value("${app.id}")
	private String instance;
	
	public void flush(Metric metric) throws JsonProcessingException {
		
		final ExecutionContext executionContext = ExecutionContextHolder.get();
		
		metric.setInstance(instance);
		
		//metric.getAdditionalProperties().put(REQUEST_URI, executionContext.getStringValue(MetricConstants.REQUEST_ID_ATTRIBUTE_NAME));
		metric.getContext().setRequestId(executionContext.getStringValue(X_REQUEST_ID));

		metroLogger.info(objectMapper.writeValueAsString(metric));
		subSystemService.saveMetric(metric);
	}

}
