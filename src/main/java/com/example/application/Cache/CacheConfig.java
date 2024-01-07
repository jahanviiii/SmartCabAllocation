package com.example.application.Cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cabLocationCacheConfig = new CacheConfiguration();
        cabLocationCacheConfig.setName("cabLocations");
        cabLocationCacheConfig.setMemoryStoreEvictionPolicy("LRU");
        cabLocationCacheConfig.setMaxEntriesLocalHeap(1000);
        cabLocationCacheConfig.setTimeToLiveSeconds(3600);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cabLocationCacheConfig);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    public CacheManager cacheManager(net.sf.ehcache.CacheManager ehcacheManager) {
        return new EhCacheCacheManager(ehcacheManager);
    }
}

