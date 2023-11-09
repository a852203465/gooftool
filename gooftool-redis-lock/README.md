# Redis分布式锁模块
 支持注解模式, 工具类模式

## 使用方式
### 引入依赖
```xml
<dependencies>
    <dependency>
        <groupId>cn.darkjrong</groupId>
        <artifactId>gooftool-redis-lock</artifactId>
    </dependency>
</dependencies>
```

### 1.2 工具类方式
```java
    @Autowired
    private RedisLockUtils redisLockUtils;
```

### 1.3 注解方式(在方法上增加注解@RedisLock)
 - 注解参数说明
   - prefix: 锁的key前缀
   - name: 锁名称
   - waitTime: 锁的等待时间, 单位：秒
   - leaseTime: 释放的时间, 单位：秒
   - timeUnit: 时间单位, 默认：秒

 - 如果`prefix`, `name`均为空, 会以`类名:方法名`为锁Key
 - 如果`prefix`不为空,`name`为空, 会以`prefix:类名.方法名`为锁Key

```java
import cn.darkjrong.redis.lock.annotation.RedisLock;

@RedisLock(prefix = "workflow", name = "startCompensate")
public void startCompensate(){
        a(){}
        }
```










































