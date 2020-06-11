package com.example.produit.produit.core;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.produit.produit.repository.ProduitRepository;
import com.example.produit.produit.repository.entity.Produit;

@Service
public class ProduitServiceImpl implements ProduitService {
	
	@Autowired
	ProduitRepository produitDao;

	@Override
	public Page<Produit> getAllProduits(Pageable pageable) {
		return produitDao.findAll(pageable);
	}

	@Override
	public Optional<Produit> getProduit(String id) {
		return produitDao.findById(id);	
	}

	@Override
	public Produit addProduit(com.example.produit.produit.web.builder.Produit produit) {
		String id = UUID.randomUUID().toString();
		Produit produitEntity = new Produit(id, produit.getNom(), produit.getPrix());
		produitDao.insert(produitEntity);
		return produitEntity;
		
	}

}
