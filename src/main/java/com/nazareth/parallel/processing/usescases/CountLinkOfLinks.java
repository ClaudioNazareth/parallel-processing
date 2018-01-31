package com.nazareth.parallel.processing.usescases;

import com.nazareth.parallel.processing.utils.ElementFiltersUtils;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CountLinkOfLinks {

  private static final Logger logger = LoggerFactory.getLogger(CountLinkOfLinks.class);

  static AtomicLong counter;

  /**
   * Iterate Iterate on all map links and Connect to the url passed by parameter and select all <a
   * href> elements and get it's values. Count how many times each link appears.
   *
   * @param linksCount | Map of String (url) and Integer (number of times url appears)
   * @return | Map of String (url) and Integer (number of times url appears)
   */
  public Map<String, Integer> execute(final Map<String, Integer> linksCount) {

    final Map<String, Integer> subLinksCount = new ConcurrentHashMap();

    final int linksSize = linksCount.size();

    DecimalFormat df = new DecimalFormat("###.##");
    counter = new AtomicLong(0);

    linksCount.forEach(
        (url, count) -> {
          try {
            final double porcentage = (counter.incrementAndGet() * 100.00) / linksSize;

            logger.info(df.format(porcentage)+ " % processed ");

            Document doc = Jsoup.connect(url.trim()).get();
            final Elements elementLinks = doc.select(ElementFiltersUtils.A_HREF);

            parallelProcessing(subLinksCount, elementLinks);

          } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
          }
        });

    mergeSublinks(linksCount, subLinksCount);

    return linksCount;
  }

  private void parallelProcessing(Map<String, Integer> subLinksCount, Elements elementLinks) {

    final AtomicInteger counter = new AtomicInteger(1);
    elementLinks.parallelStream()
        .filter(element -> !element.attr(ElementFiltersUtils.ABS_HREF).endsWith(ElementFiltersUtils.JPG))
        .forEach(element -> {
              final String attr = element.attr(ElementFiltersUtils.ABS_HREF);
              subLinksCount.merge(attr, 1, Integer::sum);
        });
  }

  private void mergeSublinks(Map<String, Integer> linksCount, Map<String, Integer> subLinksCount) {
    subLinksCount.forEach((key, value) -> linksCount.merge(key, value, Integer::sum));
  }
}
