package friends.cringe.common.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;

@org.springframework.context.annotation.Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

  public static final String USER_BY_ID_AND_NAME_CACHE = "userCache";

  public static final String LIST_FORMATION_CACHE = "listFormationCache";

  private final Configuration<Object, Object> jcacheEternalConfiguration;

  public CacheConfig() {
    jcacheEternalConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
        CacheConfigurationBuilder
            .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(1000))
            .withExpiry(ExpiryPolicyBuilder.noExpiration())
            .build()
    );
  }

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> {
      createCache(cm, USER_BY_ID_AND_NAME_CACHE, jcacheEternalConfiguration);
      createCache(cm, LIST_FORMATION_CACHE, jcacheEternalConfiguration);
    };
  }

  private void createCache(CacheManager cm, String cacheName, Configuration<Object, Object> configuration) {
    javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
    if (cache == null) {
      cm.createCache(cacheName, configuration);
    }
  }
}