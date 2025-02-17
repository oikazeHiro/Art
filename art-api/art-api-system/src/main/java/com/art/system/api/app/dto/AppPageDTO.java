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

package com.art.system.api.app.dto;

import com.art.common.core.model.BasePageEntity;
import com.art.common.core.constant.ValidationGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022/11/23 00:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "系统应用分页查询")
public class AppPageDTO extends BasePageEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	@Schema(description = "主键")
	@NotNull(message = "主键不能为空!", groups = ValidationGroup.update.class)
	private Long id;

	@Schema(description = "应用名称")
	@Size(min = 1, max = 10, message = "应用名称长度在1-10之间!", groups = ValidationGroup.add.class)
	@NotNull(message = "应用名称不能为空!", groups = ValidationGroup.add.class)
	private String name;

	@Schema(description = "应用编码")
	@Size(min = 1, max = 10, message = "应用编码长度在1-10之间!", groups = ValidationGroup.add.class)
	@NotNull(message = "应用编码不能为空!", groups = ValidationGroup.add.class)
	private String code;

	@Schema(description = "图标")
	@NotNull(message = "图标不能为空!", groups = ValidationGroup.add.class)
	private String icon;

	@Schema(description = "排序")
	@NotNull(message = "排序不能为空!", groups = ValidationGroup.add.class)
	private Integer sort;

}
