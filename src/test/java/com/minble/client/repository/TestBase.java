package com.minble.client.repository;

import com.minble.client.config.QuerydslConfig;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;

@Import(QuerydslConfig.class)
@EnableJpaAuditing
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class TestBase {

}
