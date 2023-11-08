# hdfs模块
    封装 hdfs 并制作Spring starter

## 使用方式
```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>gooftool-hdfs</artifactId>
</dependency>
```

### 配置参数(application.properties)  yml配置
```yaml
  goof:
    hdfs:
      enabled: true # 必须为true, 才能生效
      namespace: /data
      replication: 1
      username: Mr.J
      server-address: hdfs://localhost:9000
```
### API 注入
```java

@Autowired
private HdfsTemplate hdfsTemplate;
```











