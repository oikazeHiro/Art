/*
 * COPYRIGHT (C) 2022 Art AUTHORS(fxzcloud@gmail.com). ALL RIGHTS RESERVED.
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

package com.art.system.dao.mysql;

import com.art.system.api.user.dto.SystemUserPageDTO;
import com.art.system.dao.dataobject.SystemUserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2021-11-28 18:21
 */
@Mapper
public interface UserMapper extends BaseMapper<SystemUserDO> {

	/**
	 * 分页查找用户详细信息
	 * @param page 分页对象
	 * @param user 用户对象，用于传递查询条件
	 * @return Ipage
	 */
	@SuppressWarnings("all")
	Page<SystemUserDO> findUserDetailPage(Page page, @Param("user") SystemUserPageDTO user);

	/**
	 * 根据用户id获取用户信息
	 */
	SystemUserDO getUserById(Long id);

	/**
	 * 通过用户名查找用户信息
	 */
	SystemUserDO findByName(String username);

	/**
	 * 通过手机号查找用户信息
	 * @param mobile 手机号
	 * @return 用户信息
	 */
	SystemUserDO findByMobile(String mobile);

}
