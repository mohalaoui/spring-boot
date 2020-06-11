package com.example.produit.produit.web.builder;

import java.util.List;

public interface ProduitRepresentation {
	
	Produit buildOneProduitRepresentation(com.example.produit.produit.repository.entity.Produit produit);
	
	List<Produit> buildListProduitRepresentation(List<com.example.produit.produit.repository.entity.Produit> produit);
	
	

}
