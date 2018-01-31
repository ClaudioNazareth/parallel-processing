package com.nazareth.parallel.processing.gateways.ff4j;

import com.nazareth.parallel.processing.configs.Features;
import com.nazareth.parallel.processing.gateways.FeatureGateway;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Concrete Gateway to Feature Toggle configuration
 *
 * @author Claudio Nazareth
 */
@Component
public class FeatureGatewayImpl implements FeatureGateway {

  private static final Logger log = LoggerFactory.getLogger(FeatureGatewayImpl.class);

  private final FF4j ff4j;

  public FeatureGatewayImpl(FF4j ff4j) {
    this.ff4j = ff4j;
  }

  /**
   * @param feature
   * @return true if the toggle exist or it is enabled and false if the toggle is disabled o does
   *     not exists
   */
  @Override
  public boolean check(final Features feature) {
    return ff4j.check(feature.getKey());
  }

  @Override
  public void createIfNotExists(Features feature) {
    if (!ff4j.exist(feature.getKey())) {
      ff4j.createFeature(
          new Feature(
              feature.getKey(),
              feature.isDefaultValue(),
              feature.getDescription(),
              feature.getGroupName()));
      log.info("Feature {} created with value {}: ", feature.getKey(), feature.isDefaultValue());
    }
  }
}
