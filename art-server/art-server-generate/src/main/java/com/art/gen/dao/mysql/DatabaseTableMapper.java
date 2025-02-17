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

package com.art.gen.dao.mysql;

import com.art.gen.dao.dataobject.DatabaseColumnDO;
import com.art.gen.dao.dataobject.DatabaseTableDO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022-03-03 15:55
 */
@Mapper
public interface DatabaseTableMapper {

	/**
	 * 获取表基本信息
	 */
	@Select("select table_name, engine, table_comment, create_time from information_schema.tables"
			+ "	where table_schema = (select database()) and table_name = #{tableName}")
	DatabaseTableDO findByTableName(@Param("tableName") String tableName);

	/**
	 * 获取表的列信息
	 */
	@Select("select column_name, data_type, column_comment, column_key from information_schema.columns"
			+ " where table_name = #{tableName} and table_schema = (select database()) order by ordinal_position")
	List<DatabaseColumnDO> findColumnByTableName(String tableName);

	/**
	 * 分页查询基础表信息
	 */
	@Select("select table_name tableName,engine,table_comment tableComment,create_time createTime from (select table_name , engine, table_comment , create_time  from information_schema.tables"
			+ " where table_schema = (select database())) as t ${ew.customSqlSegment}")
	Page<DatabaseTableDO> page(Page<DatabaseTableDO> page, @Param(Constants.WRAPPER) Wrapper<?> wrapper);

}
