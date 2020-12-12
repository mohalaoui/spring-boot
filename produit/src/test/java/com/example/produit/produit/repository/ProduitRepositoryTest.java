package com.example.produit.produit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.produit.produit.AbstractApplicationTest;
import com.example.produit.produit.repository.entity.ProduitEntity;

public class ProduitRepositoryTest extends AbstractApplicationTest{
	
	@Autowired
	private ProduitRepository produitRepository; 

	@Test
	public void should_find_produit_by_id() {
		//GIVEN findProduitByNom
		
		//WHEN
		Optional<ProduitEntity> produit = produitRepository.findById("8ecf65bf-50ea-441a-ab0b-cae1e610d9e7");
		
		//THEN
		assertThat(produit.get().getId()).isEqualTo("8ecf65bf-50ea-441a-ab0b-cae1e610d9e7");
		assertThat(produit.get().getNom()).isEqualTo("nom2");
		assertThat(produit.get().getPrix()).isEqualTo(20000L);
		
	}
	
	@Test
	public void should_find_produit_by_name() {
		//GIVEN findProduitByNom
		
		//WHEN
		List<ProduitEntity> produits = produitRepository.findProduitByNom("nom2");
		
		//THEN
		assertThat(produits.size()).isEqualTo(2);
		assertThat(produits.get(0).getId()).isEqualTo("b29eb6bf-28bd-423e-af8d-ba81e31d50db");
		assertThat(produits.get(0).getNom()).isEqualTo("nom2");
		assertThat(produits.get(0).getPrix()).isEqualTo(20000L);
		
	}
	
	@Test
	public void should_find_all_produit() {
		//GIVEN
		
		//WHEN
		 List<ProduitEntity> produits = produitRepository.findAll();
		
		//THEN
		assertThat(produits.get(0).getId()).isEqualTo("011187cd-f753-479f-a23e-3bc64c5adeb1");
		assertThat(produits.get(0).getNom()).isEqualTo("nom1");
		assertThat(produits.get(0).getPrix()).isEqualTo(10000L);
		
	}
	
	@Test
	public void should_insert_produit() {
		//GIVEN
		
		ProduitEntity produit = new ProduitEntity("id", "nom", 1000L);
		//WHEN
		produitRepository.save(produit);
		
		//THEN
		assertThat(produit.getId()).isEqualTo("id");
		assertThat(produit.getNom()).isEqualTo("nom");
		assertThat(produit.getPrix()).isEqualTo(1000L);
		
	}

}
