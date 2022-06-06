package kr.co.hjsoft;

import org.apache.catalina.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
@EnableJpaAuditing
public class Hell0Application {
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
        return new HiddenHttpMethodFilter();
    }
    public static void main(String[] args) {
        SpringApplication.run(Hell0Application.class, args);
    }


  


}

