package com.client.feign.feignClient.entity;

public class Produit {

	private String id;
	private String nom;
	private Long prix;
	
	public Produit() {
		// TODO Auto-generated constructor stub
	}
	
	public Produit(String id, String nom, Long prix) {
		this.id = id;
		this.nom = nom;
		this.prix = prix;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Long getPrix() {
		return prix;
	}

	public void setPrix(Long prix) {
		this.prix = prix;
	}
	
	
	
}
