package com.example.produit.produit.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.produit.produit.repository.entity.ProduitEntity;

public interface ProduitService {
	
	//List<Produit> getAllProduits();
	
	Optional<ProduitEntity> getProduit(String id);
	
	ProduitEntity addProduit(com.example.produit.produit.web.builder.Produit produit);

	Page<ProduitEntity> getAllProduits(Pageable pageable);

}
