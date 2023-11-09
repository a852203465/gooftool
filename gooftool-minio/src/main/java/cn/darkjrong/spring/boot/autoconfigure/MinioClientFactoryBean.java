package cn.darkjrong.spring.boot.autoconfigure;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;


/**
 * Minio 客户端工厂 Bean
 *
 * @author Rong.Jia
 * @date 2023/11/09
 */
public class MinioClientFactoryBean implements FactoryBean<MinioClient>, InitializingBean {

    private MinioClient minioClient;
    private final MinioProperties minioProperties;

    public MinioClientFactoryBean(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Override
    public MinioClient getObject() {
        return minioClient;
    }

    @Override
    public Class<?> getObjectType() {
        return MinioClient.class;
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

        Assert.notBlank(endpoint, "'endpoint' cannot be empty");
        Assert.notBlank(secretKey, "'secretKey' cannot be empty");
        Assert.notBlank(accessKey, "'accessKey' cannot be empty");
        endpoint = StrUtil.startWith(endpoint, "http") ? endpoint : "http://" + endpoint;

        MinioClient.Builder builder = MinioClient.builder().endpoint(new URL(endpoint))
                .credentials(accessKey, secretKey);

        if (isConfiguredProxy()) {
            builder.httpClient(createHttpClient());
        }
        MinioClient client = builder.build();
        client.setTimeout(minioProperties.getConnectTimeout(), minioProperties.getWriteTimeout(), minioProperties.getReadTimeout());
        this.minioClient = client;
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

}
