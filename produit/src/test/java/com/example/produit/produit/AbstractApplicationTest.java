package com.example.produit.produit;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.produit.produit.appContext.ProduitTestSpringConfiguration;
import com.example.produit.produit.repository.entity.ProduitEntity;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, classes = {ProduitApplication.class})
@ActiveProfiles(ProduitTestSpringConfiguration.TEST_UNITAIRE_PROFILE_NAME)
@DirtiesContext
public abstract class AbstractApplicationTest extends AbstractMongoTestCase{
	
	private static final String APP_ID = "app.id";
	private static final String SPRING_CONFIG_LOCATION = "spring.config.location";
	private static final String FILE_RESOURCE_PREFIX = "file:";
	
	
	private static final String RESPONSE_FILE_LOCACTION = System.getProperty("user.dir") + "/src/test/resources/wiremock/";
	
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(options()
																			.port(8089)
																			.usingFilesUnderDirectory(RESPONSE_FILE_LOCACTION));

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	
	@Autowired
	private RestTemplate restTemplate;
		
	public static void setupSpringConfig() {
		System.setProperty(SPRING_CONFIG_LOCATION,
				FILE_RESOURCE_PREFIX + System.getProperty("user.dir") + "/src/test/resources/testConfig/bootstrap.yml");
		
		System.setProperty(APP_ID, "produit-provider_localhost");
	}
	
	
	@BeforeClass
	public static void startMongo() throws Exception {
		setUp();
		
		setupSpringConfig();
		
		loadFixtures();
	}
	
	@AfterClass
	public static void stopMongo() throws Exception {
		tearDown();
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	private static void loadFixtures() throws UnknownHostException, IOException {
		loadFixtures("produit", ProduitEntity.COLLECTION_NAME, "dataset/fixtures-produit.json");
	}
	
}
