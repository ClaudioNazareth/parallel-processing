package com.nazareth.parallel.processing.configs;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.ff4j.FF4j;
import org.ff4j.core.FeatureStore;
import org.ff4j.mongo.store.FeatureStoreMongo;
import org.ff4j.mongo.store.PropertyStoreMongo;
import org.ff4j.property.store.PropertyStore;
import org.ff4j.web.ApiConfig;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * FF4j configuration and mongo configuration for ff4j.
 *
 * @author Claudio Nazarth
 */
@Configuration
public class FF4jConfiguration {

  private static final String DEFAULT_CONSOLE = "/ff4j-console/*";

  @Bean
  public ApiConfig getApiConfig(final FF4j ff4j) {
    final ApiConfig apiConfig = new ApiConfig();
    apiConfig.setAuthenticate(false);
    apiConfig.setAutorize(false);
    apiConfig.setFF4j(ff4j);
    return apiConfig;
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean(final FF4j ff4j) {
    return new ServletRegistrationBean(getFF4JServlet(ff4j), DEFAULT_CONSOLE);
  }

  private FF4jDispatcherServlet getFF4JServlet(final FF4j ff4j) {
    final FF4jDispatcherServlet cs = new FF4jDispatcherServlet();
    cs.setFf4j(ff4j);
    return cs;
  }

  @Bean
  public FF4j getFF4j(final FeatureStore featureStore, final PropertyStore propertyStore) {
    final FF4j ff4j = new FF4j();
    ff4j.setFeatureStore(featureStore);
    ff4j.setPropertiesStore(propertyStore);
    return ff4j;
  }

  @Bean
  public FeatureStore featureStore(MongoClient mongoClient, MongoTemplate mongoTemplate) {
    final MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoTemplate.getDb().getName());
    return new FeatureStoreMongo(mongoDatabase, "ff4j-features");
  }

  @Bean
  public PropertyStore propertyStore(MongoClient mongoClient, MongoTemplate mongoTemplate) {
    final MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoTemplate.getDb().getName());
    return new PropertyStoreMongo(mongoDatabase, "ff4j-properties");
  }
}
