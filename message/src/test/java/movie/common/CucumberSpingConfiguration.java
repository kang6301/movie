package movie.common;


import movie.MessageApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = { MessageApplication.class })
public class CucumberSpingConfiguration {
    
}
