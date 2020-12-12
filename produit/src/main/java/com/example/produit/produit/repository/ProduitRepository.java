package com.example.produit.produit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.produit.produit.repository.entity.ProduitEntity;

@Repository
public interface ProduitRepository extends MongoRepository<ProduitEntity, String>{
	
	@Query("{ 'nom' : ?0 }")
	List<ProduitEntity> findProduitByNom(String nom);

}
