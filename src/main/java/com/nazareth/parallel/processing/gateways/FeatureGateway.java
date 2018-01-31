package com.nazareth.parallel.processing.gateways;

import com.nazareth.parallel.processing.configs.Features;

/**
 * Gateway to Feature configuration
 *
 * @author Claudio Nazareth
 */
public interface FeatureGateway {

  boolean check(Features feature);

  void createIfNotExists(Features feature);
}
