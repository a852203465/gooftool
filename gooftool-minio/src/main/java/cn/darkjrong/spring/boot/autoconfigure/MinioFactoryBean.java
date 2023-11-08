package cn.darkjrong.spring.boot.autoconfigure;

import cn.darkjrong.minio.MinioTemplate;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;


/**
 * minio工厂bean
 *
 * @author Rong.Jia
 * @date 2021/08/05 10:41:45
 */
public class MinioFactoryBean implements FactoryBean<MinioTemplate>, BeanFactoryAware, InitializingBean {

    private MinioTemplate minioTemplate;
    private final MinioProperties minioProperties;
    private BeanFactory beanFactory;

    public MinioFactoryBean(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Override
    public MinioTemplate getObject() {
        return minioTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return MinioTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        String endpoint = minioProperties.getEndpoint();
        String secretKey = minioProperties.getSecretKey();
        String accessKey = minioProperties.getAccessKey();
        String bucketName = minioProperties.getBucketName();

        Assert.notBlank(endpoint, "'endpoint' cannot be empty");
        Assert.notBlank(secretKey, "'secretKey' cannot be empty");
        Assert.notBlank(accessKey, "'accessKey' cannot be empty");
        Assert.notBlank(bucketName, "'bucketName' cannot be empty");

        MinioClient.Builder builder = MinioClient.builder().endpoint(new URL(endpoint))
                .credentials(accessKey, secretKey);

        if (isConfiguredProxy()) {
            builder.httpClient(createHttpClient());
        }

        MinioClient minioClient = builder.build();
        minioClient.setTimeout(minioProperties.getConnectTimeout(), minioProperties.getWriteTimeout(), minioProperties.getReadTimeout());

        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName(MinioClient.class.getName());
        beanDefinition.setRole(BeanDefinition.ROLE_SUPPORT);
        ((BeanDefinitionRegistry)beanFactory).registerBeanDefinition(MinioClient.class.getName(), beanDefinition);

        minioTemplate = new MinioTemplate(minioClient, minioProperties);
        if (!minioTemplate.bucketExists(bucketName)) {
            minioTemplate.makeBucket(bucketName);
        }
    }

    /**
     * 是否配置代理
     * @return {@link Boolean}
     */
    private Boolean isConfiguredProxy() {
        String httpHost = System.getProperty("http.proxyHost");
        String httpPort = System.getProperty("http.proxyPort");
        return StrUtil.isAllNotBlank(httpHost, httpPort);
    }

    /**
     * 创建http客户端
     *
     * @return {@link OkHttpClient}
     */
    private OkHttpClient createHttpClient() {
        String httpHost = System.getProperty("http.proxyHost");
        String httpPort = System.getProperty("http.proxyPort");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (httpHost != null) {
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpHost, Integer.parseInt(httpPort))));
        }
        return builder.build();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
