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

import com.art.system.core.bo.DeptBO;
import com.art.system.dao.dataobject.DeptDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022-02-27 18:34
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptDO> {

	/**
	 * 获取部门树
	 */
	DeptBO getDeptTree();

	DeptBO getDeptsByParentId(@Param("pId") Long pId);

	String getDeptNameByUserId(@Param("deptId") Long deptId);

}
