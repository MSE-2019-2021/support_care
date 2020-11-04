package uc.dei.mse.supportivecare.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;
import uc.dei.mse.supportivecare.GeneratedByJHipster;

@Configuration
@EnableCaching
@GeneratedByJHipster
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration<Object, Object> caffeineConfiguration = new CaffeineConfiguration<>();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, uc.dei.mse.supportivecare.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, uc.dei.mse.supportivecare.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, uc.dei.mse.supportivecare.domain.User.class.getName());
            createCache(cm, uc.dei.mse.supportivecare.domain.Authority.class.getName());
            createCache(cm, uc.dei.mse.supportivecare.domain.User.class.getName() + ".authorities");
            createCache(cm, uc.dei.mse.supportivecare.domain.Administration.class.getName());
            createCache(cm, uc.dei.mse.supportivecare.domain.Administration.class.getName() + ".drugs");
            createCache(cm, uc.dei.mse.supportivecare.domain.Notice.class.getName());
            createCache(cm, uc.dei.mse.supportivecare.domain.Drug.class.getName());
            createCache(cm, uc.dei.mse.supportivecare.domain.Drug.class.getName() + ".notices");
            createCache(cm, uc.dei.mse.supportivecare.domain.Drug.class.getName() + ".therapeuticRegimes");
            createCache(cm, uc.dei.mse.supportivecare.domain.Treatment.class.getName());
            createCache(cm, uc.dei.mse.supportivecare.domain.Treatment.class.getName() + ".therapeuticRegimes");
            createCache(cm, uc.dei.mse.supportivecare.domain.TherapeuticRegime.class.getName());
            createCache(cm, uc.dei.mse.supportivecare.domain.TherapeuticRegime.class.getName() + ".drugs");
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
