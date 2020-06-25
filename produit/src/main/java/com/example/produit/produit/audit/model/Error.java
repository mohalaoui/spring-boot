package com.example.produit.produit.audit.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
	
	@JsonProperty("error")
	private String error;
	
	@JsonProperty("error_description")
	private String description;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
