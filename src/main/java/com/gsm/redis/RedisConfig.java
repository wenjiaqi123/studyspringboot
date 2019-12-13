package com.gsm.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 */
@Configuration
public class RedisConfig {

    /**
     * 注入 RedisTemplate
     * SB 自动在容器中生成了 RedisTemplate<Object,Object> 泛型不方便，重新注入一个<String,Object>
     *     可以直接在代码中
     *     @Autowired
     *     private RedisTemplate<String,Object> redisTemplate;
     * @ConditionalOnMissingBean 如果容器中有 RedisTemplate，原本自动配置的 RedisTemplate 不会实例化
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory) {
        //新建一个 Redis
        RedisTemplate<String, Object> rt = new RedisTemplate<>();
        //设置连接工厂
        rt.setConnectionFactory(factory);
        //使用 Jackson2JsonRedisSerializer 替换默认序列化（默认采用的是JDK序列化）
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //配置
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key 采用 String 的序列化方式
        rt.setKeySerializer(stringRedisSerializer);
        //Hash 的 key 采用 String 的序列化方式
        rt.setHashKeySerializer(stringRedisSerializer);
        //value 采用 jackson 的序列化方式
        rt.setValueSerializer(jackson2JsonRedisSerializer);
        //Hash 的 value 采用 jackson 的序列化方式
        rt.setHashValueSerializer(jackson2JsonRedisSerializer);
        rt.afterPropertiesSet();
        return rt;
    }
}