/*
 * COPYRIGHT (C) 2023 Art AUTHORS(fxzcloud@gmail.com). ALL RIGHTS RESERVED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.art.common.redis.core.cache.support;

import com.art.common.redis.core.mq.client.RedisMQTemplate;
import com.art.common.redis.core.cache.properties.CacheRedisCaffeineProperties;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author fxz
 */
@Slf4j
public class RedisCaffeineCacheManager implements CacheManager {

	private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

	private CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

	private RedisTemplate<String, Object> stringKeyRedisTemplate;

	private RedisMQTemplate redisMQTemplate;

	private boolean dynamic;

	private Set<String> cacheNames;

	public RedisCaffeineCacheManager(CacheRedisCaffeineProperties cacheRedisCaffeineProperties,
			RedisTemplate redisTemplate, RedisMQTemplate redisMQTemplate) {
		this.cacheRedisCaffeineProperties = cacheRedisCaffeineProperties;
		this.stringKeyRedisTemplate = redisTemplate;
		this.redisMQTemplate = redisMQTemplate;
		this.dynamic = cacheRedisCaffeineProperties.isDynamic();
		this.cacheNames = cacheRedisCaffeineProperties.getCacheNames();
	}

	/**
	 * 根据名称获取缓存
	 * @param name the cache identifier (must not be {@code null}) 缓存名称
	 * @return 缓存
	 */
	@Override
	public Cache getCache(String name) {
		Cache cache = cacheMap.get(name);
		if (cache != null) {
			return cache;
		}
		if (!dynamic && !cacheNames.contains(name)) {
			return cache;
		}

		cache = new RedisCaffeineCache(name, redisMQTemplate, stringKeyRedisTemplate, caffeineCache(),
				cacheRedisCaffeineProperties);
		Cache oldCache = cacheMap.putIfAbsent(name, cache);
		log.debug("create cache instance, the cache name is : {}", name);
		return oldCache == null ? cache : oldCache;
	}

	/**
	 * 获取缓存名称集合
	 * @return 缓存名称集合
	 */
	@Override
	public Collection<String> getCacheNames() {
		return this.cacheNames;
	}

	public com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache() {
		Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();

		if (cacheRedisCaffeineProperties.getCaffeine().getExpireAfterAccess() > 0) {
			cacheBuilder.expireAfterAccess(cacheRedisCaffeineProperties.getCaffeine().getExpireAfterAccess(),
					TimeUnit.MILLISECONDS);
		}
		if (cacheRedisCaffeineProperties.getCaffeine().getExpireAfterWrite() > 0) {
			cacheBuilder.expireAfterWrite(cacheRedisCaffeineProperties.getCaffeine().getExpireAfterWrite(),
					TimeUnit.MILLISECONDS);
		}
		if (cacheRedisCaffeineProperties.getCaffeine().getInitialCapacity() > 0) {
			cacheBuilder.initialCapacity(cacheRedisCaffeineProperties.getCaffeine().getInitialCapacity());
		}
		if (cacheRedisCaffeineProperties.getCaffeine().getMaximumSize() > 0) {
			cacheBuilder.maximumSize(cacheRedisCaffeineProperties.getCaffeine().getMaximumSize());
		}
		if (cacheRedisCaffeineProperties.getCaffeine().getRefreshAfterWrite() > 0) {
			cacheBuilder.refreshAfterWrite(cacheRedisCaffeineProperties.getCaffeine().getRefreshAfterWrite(),
					TimeUnit.MILLISECONDS);
		}

		return cacheBuilder.build();
	}

	public void clearLocal(String cacheName, Object key) {
		Cache cache = null;

		if (StringUtils.hasText(cacheName)) {
			cache = cacheMap.get(cacheName);
		}

		if (Objects.isNull(cache)) {
			return;
		}

		RedisCaffeineCache redisCaffeineCache = (RedisCaffeineCache) cache;
		redisCaffeineCache.clearLocal(key);
	}

}
