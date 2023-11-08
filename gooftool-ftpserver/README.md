# ftpserver模块
    apache ftpserver封装 

## 使用方式
### 引入依赖
```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>gooftool-ftpserver</artifactId>
</dependency>
```

### yml配置
必须配置enabled: true，否则默认false不起作用
```yaml
  goof:
    ftpserver:
      enabled: true
      host: 192.168.205.105
      port: 21
      activePort: 20
      passivePorts: 6000-6008
      username: admin
      password: 123456
```

### 文件接收，实现AlarmCallBack 
```java
@Slf4j
@Component
public class AlarmCallBackImpl implements AlarmCallBack {

    @Override
    public void invoke(File file, String s) {

        log.info("invoke file {}, host {}", file.getName(), s);
    }
}
```
### 增加命令
继承Apache MINA的命令
```java
@Slf4j
@Component("ACCT")
public class ACCTCommand extends ACCT {



}
```

































