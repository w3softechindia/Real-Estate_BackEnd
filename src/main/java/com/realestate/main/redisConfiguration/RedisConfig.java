package com.realestate.main.redisConfiguration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

	@Value("${REDISCLOUD_URL}")
	private String redisUrl;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
	    if (redisUrl == null || redisUrl.isEmpty()) {
	        throw new IllegalArgumentException("REDISCLOUD_URL is not set or empty");
	    }

	    URI redisUri = URI.create(redisUrl);
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
