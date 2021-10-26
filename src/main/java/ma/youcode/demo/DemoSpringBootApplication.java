package ma.youcode.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * @SpringBootApplication : est une simple encapsulation de trois annotations :
 * 
 * @Configuration donne à la classe actuelle la possibilité 
 * de définir des configurations qui iront remplacer les traditionnels fichiers XML
 * 
 * @EnableAutoConfiguration active l'auto-configuration
 * 
 * @ComponentScan : Indique qu'il faut scanner les classes de ce package afin de trouver des Beans de configuration.
 * */

@SpringBootApplication
public class DemoSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

}
