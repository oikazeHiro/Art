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

package com.art.gen.service.impl;

import cn.hutool.core.lang.Assert;
import com.art.gen.dao.dataobject.DatasourceConfDO;
import com.art.gen.dao.mysql.DatasourceConfMapper;
import com.art.gen.service.MyDatasourceConfService;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据源表
 *
 * @author fxz
 * @date 2022-03-31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MyDatasourceConfServiceImpl implements MyDatasourceConfService {

	private final StringEncryptor stringEncryptor;

	private final DataSource dataSource;

	private final DefaultDataSourceCreator dataSourceCreator;

	private final DatasourceConfMapper datasourceConfMapper;

	/**
	 * 分页查询数据源管理信息
	 * @param page 分页参数
	 * @param emptyWrapper 查询条件
	 * @return
	 */
	@Override
	public Page<DatasourceConfDO> pageDataSourceConf(Page<DatasourceConfDO> page,
			QueryWrapper<DatasourceConfDO> emptyWrapper) {
		return datasourceConfMapper.selectPage(page, emptyWrapper);
	}

	/**
	 * 动态添加数据源
	 * @param datasourceConf 数据源信息
	 */
	@Override
	public Boolean addDs(DatasourceConfDO datasourceConf) {
		// 校验配置合法性
		Assert.isTrue(checkDataSource(datasourceConf), "数据源信息错误，连接失败!");

		// 添加动态数据源
		addDynamicDataSource(datasourceConf);

		// 更新数据库信息，加密数据库密码
		datasourceConf.setPassword(stringEncryptor.encrypt(datasourceConf.getPassword()));

		return datasourceConfMapper.insert(datasourceConf) > 0;
	}

	/**
	 * 修改数据源信息
	 * @param datasourceConf 数据源信息
	 * @return ture Or false
	 */
	@Override
	public Boolean updateDsConf(DatasourceConfDO datasourceConf) {
		// 校验配置合法性
		Assert.isTrue(checkDataSource(datasourceConf), "数据源信息错误，连接失败!");

		// 移除数据源
		DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
		ds.removeDataSource(datasourceConf.getName());

		// 添加数据源
		addDynamicDataSource(datasourceConf);

		// 密码加密并且更新数据源信息
		datasourceConf.setPassword(stringEncryptor.encrypt(datasourceConf.getPassword()));
		return datasourceConfMapper.updateById(datasourceConf) > 0;
	}

	/**
	 * 删除数据源信息
	 * @param id 数据源主键
	 */
	@Override
	public Boolean delete(Long id) {
		// 查询数据源信息
		DatasourceConfDO datasourceConf = datasourceConfMapper.selectById(id);

		// 移除数据源
		DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
		ds.removeDataSource(datasourceConf.getName());

		// 删除数据源信息
		return datasourceConfMapper.deleteById(id) > 0;
	}

	/**
	 * 查询所有数据源信息
	 */
	@Override
	public List<DatasourceConfDO> listDs() {
		return datasourceConfMapper.selectList(Wrappers.emptyWrapper());
	}

	/**
	 * 根据id查询数据源信息
	 * @param id 数据源id
	 * @return 数据源信息
	 */
	@Override
	public DatasourceConfDO findBtId(Long id) {
		return datasourceConfMapper.selectById(id);
	}

	/**
	 * 添加动态数据源 通用数据源会根据maven中配置的连接池根据顺序依次选择。 默认的顺序为druid>hikaricp>beecp>dbcp>spring basic
	 * @param conf 数据源信息
	 */
	public void addDynamicDataSource(DatasourceConfDO conf) {
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		dataSourceProperty.setPoolName(conf.getName());
		dataSourceProperty.setUrl(conf.getUrl());
		dataSourceProperty.setUsername(conf.getUsername());
		dataSourceProperty.setPassword(conf.getPassword());
		dataSourceProperty.setLazy(true);

		DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
		DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);

		ds.addDataSource(conf.getName(), dataSource);
	}

	/**
	 * 校验数据源配置是否有效
	 * @param conf 数据源信息
	 * @return 有效/无效
	 */
	public Boolean checkDataSource(DatasourceConfDO conf) {
		try {
			DriverManager.getConnection(conf.getUrl(), conf.getUsername(), conf.getPassword());
		}
		catch (SQLException e) {
			log.error("数据源配置 {} , 获取链接失败", conf.getName(), e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}