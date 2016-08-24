package next.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(HomeControllerTest.class);
	
	@Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void home() {
        ResponseEntity<String> result = this.restTemplate.getForEntity("/", String.class);
        logger.debug(result.getBody());
    }
}
