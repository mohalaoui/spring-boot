package com.example.audit.appcontext;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitMQSpringConfig {
	
	@Autowired
	ObjectMapper objectMapper;

	/**
	 * adding objectMapper to rabbitTemplate 
	 * the auto-configured come without jackson
	 * 
	 * @param connectionFactory
	 * @param rabbitProperties
	 * @return
	 */
	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RabbitProperties rabbitProperties) {
		
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		MessageConverter messageConverter = new Jackson2JsonMessageConverter(objectMapper);
		rabbitTemplate.setMessageConverter(messageConverter);
		rabbitTemplate.setExchange(rabbitProperties.getTemplate().getExchange());
		rabbitTemplate.setRoutingKey(rabbitProperties.getTemplate().getRoutingKey());
		
		return rabbitTemplate;
	}

}
