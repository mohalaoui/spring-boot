package com.client.feign.feignClient.proxy;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.client.feign.feignClient.entity.Produit;


@FeignClient("zuulServer")
@RibbonClient("produit")
public interface FeignProduitProxy {
	
	@GetMapping(value="/produit/produits", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Produit>> getProduits ();
	
	@GetMapping(value="/produit/produits/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Produit> getProduit (@PathVariable String id);

}
