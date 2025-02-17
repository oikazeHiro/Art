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

package com.art.scheduled.core.job;

import com.art.common.quartz.core.annotation.ArtQuartzJob;
import com.art.common.quartz.core.job.ArtJob;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022/12/7 11:53
 */
@Slf4j
@ArtQuartzJob(log = true, name = "demoJob")
public class DemoJob implements ArtJob {

	@Override
	public void execute(String parameter) {
		log.info("demoJob执行,方法参数:{},时间:{}", parameter, LocalDateTime.now());
	}

}
