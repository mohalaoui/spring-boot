package com.example.produit.produit.audit.runtime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.example.produit.produit.audit.constant.MetricConstants;
import com.google.common.collect.Maps;

/**
 * 
 * @author moalaoui
 *
 */
public class ExecutionContext implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2288714427994718097L;

	public static final String DEFAULT_SOURCE_VALUE = "N/A";
	
	public static final String SERVICE_INDICATOR_KEY_NAME ="serviceIndicatorName";
	
	private Map<String, Object> elements = Maps.newConcurrentMap();
	
	/**
	 * 
	 */
	private String serviceIndicatorName;
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void putElement(String key, Object value) {
		elements.put(key, value);
	}
	
	/**
	 * @return the elements
	 */
	public Map<String, Object> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(Map<String, Object> elements) {
		this.elements = elements;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public <T> T getValue(final String key){
		return this.elements.containsKey(key) ? (T) this.elements.get(key) : null;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasKey(final String key){
		return this.elements.containsKey(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getStringValue(String key){
		return this.elements.containsKey(key) ? this.elements.get(key).toString() : StringUtils.EMPTY;
	}
	
	/**
	 * @return the serviceIndicatorName
	 */
	public String getServiceIndicatorName() {
		return serviceIndicatorName;
	}


	/**
	 * @param serviceIndicatorName the serviceIndicatorName to set
	 */
	public void setServiceIndicatorName(String serviceIndicatorName) {
		this.serviceIndicatorName = serviceIndicatorName;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void addEventInfos(final String key, final String value){
		Map<String, Object> ape = getValue(MetricConstants.ADDITIONAL_PROPS_EVENTS);
		if(ape == null) {
			ape = new HashMap<>();
		}
		
		MapUtils.safeAddToMap(ape, key, value);
		putElement(MetricConstants.ADDITIONAL_PROPS_EVENTS, ape);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ExecutionContext clone() {
		ExecutionContext ec = new ExecutionContext();
		ec.serviceIndicatorName = this.serviceIndicatorName;
		ec.elements.putAll(this.elements);
		return ec;
	}
	
}
