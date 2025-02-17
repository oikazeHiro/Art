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

package com.art.common.security.core.service;

import com.art.common.security.core.model.ArtAuthUser;
import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author fxz
 */
public interface ArtUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	boolean support(String clientId);

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	@Override
	default int getOrder() {
		return 0;
	}

	/**
	 * 通过用户实体查询
	 * @param artAuthUser user
	 * @return 用户信息
	 */
	default UserDetails loadUserByUser(ArtAuthUser artAuthUser) {
		return this.loadUserByUsername(artAuthUser.getUsername());
	}

	/**
	 * 根据手机号查询用户信息
	 * @param mobile 手机号
	 * @return 用户信息
	 */
	UserDetails loadUserByMobile(String mobile);

}
