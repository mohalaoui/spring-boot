package com.example.produit.produit.web.builder;

public class Produit {

	private String nom;
	private Long prix;
	
	public Produit() {
		// TODO Auto-generated constructor stub
	}

	public Produit(String nom, Long prix) {
		super();
		this.nom = nom;
		this.prix = prix;
	}

	public String getNom() {
		return nom;
	}

	public Long getPrix() {
		return prix;
	}

	// builder
	public static class Builder {
		private String nom;
		private Long prix;

		public Builder() {
			// TODO Auto-generated constructor stub
		}

		Builder withId(String id) {
			return this;
		}

		Builder withNom(String nom) {
			this.nom = nom;
			return this;
		}

		Builder withPrix(Long prix) {
			this.prix = prix;
			return this;
		}

		public Produit build() {
			return new Produit(nom, prix);
		}

	}

}
