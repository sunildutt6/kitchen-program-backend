package com.sunil.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.sunil.ecommerce.entities.Product;
import com.sunil.ecommerce.entities.ProductCategory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	

	@Autowired
	private EntityManager entityManager;
	
	/*public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}*/

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		HttpMethod[] actions = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
		
		//Product class disabling post, put and delete method
		config.getExposureConfiguration().forDomainType(Product.class)
		.withItemExposure((metadata, httpMethods) -> httpMethods.disable(actions))
		.withCollectionExposure((metadata,httpMethods)-> httpMethods.disable(actions));
		
		//ProductCategory class disabling post, put and delete method
		
		config.getExposureConfiguration().forDomainType(ProductCategory.class)
		.withItemExposure((metadata,httpMethods) -> httpMethods.disable(actions))
		.withCollectionExposure((metadata, httpMethods) ->httpMethods.disable(actions));
				
		
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
		
		exposeIds(config);
		
		
	}

	private void exposeIds(RepositoryRestConfiguration config) {

		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		List<Class> entityClasses = new ArrayList<>();
		
		for(EntityType temEntityType : entities) {
			entityClasses.add(temEntityType.getJavaType());
		}
		
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
	}

	

	
	
	

}
