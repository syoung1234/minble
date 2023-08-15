package com.realtimechat.client.repository;

import com.realtimechat.client.config.QuerydslConfig;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;

@Import(QuerydslConfig.class)
@EnableJpaAuditing
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class TestBase {

}
