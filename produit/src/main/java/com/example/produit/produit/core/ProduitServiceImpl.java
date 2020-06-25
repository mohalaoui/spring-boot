package com.example.produit.produit.core;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.produit.produit.repository.entity.Produit;

/*
 * business logic class
 * 
 */

@Service
public class ProduitServiceImpl implements ProduitService {
	
	@Autowired
	SubSystemService subSystem;

	@Override
	public Page<Produit> getAllProduits(Pageable pageable) {
		return subSystem.getAllProduits(pageable);
	}

	@Override
	public Optional<Produit> getProduit(String id) {
		return subSystem.getProduit(id);	
	}

	@Override
	public Produit addProduit(com.example.produit.produit.web.builder.Produit produit) {
		return subSystem.addProduit(produit);
		
	}

}
