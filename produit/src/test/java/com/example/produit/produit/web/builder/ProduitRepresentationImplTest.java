package com.example.produit.produit.web.builder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.produit.produit.repository.entity.ProduitEntity;
import com.google.common.collect.Lists;
@RunWith(SpringRunner.class)
public class ProduitRepresentationImplTest {
	
	private ProduitRepresentationImpl produitRepresentation;
	
	@Before
	public void before() {
		this.produitRepresentation = new ProduitRepresentationImpl();
	}

	@Test
	public void should_build_One_Produit() {
		//GIVEN
		ProduitEntity produit = new ProduitEntity("id", "nom", 10L);
		
		//WHEN
		Produit produitDomain = produitRepresentation.buildOneProduitRepresentation(produit);
		
		//THEN
		assertThat(produitDomain.getId()).isEqualTo("id");
		assertThat(produitDomain.getNom()).isEqualTo("nom");
		assertThat(produitDomain.getPrix()).isEqualTo(10L);
	}
	
	@Test
	public void should_build_One_Produit_without_price() {
		//GIVEN
		ProduitEntity produit = new ProduitEntity("id", "nom", null);
		
		//WHEN
		Produit produitDomain = produitRepresentation.buildOneProduitRepresentation(produit);
		
		//THEN
		assertThat(produitDomain.getId()).isEqualTo("id");
		assertThat(produitDomain.getNom()).isEqualTo("nom");
		assertThat(produitDomain.getPrix()).isNull();
	}
	
	@Test
	public void should_build_One_Produit_without_name() {
		//GIVEN
		ProduitEntity produit = new ProduitEntity("id", null, 10L);
		
		//WHEN
		Produit produitDomain = produitRepresentation.buildOneProduitRepresentation(produit);
		
		//THEN
		assertThat(produitDomain.getId()).isEqualTo("id");
		assertThat(produitDomain.getNom()).isNull();
		assertThat(produitDomain.getPrix()).isEqualTo(10L);
	}
	
	@Test
	public void should_not_build_One_Produit_without_id() {
		//GIVEN
		ProduitEntity produit = new ProduitEntity(null, "nom", 10L);
		
		//WHEN
		
		//THEN
		assertThatThrownBy(() -> produitRepresentation.buildOneProduitRepresentation(produit))
					.isExactlyInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void should_build_List_Produit() {
		//GIVEN
		ProduitEntity produit = new ProduitEntity("id", "nom", 10L);
		List<ProduitEntity> produits = Lists.newArrayList(produit);
		
		//WHEN
		List<Produit> listProduitDomain = produitRepresentation.buildListProduitRepresentation(produits);
		
		//THEN
		assertThat(listProduitDomain.get(0).getId()).isEqualTo("id");
		assertThat(listProduitDomain.get(0).getNom()).isEqualTo("nom");
		assertThat(listProduitDomain.get(0).getPrix()).isEqualTo(10L);
	}
	
	@Test
	public void should_build_List_Produit_without_nom() {
		//GIVEN
		ProduitEntity produit = new ProduitEntity("id", null, 10L);
		List<ProduitEntity> produits = Lists.newArrayList(produit);
		
		//WHEN
		List<Produit> listProduitDomain = produitRepresentation.buildListProduitRepresentation(produits);
		
		//THEN
		assertThat(listProduitDomain.get(0).getId()).isEqualTo("id");
		assertThat(listProduitDomain.get(0).getNom()).isNull();;
		assertThat(listProduitDomain.get(0).getPrix()).isEqualTo(10L);
	}
	
	@Test
	public void should_build_List_Produit_without_prix() {
		//GIVEN
		ProduitEntity produit = new ProduitEntity("id", "nom", null);
		List<ProduitEntity> produits = Lists.newArrayList(produit);
		
		//WHEN
		List<Produit> listProduitDomain = produitRepresentation.buildListProduitRepresentation(produits);
		
		//THEN
		assertThat(listProduitDomain.get(0).getId()).isEqualTo("id");
		assertThat(listProduitDomain.get(0).getNom()).isEqualTo("nom");
		assertThat(listProduitDomain.get(0).getPrix()).isNull();
	}

	
	@Test
	public void should_not_build_List_Produit_without_id() {
		//GIVEN
		ProduitEntity produit = new ProduitEntity(null, "nom", 10L);
		List<ProduitEntity> produits = Lists.newArrayList(produit);
		
		//WHEN
		
		//THEN
		assertThatThrownBy(() -> produitRepresentation.buildListProduitRepresentation(produits))
					.isExactlyInstanceOf(IllegalArgumentException.class);
	}

}
