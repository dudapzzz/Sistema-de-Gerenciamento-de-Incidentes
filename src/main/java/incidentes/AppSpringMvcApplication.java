package incidentes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"controller", "service", "dao", "model", "util"})
public class AppSpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppSpringMvcApplication.class, args);
    }
}