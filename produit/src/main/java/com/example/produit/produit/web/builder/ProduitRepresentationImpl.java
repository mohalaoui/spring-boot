package com.example.produit.produit.web.builder;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

@Component
public class ProduitRepresentationImpl implements ProduitRepresentation{

	@Override
	public Produit buildOneProduitRepresentation(com.example.produit.produit.repository.entity.ProduitEntity produit) {
		Assert.notNull(produit.getId(), "product id must be set!");
		return new Produit.Builder()
				.withId(produit.getId())
				.withNom(produit.getNom())
				.withPrix(produit.getPrix())
				.build();
	}

	@Override
	public List<Produit> buildListProduitRepresentation(List<com.example.produit.produit.repository.entity.ProduitEntity> produitsEntity) {
		
		List<Produit> produitsDomain = Lists.newArrayList();
		
		produitsEntity.stream().forEach(produit -> {
			produitsDomain.add(buildOneProduitRepresentation(produit));
		});
		
		return produitsDomain;
	}
	
	
	

}
