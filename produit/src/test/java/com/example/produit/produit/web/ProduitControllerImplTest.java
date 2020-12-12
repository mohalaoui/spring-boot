package com.example.produit.produit.web;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.produit.produit.AbstractApplicationTest;
import com.example.produit.produit.web.builder.Produit;
import com.github.tomakehurst.wiremock.client.WireMock;

public class ProduitControllerImplTest extends AbstractApplicationTest {
	
	@Test
	public void should_get_one_exist_produit() {
		//GIVEN
		
		// can use wirmock stubbing for mocking rest call inside method that we want to test 
		
//		stubFor(WireMock.get(urlEqualTo("/produits/011187cd-f753-479f-a23e-3bc64c5adeb1"))
//		        .willReturn(aResponse()
//		        		.withStatus(HttpStatus.SC_OK)
//		        		.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//		                .withBodyFile("getProduit/getOneProduit.json")));
		
		//WHEN
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);  
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		 
		HttpEntity<Produit> requestEntity = new HttpEntity<>(null, headers);
		
		String url = "http://localhost:9104/produits/8ecf65bf-50ea-441a-ab0b-cae1e610d9e7";
		ResponseEntity<Produit> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, Produit.class);
		
		//THEN
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		Produit produit = responseEntity.getBody();
		assertThat(produit.getId()).isEqualTo("8ecf65bf-50ea-441a-ab0b-cae1e610d9e7");
		assertThat(produit.getNom()).isEqualTo("nom2");
		assertThat(produit.getPrix()).isEqualTo(20000L);
		
	}
	
	@Test
	public void should_get_list_of_exist_produits() {
		//WHEN
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);  
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		 
		HttpEntity<Produit> requestEntity = new HttpEntity<>(null, headers);
		

		//WHEN
		String url = "http://localhost:9104/produits";
		ResponseEntity<Produit[]> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, Produit[].class);
		
		//THEN
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		Produit[] produits = responseEntity.getBody();
		assertThat(produits[0].getId()).isEqualTo("011187cd-f753-479f-a23e-3bc64c5adeb1");
		assertThat(produits[0].getNom()).isEqualTo("nom1");
		assertThat(produits[0].getPrix()).isEqualTo(10000L);
		
	}
	
	@Test
	public void should_post_produit() {
		//WHEN
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);  
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		
		Produit produit = new Produit.Builder()
										.withNom("nom3")
										.withPrix(50000L)
										.build();
		
		HttpEntity<Produit> requestEntity = new HttpEntity<>(produit, headers);
		

		//WHEN
		String url = "http://localhost:9104/produits";
		ResponseEntity<Produit> responseEntity = getRestTemplate().exchange(url, HttpMethod.POST, requestEntity, Produit.class);
		
		//THEN
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		Produit produitResponse = responseEntity.getBody();
		assertThat(produitResponse.getId()).isNotNull();
		assertThat(produitResponse.getNom()).isEqualTo("nom3");
		assertThat(produitResponse.getPrix()).isEqualTo(50000L);
		
	}
	

}
