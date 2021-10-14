package com.redislabs.demos.redisbank;

import java.io.Serializable;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "")
@EnableConfigurationProperties(RedisProperties.class)
@EnableAutoConfiguration
@ComponentScan("com.redislabs.demos.redisbank")
public @Data class Config {

	private StompConfig stomp = new StompConfig();

	public static @Data class StompConfig implements Serializable {
		private static final long serialVersionUID = -623741573410463326L;
		private String protocol;
		private String host;
		private int port;
		private String endpoint;
		private String destinationPrefix;
		private String transactionsTopic;
	}

}