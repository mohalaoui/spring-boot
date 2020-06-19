package com.example.produit.produit.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author Moalaoui
 * 
 */
@ConfigurationProperties(prefix = "audit")
public class AuditConfigurationProperties {

	private boolean local;

	private int executorPoolSize;

	private int executorMaxPoolSize;

	private int executorKeepAlive;

	private int queueCapacity;

	public int getExecutorPoolSize() {
		return executorPoolSize;
	}

	public void setExecutorPoolSize(int executorPoolSize) {
		this.executorPoolSize = executorPoolSize;
	}

	public int getExecutorMaxPoolSize() {
		return executorMaxPoolSize;
	}

	public void setExecutorMaxPoolSize(int executorMaxPoolSize) {
		this.executorMaxPoolSize = executorMaxPoolSize;
	}

	public int getExecutorKeepAlive() {
		return executorKeepAlive;
	}

	public void setExecutorKeepAlive(int executorKeepAlive) {
		this.executorKeepAlive = executorKeepAlive;
	}

	public int getQueueCapacity() {
		return this.queueCapacity;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

}