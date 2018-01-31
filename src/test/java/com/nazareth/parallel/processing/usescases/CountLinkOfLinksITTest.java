package com.nazareth.parallel.processing.usescases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountLinkOfLinksITTest {

  @Autowired private CountLinkOfLinks countLinkOfLinks;

  @Autowired private CountHtmlLinks countHtmlLinks;

  @Test
  public void execute() {

    String url = "https://www.warnerbros.com/archive/spacejam/movie/jam.htm";

    final Map<String, Integer> linkCount = countHtmlLinks.execute(url);

    final Map<String, Integer> allLinksCounted = countLinkOfLinks.execute(linkCount);

    assertThat(allLinksCounted).as("The total of links must be 12").hasSize(157);

    assertThat(allLinksCounted.get("https://www.wbstudiotour.co.uk/"))
        .as("The total number of times this link appeared were 2")
        .isEqualTo(2);
  }
}
