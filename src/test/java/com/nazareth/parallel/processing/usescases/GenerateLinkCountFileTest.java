package com.nazareth.parallel.processing.usescases;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenerateLinkCountFileTest {

  @Autowired private GenerateLinkCountFile generateLinkCountFile;

  @Value("${file.path}")
  private String filePath;

  @Test
  public void execute() {
    String url = "https://www.warnerbros.com/archive/spacejam/movie/jam.htm";
    generateLinkCountFile.execute(url);

    File file = new File(filePath);

    assertThat(file.exists()).isTrue();
  }
}
