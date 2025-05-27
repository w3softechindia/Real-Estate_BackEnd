package com.realestate.main.redisConfiguration;

import java.net.URI;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		URI redisUri = URI.create(System.getenv("REDISCLOUD_URL"));
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(redisUri.getHost());
		config.setPort(redisUri.getPort());
		String[] userInfo = redisUri.getUserInfo().split(":", 2);
		config.setPassword(RedisPassword.of(userInfo[1]));
		return new LettuceConnectionFactory(config);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}
}
