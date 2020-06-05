package com.example.produit.produit.web.builder;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class ProduitRepresentationImpl implements ProduitRepresentation{

	@Override
	public Produit buildOneProduitRepresentation(com.example.produit.produit.repository.entity.Produit produit) {
		return new Produit.Builder()
				.withId(produit.getId())
				.withNom(produit.getNom())
				//.withPrix(produit.getPrix())
				.build();
	}

	@Override
	public List<Produit> buildListProduitRepresentation(
			List<com.example.produit.produit.repository.entity.Produit> produitsEntity) {
		List<Produit> produitsDomain = Lists.newArrayList();
		produitsEntity.stream().forEach(produit -> {
			produitsDomain.add(buildOneProduitRepresentation(produit));
		});
		
		return produitsDomain;
	}
	
	
	

}
