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

package com.art.scheduled.controller;

import com.art.common.core.model.PageResult;
import com.art.common.core.model.Result;
import com.art.scheduled.core.dto.JobLogDTO;
import com.art.scheduled.core.dto.JobLogPageDTO;
import com.art.scheduled.service.JobLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务调度日志
 *
 * @author fxz
 * @date 2022-04-03
 */
@RestController
@RequestMapping("/jobLog")
@RequiredArgsConstructor
public class JobLogController {

	private final JobLogService jobLogService;

	@GetMapping(value = "/findById")
	public Result<JobLogDTO> findById(Long id) {
		return Result.success(jobLogService.findById(id));
	}

	@GetMapping(value = "/page")
	public Result<PageResult<JobLogDTO>> page(JobLogPageDTO pageDTO) {
		return Result.success(PageResult.success(jobLogService.page(pageDTO)));
	}

}