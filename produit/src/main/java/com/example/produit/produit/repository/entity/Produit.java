package com.example.produit.produit.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Produit.COLLECTION_NAME)
public class Produit {
	
	public final static String COLLECTION_NAME = "Produit";

	@Id
	private String id;
	private String nom;
	private Long prix;
	
	public Produit(String id, String nom, Long prix) {
		super();
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
