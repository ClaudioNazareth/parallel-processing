package com.nazareth.parallel.processing.usescases;

import com.nazareth.parallel.processing.configs.Features;
import com.nazareth.parallel.processing.gateways.FeatureGateway;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** @author Claudio Nazareth */
@Service
public class GenerateLinkCountFile {

  private static final Logger logger = LoggerFactory.getLogger(GenerateLinkCountFile.class);

  @Value("${data.separator}")
  public String dataSeparator;

  @Value("${parallelism.number}")
  private int parallelism;

  @Value("${file.path}")
  private String filePath;

  private CountHtmlLinks countLinks;

  private CountLinkOfLinks countSubLinks;

  private FeatureGateway featureGateway;

  @Autowired
  public GenerateLinkCountFile(
      CountHtmlLinks countLinks, CountLinkOfLinks countSubLinks, FeatureGateway featureGateway) {
    this.countLinks = countLinks;
    this.countSubLinks = countSubLinks;
    this.featureGateway = featureGateway;
  }

  /**
   * Generate a file with all URL links and their links and how many times this link has been
   * referenced
   *
   * @param url | String url s
   */
  public void execute(String url) {

    if (url == null || url.isEmpty()) {
      url = "https://en.wikipedia.org/wiki/Europe";
    }
    deleteFileIfAlreadyExists();

    final Map<String, Integer> linksCount = countLinks.execute(url);

    if (featureGateway.check(Features.FIND_INSIDE_LINKS_TOGGLE)) {
      countSubLinks.execute(linksCount);
    }

    writeFileInParallel(linksCount);
  }

  private void writeFileInParallel(Map<String, Integer> linksCount) {
    Path mOutputPath = Paths.get(filePath);

    try (Writer writer = Files.newBufferedWriter(mOutputPath)) {
      ForkJoinPool customThreadPool = new ForkJoinPool(parallelism);
      linksCount.forEach( (key, value) -> {
            Thread t1 = new Thread(() ->  customThreadPool.submit(() -> {
                              try {
                                writer.write(key + dataSeparator + value + System.lineSeparator());
                              } catch (IOException e) {
                                e.printStackTrace();
                              }
                         }));
            try {
              t1.start();
              t1.join();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          });
    } catch (UncheckedIOException e) {
      logger.error(e.getLocalizedMessage());
    } catch (IOException e) {
      logger.error(e.getLocalizedMessage());
    }
  }

  private void deleteFileIfAlreadyExists() {
    File file = new File(filePath);
    if(file.exists()){
      Path fileToDeletePath = Paths.get(filePath);
      try {
        Files.delete(fileToDeletePath);
      } catch (IOException e) {
        logger.error(e.getLocalizedMessage());
      }
    }
  }
}
