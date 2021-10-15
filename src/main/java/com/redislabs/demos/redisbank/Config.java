package com.redislabs.demos.redisbank;

import java.io.Serializable;
import java.util.Collections;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.core.convert.MappingConfiguration;
import org.springframework.data.redis.core.index.IndexConfiguration;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.redislabs.demos.redisbank.transactions.BankTransaction;

@Configuration
@ConfigurationProperties(prefix = "")
@EnableConfigurationProperties(RedisProperties.class)
@EnableAutoConfiguration
@EnableRedisRepositories
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

	@Bean
	public RedisMappingContext keyValueMappingContext() {
	  return new RedisMappingContext(
		new MappingConfiguration(new IndexConfiguration(), new MyKeyspaceConfiguration()));
	}

	public static class MyKeyspaceConfiguration extends KeyspaceConfiguration {

		@Override
		protected Iterable<KeyspaceSettings> initialConfiguration() {
			return Collections.singleton(new KeyspaceSettings(BankTransaction.class, "transactions:brad"));
		}
	}


}