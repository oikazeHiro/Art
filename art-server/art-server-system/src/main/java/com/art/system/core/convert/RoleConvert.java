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

package com.art.system.core.convert;

import com.art.system.api.role.dto.RoleDTO;
import com.art.system.core.bo.RoleBO;
import com.art.system.dao.dataobject.RoleDO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022/11/26 11:57
 */
@Mapper
public interface RoleConvert {

	RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

	RoleDO convert(RoleDTO roleDTO);

	RoleDTO convert(RoleDO roleDO);

	RoleDTO convert(RoleBO roleBO);

	List<RoleDTO> convert(List<RoleDO> roleDO);

	Page<RoleDTO> convert(Page<RoleDO> roleDO);

}
