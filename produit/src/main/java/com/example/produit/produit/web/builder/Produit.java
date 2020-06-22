package com.example.produit.produit.web.builder;

public class Produit {

	private String id;
	private String nom;
	private Long prix;

	public Produit() {
	}

	public Produit(String id, String nom, Long prix) {
		this.id = id;
		this.nom = nom;
		this.prix = prix;
	}

	public String getNom() {
		return nom;
	}

	public Long getPrix() {
		return prix;
	}

	public String getId() {
		return id;
	}

	// builder
	public static class Builder {
		private String id;
		private String nom;
		private Long prix;

		public Builder() {
		}

		Builder withId(String id) {
			this.id = id;
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
			return new Produit(id, nom, prix);
		}

	}

}
