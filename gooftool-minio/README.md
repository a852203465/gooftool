# Minio模块
    MINIO Java SDK 封装, 并制作成Spring-boot starter

## 使用方式
### 2.引入依赖
```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>gooftool-minio</artifactId>
</dependency>
```

### 3.配置参数(application.properties)  yml配置

```yaml
wit:
  minio:
    # 必须为true
    enabled: true
    endpoint: http:192.168.42.131:9000
    access-key: minio
    secret-key: minio123
    bucket-name: data
```
### 4. API 注入
```java
        
    @Autowired
    private MinioTemplate minioTemplate;            

```











