package com.nazareth.parallel.processing.controllers;

import com.nazareth.parallel.processing.controllers.jsons.UrlRequest;
import com.nazareth.parallel.processing.usescases.GenerateLinkCountFile;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/url-parser")
public class LinkCounterController {

  private GenerateLinkCountFile generateLinkCountFile;

  @Autowired
  public LinkCounterController(GenerateLinkCountFile generateLinkCountFile) {
    this.generateLinkCountFile = generateLinkCountFile;
  }

  @PostMapping(value = "/generate-count-links-file")
  @ApiOperation(
    value =
        "Reads the content in HTML format from url passed in parameter the default value is https://en.wikipedia.org/wiki/Europe"
            + "Afterwards each link in that article will be stored and followed up, reads again the content "
            + "from the link found and stores all of the URLs found in the content"
  )
  @ApiResponses({
    @ApiResponse(code = 202, message = "Accepted"),
    @ApiResponse(code = 400, message = "Bad Request"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  public ResponseEntity generateLinkCountFile(
      @RequestBody(required = false) UrlRequest urlRequest) {
    generateLinkCountFile.execute(urlRequest.getUrl());
    return ResponseEntity.accepted().build();
  }
}
