package com.nazareth.parallel.processing.configs;

/**
 * Enum to configure feature toggle
 *
 * @author Claudio Nazareth
 */
public enum Features {
  FIND_INSIDE_LINKS_TOGGLE(
      "FIND_INSIDE_LINKS_TOGGLE", "Enable or Disable search in inside link", "Synertrade", true);

  private final String key;
  private final String description;
  private final String groupName;
  private final boolean defaultValue;

  Features(
      final String key, final String group, final String description, final boolean defaultValue) {
    this.key = key;
    this.description = description;
    this.defaultValue = defaultValue;
    this.groupName = group;
  }

  public String getKey() {
    return key;
  }

  public String getDescription() {
    return description;
  }

  public String getGroupName() {
    return groupName;
  }

  public boolean isDefaultValue() {
    return defaultValue;
  }
}
