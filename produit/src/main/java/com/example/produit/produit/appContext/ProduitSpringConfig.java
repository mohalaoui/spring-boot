package com.example.produit.produit.appContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.produit.produit.audit.filter.AuditFilter;
import com.example.produit.produit.config.AuditConfigurationProperties;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableConfigurationProperties(AuditConfigurationProperties.class)
@EnableAsync
public class ProduitSpringConfig {
	
	// use this instead of @component to avoid that it will be add automatically
	// in case we decide to externalize AuditFilter   
	@Bean
	public AuditFilter auditFilter(){
		return new AuditFilter();
	}
	
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
	
    @Bean(name = "auditExecutor")
    public TaskExecutor auditExecutor(AuditConfigurationProperties config) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getExecutorPoolSize());
        executor.setMaxPoolSize(config.getExecutorMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setKeepAliveSeconds(config.getExecutorKeepAlive());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        executor.setThreadNamePrefix("audit-asyncTask-");
        executor.initialize();
        return executor;
    }

}
