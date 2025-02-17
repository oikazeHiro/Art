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

package com.art.system.api.file.dto;

import com.art.common.core.model.BasePageEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022/11/23 19:08
 */
@Data
public class FilePageDTO extends BasePageEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;

	private String fileName;

	private String bucketName;

	private String original;

	private String type;

	private Long fileSize;

	private String delFlag;

}
