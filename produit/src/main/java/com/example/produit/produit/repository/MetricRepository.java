package com.example.produit.produit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.produit.produit.repository.entity.MetricEntity;

@Repository
public interface MetricRepository extends MongoRepository<MetricEntity, String>{
	

}
