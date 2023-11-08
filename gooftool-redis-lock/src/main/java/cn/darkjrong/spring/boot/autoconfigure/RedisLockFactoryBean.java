package cn.darkjrong.spring.boot.autoconfigure;

import cn.darkjrong.redis.lock.RedisLockUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Redis 锁工厂Bean
 *
 * @author Rong.Jia
 * @date 2023/10/11
 */
public class RedisLockFactoryBean implements FactoryBean<RedisLockUtils>, InitializingBean {

    private RedisLockUtils redisLockUtils;
    private final RedissonClient redissonClient;

    public RedisLockFactoryBean(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean isSingleton() {
        return Boolean.TRUE;
    }

    @Override
    public RedisLockUtils getObject() {
        return this.redisLockUtils;
    }

    @Override
    public Class<?> getObjectType() {
        return RedisLockUtils.class;
    }

    @Override
    public void afterPropertiesSet() {
        this.redisLockUtils = new RedisLockUtils(redissonClient);
    }
}
