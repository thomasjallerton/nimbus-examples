package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name="myColourBean")
    public MyColour getMyColors(){
        return new RedColour();
    }
}
