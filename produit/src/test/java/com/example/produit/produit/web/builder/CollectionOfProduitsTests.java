package com.example.produit.produit.web.builder;

import java.util.List;

import com.example.produit.produit.web.builder.Produit;
import com.google.common.collect.Lists;

public class CollectionOfProduitsTests {
	
	private List<Produit> produitList = Lists.newArrayList();

	public List<Produit> getProduitList() {
		return produitList;
	}

	public void setProduitList(List<Produit> produitList) {
		this.produitList = produitList;
	}
	
	

}
