package cn.darkjrong.spring.boot.autoconfigure;

import cn.darkjrong.ftpclient.FtpClientTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * ftp客户端工厂Bean
 *
 * @author Rong.Jia
 * @date 2022/01/06
 */
@Slf4j
public class FtpClientFactoryBean implements FactoryBean<FtpClientTemplate>, InitializingBean, DisposableBean {

    private FtpClientTemplate ftpClientTemplate;
    private final FtpClientProperties properties;
    private final GenericObjectPool<FTPClient> pool;

    public FtpClientFactoryBean(FtpClientProperties properties, GenericObjectPool<FTPClient> pool) {
        this.properties = properties;
        this.pool = pool;
    }

    @Override
    public FtpClientTemplate getObject() {
        return this.ftpClientTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return FtpClientTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() {
        pool.clear();
    }

    @Override
    public void afterPropertiesSet() {

        Assert.notNull(this.properties, "'properties' must be not null");
        Assert.notNull(this.properties.getUsername(), "'username' must be not null");
        Assert.notNull(this.properties.getPassword(), "'password' must be not null");
        Assert.notNull(this.properties.getPort(), "port' must be not null");
        Assert.notNull(this.properties.getHost(), "host' must be not null");

        ftpClientTemplate = new FtpClientTemplate(properties, pool);
    }
























}
