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

package com.art.common.hazelcast.core.cache;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2023/3/23 16:16
 */
public abstract class AbstractCacheManager<T> implements DistributedCacheManager<T> {

	private final DistributedCacheManager<T> distributedCacheManager;

	protected AbstractCacheManager(DistributedCacheProvider distributedCacheProvider) {
		this.distributedCacheManager = distributedCacheProvider.getOrCreate(getClass().getName());
	}

	/**
	 * 获取指定键的值
	 * @param key key
	 * @return value
	 */
	@Override
	public T get(String key) {
		return distributedCacheManager.get(key);
	}

	/**
	 * 为指定key设置过期时间
	 * @param key 指定key
	 * @param seconds 过期时间，以秒为单位
	 * @return true or false
	 */
	@Override
	public boolean touch(String key, int seconds) {
		return distributedCacheManager.touch(key, seconds);
	}

	/**
	 * 添加，有值则不替换
	 * @param key key
	 * @param data data
	 * @return true or false
	 */
	@Override
	public boolean add(String key, T data) {
		return distributedCacheManager.add(key, data);
	}

	/**
	 * 添加，有值则不替换
	 * @param key key
	 * @param data data
	 * @param seconds 过期时间，单位秒
	 * @return true or false
	 */
	@Override
	public boolean add(String key, T data, int seconds) {
		return distributedCacheManager.add(key, data, seconds);
	}

	/**
	 * 添加，有值则替换
	 * @param key key
	 * @param data data
	 * @return true or false
	 */
	@Override
	public boolean set(String key, T data) {
		return distributedCacheManager.set(key, data);
	}

	/**
	 * 添加，有值则替换
	 * @param key key
	 * @param data data
	 * @param seconds 过期时间，单位秒
	 * @return true or false
	 */
	@Override
	public boolean set(String key, T data, int seconds) {
		return distributedCacheManager.set(key, data, seconds);
	}

	/**
	 * 删除
	 * @param key
	 * @return true or false
	 */
	@Override
	public boolean delete(String key) {
		return distributedCacheManager.delete(key);
	}

	/**
	 * 替换，数据不存在返回false
	 * @param key key
	 * @param data data
	 * @return true or false
	 */
	@Override
	public boolean replace(String key, T data) {
		return distributedCacheManager.replace(key, data);
	}

	/**
	 * 替换，数据不存在返回false
	 * @param key key
	 * @param data data
	 * @param seconds 过期时间，单位秒
	 * @return true or false
	 */
	@Override
	public boolean replace(String key, T data, int seconds) {
		return distributedCacheManager.replace(key, data, seconds);
	}

	/**
	 * 加锁
	 * @param key key
	 * @param seconds 过期时间，单位秒
	 */
	@Override
	public void lock(String key, long seconds) {
		distributedCacheManager.lock(key, seconds);
	}

	/**
	 * 解锁
	 * @param key key
	 */
	@Override
	public void unlock(String key) {
		distributedCacheManager.unlock(key);
	}

	/**
	 * 无时间限制锁
	 * @param key key
	 */
	@Override
	public void lockInfinite(String key) {
		distributedCacheManager.lockInfinite(key);
	}

	/**
	 * 尝试加锁
	 * @param key key
	 * @return true or false
	 */
	@Override
	public boolean tryLock(String key) {
		return distributedCacheManager.tryLock(key);
	}

	/**
	 * 尝试加锁
	 * @param key key
	 * @param seconds 尝试加锁时间
	 * @return true or false
	 */
	@Override
	public boolean tryLock(String key, long seconds) {
		return distributedCacheManager.tryLock(key, seconds);
	}

	/**
	 * 尝试加锁
	 * @param key key
	 * @param seconds 尝试加锁时间
	 * @param leaseSeconds 加锁时间
	 * @return true or false
	 */
	@Override
	public boolean tryLock(String key, long seconds, long leaseSeconds) {
		return distributedCacheManager.tryLock(key, seconds, leaseSeconds);
	}

	/**
	 * 是否加锁成功
	 * @param key key
	 * @return true or false
	 */
	@Override
	public boolean isLocked(String key) {
		return distributedCacheManager.isLocked(key);
	}

}
