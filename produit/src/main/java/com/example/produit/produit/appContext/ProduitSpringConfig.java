package com.example.produit.produit.appContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.audit.appcontext.AuditSpringConfig;

@Configuration
@Import(value = { AuditSpringConfig.class})
public class ProduitSpringConfig {

}
