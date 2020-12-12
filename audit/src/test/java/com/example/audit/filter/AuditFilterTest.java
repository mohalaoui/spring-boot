package com.example.audit.filter;

import static com.example.audit.constant.MetricConstants.X_REQUEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.example.audit.config.AuditConfigurationProperties;
import com.example.audit.model.Metric;
import com.example.audit.repository.AuditRepository;

@RunWith(MockitoJUnitRunner.class)
public class AuditFilterTest {

	@InjectMocks
	private AuditFilter auditFilter;

	@Mock
	private AuditRepository auditRepository;

	@Mock
	private AuditConfigurationProperties configuration;

	@Captor
	private ArgumentCaptor<Metric> metricCaptor;
	
	@BeforeAll
	private void before() {
		reset();
	}

	@Test
	public void should_not_send_event_if_audit_is_disabled() throws IOException, ServletException {
		// GIVEN
		final MockHttpServletRequest request = new MockHttpServletRequest();
		final MockHttpServletResponse response = new MockHttpServletResponse();
		final MockFilterChain filterChain = new MockFilterChain();

		when(configuration.isEnable()).thenReturn(Boolean.FALSE);

		// WHEN
		auditFilter.doFilter(request, response, filterChain);
		// THEN
		verify(auditRepository, never()).flush(any(Metric.class));
	}

	@Test
	public void should_send_event_if_audit_is_enabled() throws IOException, ServletException {
		// GIVEN
		final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/produits");
		request.addHeader(X_REQUEST_ID, "value");
		final MockHttpServletResponse response = new MockHttpServletResponse();
		final MockFilterChain filterChain = new MockFilterChain();

		when(configuration.isEnable()).thenReturn(Boolean.TRUE);

		// WHEN
		auditFilter.doFilter(request, response, filterChain);

		// THEN
		verify(auditRepository, times(1)).flush(metricCaptor.capture());

		Metric metric = metricCaptor.getValue();
		assertThat(metric.getOperation()).isEqualTo("GET_PRODUITS");
		assertThat(metric.getInstance()).isBlank();
		assertThat(metric.getStatus()).isEqualTo("200");
		assertThat(metric.getContext().getRequestId()).isBlank();
		assertThat(metric.getDebut()).isNotNull();
		assertThat(metric.getFin()).isNotNull();
		assertThat(metric.getResponseTime()).isNotNull();
	}

}
