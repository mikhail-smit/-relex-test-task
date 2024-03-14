package mikhail.task.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {
    @Value("${spring.redis.password}")
    private String redisPassword;
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        RedisProperties redisProperties = properties();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisProperties.getHost());
        configuration.setPassword(redisPassword);
        configuration.setPort(redisProperties.getPort());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisProperties properties() {
        return new RedisProperties();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
    }

    /**
     * Configure cache ttl for different entities
     * ttl here is magic numbers:)
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("product",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)))
                .withCacheConfiguration("harvestResult",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20)))
                .withCacheConfiguration("user",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)))
                .withCacheConfiguration("role",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(120)));
    }
}
