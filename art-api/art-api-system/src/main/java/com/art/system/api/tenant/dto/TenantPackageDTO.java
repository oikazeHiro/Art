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

package com.art.system.api.tenant.dto;

import com.art.common.core.constant.GlobalStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022/11/26 15:24
 */
@Data
public class TenantPackageDTO implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 套餐名
	 */
	private String name;

	/**
	 * 套餐状态
	 * <p>
	 * 枚举 {@link GlobalStatusEnum}
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 套餐关联的菜单编号
	 */
	private String menuIds;

	/**
	 * 创建者
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	private String updateBy;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

	/**
	 * 逻辑删除标志
	 */
	private Boolean delFlag;

}
