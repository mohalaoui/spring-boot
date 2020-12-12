package com.example.audit.repository;

import static com.example.audit.constant.MetricConstants.X_REQUEST_ID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.audit.model.Metric;
import com.example.audit.runtime.ExecutionContext;
import com.example.audit.runtime.ExecutionContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuditRepository {
	
	private static final Logger metroLogger = LoggerFactory.getLogger("metrologie");
	
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	RabbitTemplate rabbitTemplate; 
	
	@Value("${app.id}")
	private String instance;
	
	@Async("auditExecutor")
	public void flush(Metric metric) throws JsonProcessingException {
		
		final ExecutionContext executionContext = ExecutionContextHolder.get();
		
		metric.setInstance(this.instance);
		
		//metric.getAdditionalProperties().put(REQUEST_URI, executionContext.getStringValue(MetricConstants.REQUEST_ID_ATTRIBUTE_NAME));
		metric.getContext().setRequestId(executionContext.getStringValue(X_REQUEST_ID));

		metroLogger.info(objectMapper.writeValueAsString(metric));
		rabbitTemplate.convertAndSend(metric);
	}

}
