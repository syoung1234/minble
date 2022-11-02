package com.realtimechat.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringbootRealtimeChatProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRealtimeChatProjectApplication.class, args);
	}

}
