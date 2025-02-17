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

package com.art.common.hazelcast.core.base;

import java.util.concurrent.locks.Lock;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2023/3/27 09:10
 */
public interface DistributedBaseFactory {

	/**
	 * 获取分布式集合
	 * @param name 分布式集合的名称
	 * @return 分布式集合
	 */
	DistributedCountDownLatch getCountDownLatch(String name);

	/**
	 * 获取分布式集合
	 * @param name 分布式集合的名称
	 * @return 分布式集合
	 */
	Lock getLock(String name);

	/**
	 * 获取分布式集合
	 * @param name 分布式集合的名称
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return 分布式集合
	 */
	<K, V> DistributedMap<K, V> getMap(String name);

	/**
	 * 获取分布式集合
	 * @param name 分布式集合的名称
	 * @param <T> 泛型
	 * @return 分布式集合
	 */
	<T> DistributedQueue<T> getQueue(String name);

	/**
	 * 获取分布式集合
	 * @param name 分布式集合的名称
	 * @param <T> 泛型
	 * @return 分布式集合
	 */
	<T> DistributedSet<T> getSet(String name);

}
