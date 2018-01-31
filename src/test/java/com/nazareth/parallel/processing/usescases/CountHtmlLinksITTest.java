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
public class CountHtmlLinksITTest {

  @Autowired private CountHtmlLinks countHtmlLinks;

  @Test
  public void count_links() {
    String url = "https://www.warnerbros.com/archive/spacejam/movie/jam.htm";

    final Map<String, Integer> linkCount = countHtmlLinks.execute(url);

    assertThat(linkCount).as("The total of links must be 12").hasSize(12);
    assertThat(
            linkCount.get(
                "https://www.warnerbros.com/archive/spacejam/movie/cmp/jump/jumpframes.html"))
        .as("The total number of times this link appeared were 2")
        .isEqualTo(1);
  }
}
