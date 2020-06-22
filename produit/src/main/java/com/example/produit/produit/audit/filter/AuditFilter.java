package com.example.produit.produit.audit.filter;

import static com.example.produit.produit.audit.constant.MetricConstants.REQUEST_URI;
import static com.example.produit.produit.audit.constant.MetricConstants.UNEXPECTED_ERROR;
import static com.example.produit.produit.audit.constant.MetricConstants.X_REQUEST_ID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.produit.produit.audit.model.Metric;
import com.example.produit.produit.audit.repository.AuditRepository;
import com.example.produit.produit.audit.runtime.ExecutionContext;
import com.example.produit.produit.audit.runtime.ExecutionContextHolder;
import com.example.produit.produit.config.AuditConfigurationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Splitter;

public class AuditFilter implements Filter{
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	AuditRepository auditRepository;
	
	@Autowired
	AuditConfigurationProperties configuration;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO catch request information for audit
		
		Exception exception = null;
		Metric metric = null;
		
		try {
			if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				
				final ExecutionContext requestContext = ExecutionContextHolder.get();
				
				if(isAuditEnabled()) {
					metric = new Metric(); // set start time in constructor
					populateContext(httpRequest, requestContext);
					populateMDC(httpRequest);
				}
				
				try {
					// invocation suivantes
					chain.doFilter(request, response);
				} catch (Exception e) {
					exception = e;
					throw new ServletException(e);
				} finally {
					if(isAuditEnabled()) {
						monitor(metric, httpResponse, httpRequest, exception);
					}
				}
				
			}
			else {
				//next invocation
				chain.doFilter(request, response);
			}
		} catch (ServletException se) {
			logger.error("Error while processing HTTP request", se);
			throw se;
		} catch (Exception e) {
			logger.error("Error while processing HTTP request", e);
			throw new ServletException(e);
		} finally {
			logger.trace("cleaning execution context...");
			ExecutionContextHolder.cleanup();
			MDC.clear();
		}
		
		
	}

	private void populateContext(HttpServletRequest httpRequest, final ExecutionContext requestContext) {
		
		String requestIdValue = httpRequest.getHeader(X_REQUEST_ID);
		// Si pas de requestId positionne on en génère un
		if(StringUtils.isBlank(requestIdValue)){
			requestIdValue = UUID.randomUUID().toString();
		}
		
		requestContext.putElement(X_REQUEST_ID, requestIdValue);
		//... more info to log
	}

	private void monitor(Metric metric, HttpServletResponse httpResponse, HttpServletRequest httpRequest, Exception exception)
			throws JsonProcessingException {
		
		metric.setOperation(buildDefaultOperationName(httpRequest));
		
		if(exception!=null) {
			metric.getErrorInstance().setError(UNEXPECTED_ERROR);
			metric.getErrorInstance().setDescription(exception.getLocalizedMessage());
		}
		
		// set response status
		metric.setStatus(String.valueOf(httpResponse.getStatus()));
		Interval interval = new Interval(metric.getStartTime(), System.currentTimeMillis());
		metric.setResponseTime(interval.toDurationMillis());
		metric.setFin(LocalDateTime.now().toDate());
		
		//send metric
		auditRepository.flush(metric);
	}

	private void populateMDC(HttpServletRequest httpRequest) {
		logger.trace("adding [name : REQUEST URI, value : {}] to MDC...", httpRequest.getRequestURI());
		MDC.put(REQUEST_URI, httpRequest.getRequestURI());
		//... more info to add to MDC
	}
	
	/**
	 * build default operation name from request uri. 
	 * @param httpRequest
	 * @return
	 */
	private String buildDefaultOperationName(HttpServletRequest httpRequest) {
		
		String path = httpRequest.getRequestURI();
		Iterable<String> parts = Splitter.on('/').trimResults().omitEmptyStrings().split(path);
		Iterator<String> it = parts.iterator();
		
		List<String> partList = new ArrayList<>();
		while(it.hasNext()) {
			String part = it.next();
			if(NumberUtils.isDigits(part) || StringUtils.isBlank(part)){
				continue;
			}
			partList.add(part.toUpperCase());
		}
		
		StringBuilder sb = new StringBuilder(StringUtils.defaultString(httpRequest.getMethod()).toUpperCase())
				.append('_').append(StringUtils.join(partList, '_'));
		return sb.toString();
	}

	private boolean isAuditEnabled() {
		boolean auditable = configuration.isEnable();
		if (!auditable) {
			logger.warn("Audit is disabled. NEVER disable this in a production environment.");
		}
		return auditable;
	}

}
