package com.example.produit.produit.repository.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = MetricEntity.COLLECTION_NAME)
public class MetricEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7390478711993946483L;

	public final static String COLLECTION_NAME = "Metric";

	@Field("nom")
	@JsonProperty("nom")
	private String name;

	private String operation;

	@Field("duree")
	@JsonProperty("duree")
	private Long responseTime;

	@Field("debut")
	@JsonProperty("debut")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "CET")
	private Date debut;

	@Field("fin")
	@JsonProperty("fin")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "CET")
	private Date fin;

	/**
	 * The date in timeInMillis
	 */
	@Field("timestamp")
	@JsonProperty("timestamp")
	private long startTime;

	private String instance;

	private String status;

	private Error error;

	@Field("ctx")
	@JsonProperty("ctx")
	private MetricContext context;

	private Map<String, Object> additionalProperties;

	public MetricEntity() {
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public MetricContext getContext() {
		return context;
	}

	public void setContext(MetricContext context) {
		this.context = context;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

}
