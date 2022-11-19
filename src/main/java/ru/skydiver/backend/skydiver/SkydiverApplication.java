package ru.skydiver.backend.skydiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SkydiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkydiverApplication.class, args);//запуск приложения спринг
	}

}
