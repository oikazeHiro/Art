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

package com.art.system.api.dict.dto;

import com.art.common.mp.core.base.BaseCreateEntity;
import lombok.Data;

/**
 * 字典表
 *
 * @author fxz
 * @date 2022-04-04
 */
@Data
public class DictDTO extends BaseCreateEntity {

	private static final long serialVersionUID = -7062810411163801969L;

	private Long id;

	private String type;

	private String description;

	private String remark;

	private String systemFlag;

	private String delFlag;

}