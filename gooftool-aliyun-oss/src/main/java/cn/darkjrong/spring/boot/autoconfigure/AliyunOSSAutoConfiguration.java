package cn.darkjrong.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云 OSS 配置类
 *
 * @author Rong.Jia
 * @date 2021/01/28 16:18:22
 */
@Configuration
@ComponentScan("cn.darkjrong.aliyun.oss")
@ConditionalOnProperty(prefix = "goof.aliyun.oss", name = "enabled", havingValue = "true")
@ConditionalOnClass({AliyunOSSProperties.class})
@EnableConfigurationProperties({AliyunOSSProperties.class})
public class AliyunOSSAutoConfiguration {

    private final AliyunOSSProperties properties;

    public AliyunOSSAutoConfiguration(AliyunOSSProperties properties) {
        this.properties = properties;
    }

    @Bean
    public AliyunOSSFactoryBean ossClientFactoryBean() {
        final AliyunOSSFactoryBean factoryBean = new AliyunOSSFactoryBean();
        factoryBean.setProperties(this.properties);
        return factoryBean;
    }

}
