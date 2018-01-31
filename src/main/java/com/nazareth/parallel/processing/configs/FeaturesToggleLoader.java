package com.nazareth.parallel.processing.configs;

import com.nazareth.parallel.processing.gateways.FeatureGateway;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * Configure the feature toggles when the application starts
 *
 * @author Claudio Nazareth
 */
@Configuration
public class FeaturesToggleLoader {

  private final FeatureGateway featureGateway;

  public FeaturesToggleLoader(final FeatureGateway featureGateway) {
    this.featureGateway = featureGateway;
  }

  @PostConstruct
  public void createFeatures() {
    for (Features feature : Features.values()) {
      featureGateway.createIfNotExists(feature);
    }
  }
}
