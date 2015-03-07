package com.haogrgr.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.EnvironmentRepository;
import org.springframework.cloud.config.server.NativeEnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServer {

	@Autowired
	private ConfigurableEnvironment environment;

	@Bean
	public EnvironmentRepository environmentRepository() {
		NativeEnvironmentRepository r = new NativeEnvironmentRepository(environment);
		return r;
	}

}
