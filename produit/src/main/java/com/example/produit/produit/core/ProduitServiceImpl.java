package com.example.produit.produit.core;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.produit.produit.repository.entity.Produit;
import com.google.common.collect.Lists;

@Service
public class ProduitServiceImpl implements ProduitService {

	@Override
	public List<Produit> getAllProduits() {
		Produit produit1 = new Produit("1", "nom1", 300L);
		Produit produit2 = new Produit("2", "nom2", 400L);
		Produit produit3 = new Produit("3", "nom3", 500L);
		Produit produit4 = new Produit("4", "nom4", 600L);
		Produit produit5 = new Produit("5", "nom5", 700L);
		Produit produit6 = new Produit("6", "nom6", 800L);
		
		return Lists.newArrayList(produit1, produit2, produit3, produit4, produit5, produit6);
	}

}
