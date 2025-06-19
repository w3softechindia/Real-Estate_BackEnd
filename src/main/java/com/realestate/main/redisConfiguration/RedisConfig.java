package com.realestate.main.redisConfiguration;
import java.net.URI;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.cache.RedisCacheManager;


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
	    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
	        RedisTemplate<String, Object> template = new RedisTemplate<>();
	        template.setConnectionFactory(connectionFactory);

	        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

	        template.setKeySerializer(new StringRedisSerializer());
	        template.setValueSerializer(valueSerializer);
	        template.setHashKeySerializer(new StringRedisSerializer());
	        template.setHashValueSerializer(valueSerializer);

	        template.afterPropertiesSet();
	        return template;
	    }

	    @Bean
	    public RedisCacheConfiguration cacheConfiguration() {
	        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

	        return RedisCacheConfiguration.defaultCacheConfig()
	                .entryTtl(Duration.ofHours(1)) // Optional: Set cache TTL
	                .serializeKeysWith(org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
	                .serializeValuesWith(org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
	                .disableCachingNullValues();
	    }

	    @Bean
	    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
	        return RedisCacheManager.builder(connectionFactory)
	                .cacheDefaults(cacheConfiguration())
	                .build();
	    }

}
