package com.nazareth.parallel.processing.usescases;

import com.nazareth.parallel.processing.utils.ElementFiltersUtils;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Parse HTML get all <a href> get its value attributes of each method.
 *
 * @author Claudio Nazareth
 */
@Service
public class CountHtmlLinks {

  private static final Logger logger = LoggerFactory.getLogger(CountHtmlLinks.class);

  static AtomicInteger counter = new AtomicInteger(1);

  /**
   * Connect to the url passed by parameter and select all <a href> elements and get it's values.
   * Count how many times each link appears.
   *
   * @param url | String url that will be parsed and the links will be counted
   * @return | Map of String (url) and Integer (number of times url appears)
   */
  public Map<String, Integer> execute(String url) {

    if (url == null || url.isEmpty()) {
      url = "https://en.wikipedia.org/wiki/Europe";
    }

    final Map<String, Integer> linksCount = new ConcurrentHashMap();

    try {
      Document doc = Jsoup.connect(url.trim()).get();

      Elements elementLinks = doc.select(ElementFiltersUtils.A_HREF);

      parallelProcessing(linksCount, elementLinks);

    } catch (IOException e) {
      logger.error(e.getLocalizedMessage());
    }

    return linksCount;
  }

  private void parallelProcessing(Map<String, Integer> linksCount, Elements elementLinks) {

    final int linksSize = elementLinks.size();
    elementLinks.parallelStream()
        .filter(element -> !element.attr(ElementFiltersUtils.ABS_HREF).endsWith(ElementFiltersUtils.JPG))
        .forEach(element -> {
              final String attr = element.attr(ElementFiltersUtils.ABS_HREF);
              linksCount.merge(attr, 1, Integer::sum);
        });
  }
}
