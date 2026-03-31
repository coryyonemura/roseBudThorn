package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "app", "domain", "validation", "repository", "service", "web" })
public class RoseBudThornApp {

    public static void main(String[] args) {
        SpringApplication.run(RoseBudThornApp.class, args);
    }
}
