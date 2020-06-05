package com.example.produit.produit.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.produit.produit.config.ProduitProperties;
import com.example.produit.produit.core.ProduitService;
import com.example.produit.produit.web.builder.Produit;
import com.example.produit.produit.web.builder.ProduitRepresentationImpl;

@RestController
public class ProduitControllerImpl {
	
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	private ProduitRepresentationImpl ProduitRepresentation;
	
	@Autowired
	private ProduitProperties config;
	
	@GetMapping(value="/produits", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Produit>> getProduits () {
		 
		List<com.example.produit.produit.repository.entity.Produit> produitsEntity = produitService.getAllProduits().subList(0, config.getLimit());
		
		List<Produit> produitsDomain = ProduitRepresentation.buildListProduitRepresentation(produitsEntity);
		
		return ResponseEntity.ok().body(produitsDomain);
	}
	
	@GetMapping(value="/produits/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Produit> getProduit (@PathVariable String id) {
		 
		com.example.produit.produit.repository.entity.Produit produitEntity = produitService.getProduit(id);
		
		Produit produitsDomain = ProduitRepresentation.buildOneProduitRepresentation(produitEntity);
		
		return ResponseEntity.ok().body(produitsDomain);
	}

}
