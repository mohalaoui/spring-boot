package com.example.produit.produit.appContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableConfigurationProperties
public class ProduitSpringConfig {
	
	@Bean 
	ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.setSerializationInclusion(Include.NON_NULL);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
		mapper.setDateFormat(df);
		
		return mapper;
	}

}
