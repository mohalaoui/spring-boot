package com.example.produit.produit.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.produit.produit.config.ProduitProperties;
import com.example.produit.produit.core.ProduitService;
import com.example.produit.produit.repository.entity.Produit;

@RestController
public class ProduitControllerImpl {
	
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	private ProduitProperties config;
	
	@GetMapping(value="/produits", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Produit>> getProduits () {
		 
		List<Produit> produits = produitService.getAllProduits().subList(0, config.getLimit());
		
		return ResponseEntity.ok().body(produits);
	}

}
