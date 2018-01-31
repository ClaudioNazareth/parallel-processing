package com.nazareth.parallel.processing.controllers.jsons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UrlRequest", description = "Contains URL to be parsed")
public class UrlRequest {

  @ApiModelProperty(value = "Url address to be parsed", required = true)
  private String url;

  public String getUrl() {
    return url;
  }
}
