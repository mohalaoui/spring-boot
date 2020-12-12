package com.example.produit.produit.core;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.produit.produit.repository.entity.ProduitEntity;

/*
 * business logic class
 * 
 */

@Service
public class ProduitServiceImpl implements ProduitService {
	
	@Autowired
	private SubSystemService subSystemService;

	@Override
	public Page<ProduitEntity> getAllProduits(Pageable pageable) {
		return subSystemService.getAllProduits(pageable);
	}

	@Override
	public Optional<ProduitEntity> getProduit(String id) {
		return subSystemService.getProduit(id);	
	}

	@Override
	public ProduitEntity addProduit(com.example.produit.produit.web.builder.Produit produit) {
		return subSystemService.addProduit(produit);
		
	}

}
