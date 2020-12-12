package com.example.audit.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.audit.model.Metric;
import com.example.audit.repository.AuditRepository;

@RunWith(MockitoJUnitRunner.class)
public class AuditAdvieTest{
	
	@InjectMocks
	private AuditAdvie auditAdvie;
	
	@Mock
	private AuditRepository auditRepository;
	
	@Mock
	private ProceedingJoinPoint joinPoint;
	
	@Mock
	private Audit audit;
	
	@Captor
	private ArgumentCaptor<Metric> metricCaptor;
	
	@BeforeAll
	private void before() {
		reset();
	}
	
	
	@Test
	public void test() throws Throwable {
		//GIVEN
		MethodSignature methodSignature = Mockito.mock(MethodSignature.class);
		when(joinPoint.getSignature()).thenReturn(methodSignature);
		when(methodSignature.getName()).thenReturn("operation");
		
		when(audit.value()).thenReturn("r_operation_name");
		
		//WHEN
		auditAdvie.subSystemMetrologie(joinPoint, audit);
		
		
		//THEN
		verify(auditRepository, times(1)).flush(metricCaptor.capture());
		
		Metric metric = metricCaptor.getValue();
		assertThat(metric.getResponseTime()).isNotNull();
		assertThat(metric.getName()).isEqualTo("r_operation_name");
		assertThat(metric.getOperation()).isEqualTo("operation");
		assertThat(metric.getDebut()).isNotNull();
		assertThat(metric.getFin()).isNotNull();
	}
	
	

}
