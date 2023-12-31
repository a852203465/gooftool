package cn.darkjrong.spring.boot.autoconfigure;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * minio自动配置
 *
 * @author Rong.Jia
 * @date 2021/08/03 22:32:11
 */
@Configuration
@ComponentScan("cn.darkjrong.minio")
@EnableConfigurationProperties({MinioProperties.class})
@ConditionalOnProperty(prefix = "goof.minio", name = "enabled", havingValue = "true")
public class MinioAutoConfiguration {

    private final MinioProperties minioProperties;

    public MinioAutoConfiguration(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Bean
    public MinioFactoryBean minioFactoryBean(MinioClient minioClient) {
        return new MinioFactoryBean(minioClient, minioProperties);
    }

    @Bean
    public MinioClientFactoryBean minioClientFactoryBean() {
        return new MinioClientFactoryBean(minioProperties);
    }


}
