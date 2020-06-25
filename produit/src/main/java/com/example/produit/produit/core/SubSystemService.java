package com.example.produit.produit.core;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.example.produit.produit.audit.aop.Audit;
import com.example.produit.produit.audit.model.Metric;
import com.example.produit.produit.repository.MetricRepository;
import com.example.produit.produit.repository.ProduitRepository;
import com.example.produit.produit.repository.entity.MetricEntity;
import com.example.produit.produit.repository.entity.Produit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;

@Component
public class SubSystemService {
	
	private static final Logger logger = LoggerFactory.getLogger(SubSystemService.class);
	
	@Autowired
	ProduitRepository produitRepository;
	
	@Autowired
	MetricRepository metricRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Audit(value = "r_get_produit_all")
	public Page<Produit> getAllProduits(Pageable pageable) {
		return produitRepository.findAll(pageable);
	}

	@Audit(value = "r_get_produit_one")
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

	@Async("auditExecutor")
	public Future<MetricEntity> saveMetric(Metric metric) {
		
		MetricEntity metricEntity = null;
		
		try {
			//dont do depp copy
			//BeanUtils.copyProperties(metric, metricEntity);
			metricEntity = objectMapper.convertValue(metric, MetricEntity.class);
			logger.debug("saving metric to mongodb...");
			metricRepository.save(metricEntity);
		}
		catch (IllegalArgumentException e) {
			logger.error("failed while copying from metricDTO to metricEntity...", e);
			return new AsyncResult<MetricEntity>(null);
		}
		catch (MongoException e) {
			logger.error("failed while saving metric to mongodb...", e);
			return new AsyncResult<MetricEntity>(null);
		}
		return new AsyncResult<MetricEntity>(metricEntity);
		
	}
	
}
