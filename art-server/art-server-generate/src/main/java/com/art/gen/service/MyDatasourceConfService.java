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

package com.art.gen.service;

import com.art.gen.dao.dataobject.DatasourceConfDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022-03-31 11:42
 */
public interface MyDatasourceConfService {

	/**
	 * 分页查询数据源管理信息
	 * @param page 分页参数
	 * @param emptyWrapper 查询条件
	 * @return
	 */
	Page<DatasourceConfDO> pageDataSourceConf(Page<DatasourceConfDO> page, QueryWrapper<DatasourceConfDO> emptyWrapper);

	/**
	 * 动态添加数据源
	 * @param datasourceConf 数据源信息
	 */
	Boolean addDs(DatasourceConfDO datasourceConf);

	/**
	 * 根据id查询数据源信息
	 * @param id 数据源id
	 * @return 数据源信息
	 */
	DatasourceConfDO findBtId(Long id);

	/**
	 * 修改数据源信息
	 * @param datasourceConf 数据源信息
	 * @return ture Or false
	 */
	Boolean updateDsConf(DatasourceConfDO datasourceConf);

	Boolean delete(Long id);

	/**
	 * 查询所有数据源信息
	 */
	List<DatasourceConfDO> listDs();

}
