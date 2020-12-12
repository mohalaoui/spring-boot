package com.example.produit.produit.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.produit.produit.repository.ProduitRepository;
import com.example.produit.produit.repository.entity.ProduitEntity;
import com.example.produit.produit.web.builder.Produit;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class SubSystemServiceTest {
	
	@InjectMocks
	private SubSystemService subsystemService;
	
	@Mock
	private ProduitRepository produitRepository;
	
	@Spy
	private ObjectMapper objectMapper;
	
	@Captor
	private ArgumentCaptor<ProduitEntity> metricCaptor;
	
	@Test
	public void should_call_produitRepository_findAll() {
		//GIVEN
		Pageable pageable =PageRequest.of(0, 1);
		
		//WHEN
		subsystemService.getAllProduits(pageable);
		
		//THEN
		verify(produitRepository, times(1)).findAll(pageable);
	}

	@Test
	public void should_call_produitRepository_findById() {
		//GIVEN
		
		//WHEN
		subsystemService.getProduit("id");
		
		//THEN
		verify(produitRepository, times(1)).findById("id");
	}
	
	@Test
	public void should_call_produitRepository_save() {
		//GIVEN
		Produit produit =new Produit.Builder()
				.withNom("nom")
				.withPrix(10L)
				.build();
		
		//WHEN
		subsystemService.addProduit(produit);
		
		//THEN
		verify(produitRepository, times(1)).save(metricCaptor.capture());
		
		ProduitEntity produitEntity = metricCaptor.getValue();
		assertThat(produitEntity.getId()).isNotNull();
		assertThat(produitEntity.getNom()).isEqualTo("nom");
		assertThat(produitEntity.getPrix()).isEqualTo(10L);
	}

}
