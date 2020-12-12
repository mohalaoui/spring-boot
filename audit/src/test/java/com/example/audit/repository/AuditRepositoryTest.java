package com.example.audit.repository;

import static com.example.audit.constant.MetricConstants.X_REQUEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.example.audit.model.Metric;
import com.example.audit.runtime.ExecutionContext;
import com.example.audit.runtime.ExecutionContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class AuditRepositoryTest {

	@InjectMocks
	private AuditRepository auditRepository;
	
	@Spy
	private ObjectMapper objectMapper;

	@Mock
	private RabbitTemplate rabbitTemplate; 
	
	@Captor
	ArgumentCaptor<Metric> metricCaptor;

	@Test
	public void should_flush_metric() throws JsonProcessingException {
		// GIVEN
		Metric metric = getMetricTest();
		
		ExecutionContext executionContext = ExecutionContextHolder.get();
		executionContext.putElement(X_REQUEST_ID, "requestId");
		
		// WHEN
		auditRepository.flush(metric);

		// THEN
		verify(rabbitTemplate, times(1)).convertAndSend(metricCaptor.capture());
		
		Metric metricToAssert = metricCaptor.getValue();
		
		assertThat(metricToAssert.getContext().getRequestId()).isEqualTo("requestId");
		assertThat(metricToAssert.getStatus()).isEqualTo("200");
		
	}

	private Metric getMetricTest() {
		Metric metric = new Metric();
		metric.setDebut(new Date());
		metric.setFin(new Date());
		metric.setName("name");
		metric.setOperation("operation");
		metric.setResponseTime(10000L);
		metric.setStatus("200");
		return metric;
	}

}
