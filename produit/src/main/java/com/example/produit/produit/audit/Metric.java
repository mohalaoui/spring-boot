package com.example.produit.produit.audit;

import java.util.Date;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Metric {
	
	@JsonProperty("nom")
    private String name;

    private String description;
    
	@JsonProperty("duree")
    private Long responseTime;

	@JsonProperty("debut")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="CET")
	private Date debut;
    
	@JsonProperty("fin")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="CET")
	private Date fin;
	
	public Metric() {
		this.debut = LocalDateTime.now().toDate();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	
	
}
