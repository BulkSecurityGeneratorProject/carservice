package com.vencislav.carserviceapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.vencislav.carserviceapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Owner.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Owner.class.getName() + ".cars", jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Car.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Car.class.getName() + ".visits", jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Mem.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Visit.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Part.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Work.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Worker.class.getName(), jcacheConfiguration);
            cm.createCache(com.vencislav.carserviceapp.domain.Worker.class.getName() + ".visits", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
