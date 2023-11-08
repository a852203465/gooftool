# ftp客户端模块
    commons-net封装

##使用方式
### 引入依赖
```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>gooftool-ftpclient</artifactId>
</dependency>
```
### 配置说明
yml配置，必须配置enabled: true，否则默认false不起作用
```yaml
goof:
  ftpclient:
    enabled: true
    host: 192.168.10.107
    port: 21
    base-dir: /data
    username: admin
    passive-mode: false
    password: 123456
    buffer-size: 2048
    encoding: UTF-8
    connect-timeout: 6000
    pool:
      max-active: 10
      max-idle: 5
      max-wait: 1000
      min-idle: 1
```

### API 操作
```java

@Autowired
private FtpClientTemplate ftpClientTemplate;
```
































