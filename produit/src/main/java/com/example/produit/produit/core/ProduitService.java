package com.example.produit.produit.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.produit.produit.repository.entity.Produit;

public interface ProduitService {
	
	//List<Produit> getAllProduits();
	
	Optional<Produit> getProduit(String id);
	
	Produit addProduit(com.example.produit.produit.web.builder.Produit produit);

	Page<Produit> getAllProduits(Pageable pageable);

}
