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

package com.art.system.service;

import com.art.system.api.app.dto.AppDTO;
import com.art.system.api.app.dto.AppPageDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 系统应用表
 *
 * @author fxz
 * @date 2022-09-12
 */
public interface AppService {

	/**
	 * 添加
	 */
	Boolean addApp(AppDTO appDTO);

	/**
	 * 修改
	 */
	Boolean updateApp(AppDTO appDTO);

	/**
	 * 分页
	 */
	IPage<AppDTO> pageApp(AppPageDTO appPageDTO);

	/**
	 * 获取单条
	 */
	AppDTO findById(Long id);

	/**
	 * 获取全部
	 */
	List<AppDTO> findAll();

	/**
	 * 删除
	 */
	Boolean deleteApp(Long id);

}