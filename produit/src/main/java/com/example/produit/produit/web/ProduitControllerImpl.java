package com.example.produit.produit.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		
		Pageable pageRequest = PageRequest.of(0, config.getLimit());
		 
		Page<com.example.produit.produit.repository.entity.Produit> produitsEntity = produitService.getAllProduits(pageRequest);
		
		List<Produit> produitsDomain = ProduitRepresentation.buildListProduitRepresentation(produitsEntity.getContent());
		
		return ResponseEntity.ok().body(produitsDomain);
	}
	
	@GetMapping(value="/produits/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Produit> getProduit (@PathVariable String id) {
		 
		com.example.produit.produit.repository.entity.Produit produitEntity = produitService.getProduit(id).get();
		
		Produit produitsDomain = ProduitRepresentation.buildOneProduitRepresentation(produitEntity);
		
		return ResponseEntity.ok().body(produitsDomain);
	}
	
	@PostMapping(value="/produits", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Produit> postProduit (@RequestBody Produit produit) {
		 
		com.example.produit.produit.repository.entity.Produit produitEntity = produitService.addProduit(produit);;
		
		Produit produitsDomain = ProduitRepresentation.buildOneProduitRepresentation(produitEntity);
		
		return ResponseEntity.ok().body(produitsDomain);
	}

}
