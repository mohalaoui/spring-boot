/**
 * 
 */
package com.example.produit.produit.audit.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author moalaoui
 *
 */
public class MetricContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5755022716022445368L;
	
	/**
	 * Identifiant de tracking
	 */
	private String trackerId;
	
	private String requestId;
	
	private String executionId;
	
	private String actionId;
	
	private String messageId;
	
	private String process;
	
	private String source;
	
	private String ip;
	
	private String userAgent;
	
	private String referer;
	
	@JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();


	public MetricContext() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param caller
	 * @return
	 */
	public MetricContext withCaller(final String caller) {
		this.source = caller;
		return this;
	}
	
	/**
	 * 
	 * @param trackerId
	 * @return
	 */
	public MetricContext withTrackerId(final String trackerId) {
		this.trackerId = trackerId;
		return this;
	}
	
	/**
	 * @return the trackerId
	 */
	public String getTrackerId() {
		return trackerId;
	}

	/**
	 * @param trackerId the trackerId to set
	 */
	public void setTrackerId(String trackerId) {
		this.trackerId = trackerId;
	}

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the executionId
	 */
	public String getExecutionId() {
		return executionId;
	}

	/**
	 * @param executionId the executionId to set
	 */
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	/**
	 * @return the actionId
	 */
	public String getActionId() {
		return actionId;
	}

	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the process
	 */
	public String getProcess() {
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(String process) {
		this.process = process;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @return the referer
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @param referer the referer to set
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * @param additionalProperties the additionalProperties to set
	 */
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    
    /**
    *
    * @param name
    * @param value
    * @return
    */
	public MetricContext withAdditionalProperty(String name, Object value) {
       this.additionalProperties.put(name, value);
       return this;
    }
	
    /**
	 * @return the additionalProperties
	 */
    @JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

}
