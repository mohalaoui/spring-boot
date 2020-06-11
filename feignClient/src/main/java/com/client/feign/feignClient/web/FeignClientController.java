package com.client.feign.feignClient.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.client.feign.feignClient.entity.Produit;
import com.client.feign.feignClient.proxy.FeignProduitProxy;


@RestController
public class FeignClientController {
	
	@Autowired	
	private FeignProduitProxy feignProduitProxy;
	
	@GetMapping(value="/feign/produits", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Produit>> getProduitsWithFeign() {
		
		return feignProduitProxy.getProduits();
	}
	
	@GetMapping(value="/feign/produit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Produit> getProduitWithFeign(@PathVariable String id) {
		
		return feignProduitProxy.getProduit(id);
	}

}
