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

package com.art.common.dataPermission.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.art.common.dataPermission.aop.DataPermissionAnnotationAdvisor;
import com.art.common.dataPermission.db.DataPermissionDatabaseInterceptor;
import com.art.common.dataPermission.factory.DataPermissionRuleFactory;
import com.art.common.dataPermission.factory.DataPermissionRuleFactoryImpl;
import com.art.common.dataPermission.rule.DataPermissionRule;
import com.art.common.mp.core.utils.MyBatisUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 数据权限的自动配置类
 *
 * @author fxz
 */
@AutoConfiguration
public class DataPermissionAutoConfiguration {

	/**
	 * 注入数据权限规则工厂
	 * @param rules 容器中的数据权限规则类
	 */
	@Bean
	public DataPermissionRuleFactory dataPermissionRuleFactory(List<DataPermissionRule> rules) {
		return new DataPermissionRuleFactoryImpl(rules);
	}

	/**
	 * 添加拦截器 重写sql
	 */
	@Bean
	public DataPermissionDatabaseInterceptor dataPermissionDatabaseInterceptor(MybatisPlusInterceptor interceptor,
			List<DataPermissionRule> rules) {
		// 数据权限规则工厂接口
		DataPermissionRuleFactory ruleFactory = dataPermissionRuleFactory(rules);

		// 创建 DataPermissionDatabaseInterceptor 拦截器
		DataPermissionDatabaseInterceptor inner = new DataPermissionDatabaseInterceptor(ruleFactory);

		// 需要加在分页插件前面
		MyBatisUtils.addInterceptor(interceptor, inner, 0);
		return inner;
	}

	/**
	 * 数据权限aop处理
	 */
	@Bean
	public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor() {
		return new DataPermissionAnnotationAdvisor();
	}

}
