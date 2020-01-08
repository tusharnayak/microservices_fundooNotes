package com.bridgelabz.fundoouser.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoouser.model.User;

@Configuration
@EnableRedisRepositories
@EnableElasticsearchRepositories(basePackages = "com.bridgelabz.fundoouser.config")
public class Config {
	@Bean
	public BCryptPasswordEncoder bcyBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {

		RedisProperties properties = redisProperties();
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(properties.getHost());
		configuration.setPort(properties.getPort());

		return new JedisConnectionFactory(configuration);
	}

	@Bean
	public RedisTemplate<String, User> redisTemplate() {

		final RedisTemplate<String, User> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<>(User.class));
		return template;

	}

	@Bean
	@Primary
	public RedisProperties redisProperties() {
		return new RedisProperties();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
