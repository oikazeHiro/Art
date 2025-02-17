/*
 * COPYRIGHT (C) 2023 Art AUTHORS(fxzcloud@gmail.com). ALL RIGHTS RESERVED.
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

package com.art.common.file.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.art.common.file.core.client.ftp.FtpFileStorage;
import com.art.common.file.core.client.ftp.FtpProperties;
import com.art.common.file.core.client.local.LocalFileStorage;
import com.art.common.file.core.client.oss.OssFileStorage;
import com.art.common.file.core.client.oss.OssProperties;
import com.art.common.file.core.propertie.FileStorageProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author fxz
 */
@EnableConfigurationProperties(FileStorageProperties.class)
@AutoConfiguration
public class FileStorageAutoConfiguration {

	@ConditionalOnProperty(prefix = "file.storage.local", name = "enable", havingValue = "true", matchIfMissing = true)
	public static class LocalFileStorageAutoConfiguration {

		@Bean
		public LocalFileStorage localFileStorage(FileStorageProperties properties) {
			return new LocalFileStorage(properties);
		}

	}

	@ConditionalOnProperty(prefix = "file.storage.ftp", name = "enable", havingValue = "true", matchIfMissing = false)
	public static class FtpFileStorageAutoConfiguration {

		@Bean
		public FtpFileStorage ftpFileStorage(FileStorageProperties fileStorageProperties) {
			FtpProperties properties = fileStorageProperties.getFtp();
			Ftp ftp = new Ftp(properties.getHost(), properties.getPort(), properties.getUsername(),
					properties.getPassword(), CharsetUtil.CHARSET_UTF_8, null, null, FtpMode.Passive);

			return new FtpFileStorage(ftp, properties.getBasePath());
		}

	}

	@ConditionalOnProperty(prefix = "file.storage.oss", name = "enable", havingValue = "true", matchIfMissing = true)
	public static class OssFileStorageAutoConfiguration {

		@Bean
		public OssFileStorage ossFileStorage(AmazonS3 amazonS3) {
			return new OssFileStorage(amazonS3);
		}

		@Bean
		public AmazonS3 amazonS3(FileStorageProperties fileStorageProperties) {
			OssProperties properties = fileStorageProperties.getOss();
			// 客户端配置
			ClientConfiguration clientConfiguration = new ClientConfiguration();
			clientConfiguration.setProtocol(Protocol.HTTP);

			// 端点配置
			AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
					properties.getEndpoint(), Region.getRegion(Regions.CN_NORTH_1).getName());

			// 凭证配置
			AWSCredentials awsCredentials = new BasicAWSCredentials(properties.getAccessKey(),
					properties.getSecretKey());

			return AmazonS3Client.builder()
				.withEndpointConfiguration(endpointConfiguration)
				.withClientConfiguration(clientConfiguration)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withPathStyleAccessEnabled(properties.getPathStyleAccess())
				.build();
		}

	}

}
