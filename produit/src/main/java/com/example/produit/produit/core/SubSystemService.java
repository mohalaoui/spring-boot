package com.example.produit.produit.core;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.audit.aop.Audit;
import com.example.produit.produit.repository.ProduitRepository;
import com.example.produit.produit.repository.entity.ProduitEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SubSystemService {
	
	private static final Logger logger = LoggerFactory.getLogger(SubSystemService.class);
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Audit(value = "r_get_produit_all")
	public Page<ProduitEntity> getAllProduits(Pageable pageable) {
		return produitRepository.findAll(pageable);
	}

	@Audit(value = "r_get_produit_one")
	public Optional<ProduitEntity> getProduit(String id) {
		return produitRepository.findById(id);	
	}

	@Audit(value = "r_add_produit")
	public ProduitEntity addProduit(com.example.produit.produit.web.builder.Produit produit) {
		String id = UUID.randomUUID().toString();
		ProduitEntity produitEntity = new ProduitEntity(id, produit.getNom(), produit.getPrix());
		produitRepository.save(produitEntity);
		return produitEntity;
		
	}
	
}
