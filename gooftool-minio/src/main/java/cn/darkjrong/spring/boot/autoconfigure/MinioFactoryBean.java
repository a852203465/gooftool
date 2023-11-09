package cn.darkjrong.spring.boot.autoconfigure;

import cn.darkjrong.minio.MinioTemplate;
import cn.hutool.core.lang.Assert;
import io.minio.MinioClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * minio工厂bean
 *
 * @author Rong.Jia
 * @date 2021/08/05 10:41:45
 */
public class MinioFactoryBean implements FactoryBean<MinioTemplate>, InitializingBean {

    private MinioTemplate minioTemplate;
    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    public MinioFactoryBean(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
        this.minioClient = minioClient;
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

        String bucketName = minioProperties.getBucketName();
        Assert.notBlank(bucketName, "'bucketName' cannot be empty");

        minioTemplate = new MinioTemplate(minioClient, minioProperties);
        if (!minioTemplate.bucketExists(bucketName)) {
            minioTemplate.makeBucket(bucketName);
        }
    }


}
