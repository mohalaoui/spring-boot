package com.example.audit.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Metric implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -888442290999236554L;

	@JsonProperty("nom")
	private String name;

	private String operation;

	@JsonProperty("duree")
	private Long responseTime;

	@JsonProperty("debut")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "CET")
	private Date debut;

	@JsonProperty("fin")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "CET")
	private Date fin;

	/**
	 * The date in timeInMillis
	 */
	@JsonProperty("timestamp")
	private long startTime;

	private String instance;

	private String status;

	private Error error;

	@JsonProperty("ctx")
	private MetricContext context = new MetricContext();

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	public Metric() {
		this.debut = LocalDateTime.now().toDate();
		this.startTime = debut.getTime();
	}

	public MetricContext getContext() {
		return context;
	}

	public void setContext(MetricContext context) {
		this.context = context;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}
	
	public Date getDebut() {
		return debut;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@JsonIgnore
	public Error getErrorInstance() {
		return new Error();
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

}
