package com.example.produit.produit.core;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.produit.produit.web.builder.Produit;

@RunWith(MockitoJUnitRunner.class)
public class ProduitServiceImplTest {
	
	@InjectMocks
	private ProduitServiceImpl ProduitService;
	
	@Mock
	private SubSystemService subSystemService;

	@Test
	public void should_call_getAllProduits() {
		//GIVEN
		Pageable pageable = PageRequest.of(0, 1);
		
		//WHEN
		ProduitService.getAllProduits(pageable);
		
		//THEN
		verify(subSystemService, times(1)).getAllProduits(pageable);
	}
	
	@Test
	public void should_call_getProduit() {
		//GIVEN
		
		//WHEN
		ProduitService.getProduit("id");
		
		//THEN
		verify(subSystemService, times(1)).getProduit("id");
	}
	
	@Test
	public void should_call_addProduit() {
		//GIVEN
		Produit produit = new Produit.Builder()
				.withId("id")
				.withNom("nom")
				.withPrix(10L)
				.build();
		
		//WHEN
		ProduitService.addProduit(produit);
		
		//THEN
		verify(subSystemService, times(1)).addProduit(produit);
	}

}
