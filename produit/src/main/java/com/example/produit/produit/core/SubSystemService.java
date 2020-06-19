package com.example.produit.produit.core;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.produit.produit.audit.Metric;
import com.example.produit.produit.audit.aop.Audit;
import com.example.produit.produit.repository.MetricRepository;
import com.example.produit.produit.repository.ProduitRepository;
import com.example.produit.produit.repository.entity.MetricEntity;
import com.example.produit.produit.repository.entity.Produit;

@Component
public class SubSystemService {
	
	@Autowired
	ProduitRepository produitRepository;
	
	@Autowired
	MetricRepository metricRepository;
	
	@Audit(value = "r_get_produit_all")
	public Page<Produit> getAllProduits(Pageable pageable) {
		return produitRepository.findAll(pageable);
	}

	@Audit(value = "r_get_produit")
	public Optional<Produit> getProduit(String id) {
		return produitRepository.findById(id);	
	}

	@Audit(value = "r_add_produit")
	public Produit addProduit(com.example.produit.produit.web.builder.Produit produit) {
		String id = UUID.randomUUID().toString();
		Produit produitEntity = new Produit(id, produit.getNom(), produit.getPrix());
		produitRepository.save(produitEntity);
		return produitEntity;
		
	}

	public MetricEntity saveMetric(Metric metric) {
		MetricEntity metricEntity = new MetricEntity();
		BeanUtils.copyProperties(metric, metricEntity);
		metricRepository.save(metricEntity);
		return metricEntity;
		
	}
}
