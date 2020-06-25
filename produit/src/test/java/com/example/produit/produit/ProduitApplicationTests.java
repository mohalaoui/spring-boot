package com.example.produit.produit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(args = ProduitTestSpringConfiguration.TEST_UNITAIRE_APP_ID)
@ActiveProfiles({ProduitTestSpringConfiguration.TEST_UNITAIRE_PROFILE_NAME})	
@DirtiesContext
class ProduitApplicationTests {
	
	
	@Test
	void contextLoads() {
	}

}
