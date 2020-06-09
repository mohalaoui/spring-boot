package com.example.produit.produit.appContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.produit.produit.config.MongoDBProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;


@Configuration
@EnableMongoRepositories(basePackages = "com.example.produit.produit.repository")
public class MongoDBSpringConfig {

	@Autowired
	MongoDBProperties mongoProperties;

	public String getDatabaseName() {
		return mongoProperties.getDatabaseName();
	}
	
	@Bean
	public MongoClient mongoClient() {
		ServerAddress address = new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort());
		MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUserName(), getDatabaseName(), mongoProperties.getPassword().toCharArray());
		MongoClientOptions options = new MongoClientOptions.Builder().build();
        
		MongoClient client = new MongoClient(address, credential, options);
		return client;
	}
	
	@Bean
	public MongoDbFactory mongoDbFactory() {
		MongoDbFactory factory = new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
		return factory;
	}
	
	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate template = new MongoTemplate(mongoDbFactory());
		template.setReadPreference(ReadPreference.primaryPreferred());
		template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		return template;
	}
}
