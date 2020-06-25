package com.example.produit.produit;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 
 * @author moalaoui
 *
 */
@Configuration
@Profile(ProduitTestSpringConfiguration.TEST_UNITAIRE_PROFILE_NAME)
public class ProduitTestSpringConfiguration {

	public static final String TEST_UNITAIRE_PROFILE_NAME = "test-unitaire";
	public static final String TEST_UNITAIRE_APP_ID = "--app.id=produit-provider";
	
	
}
