package com.example.produit.produit.appContext;

import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.util.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
	
	@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory());

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper());
        
        List<HttpMessageConverter<?>> converters = Lists.newArrayList();
        converters.add(mappingJackson2HttpMessageConverter);

        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(closeableHttpClient());
        return requestFactory;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultRequestConfig(requestConfig());
        return httpClientBuilder.build();
    }

    @Bean
    public RequestConfig requestConfig() {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        requestConfigBuilder.setConnectTimeout(100000);
        requestConfigBuilder.setSocketTimeout(100000);
        requestConfigBuilder.setConnectionRequestTimeout(100000);

        return requestConfigBuilder.build();
    }
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper;
	}

}
