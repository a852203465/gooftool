# Minio模块
    MINIO Java SDK封装, 并制作成Spring Starter

## 使用方式
### 引入依赖
```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>gooftool-minio</artifactId>
</dependency>
```
### 配置参数(application.properties)  yml配置
```yaml
goof:
  minio:
    # 必须为true
    enabled: true
    endpoint: http:192.168.42.131:9000
    access-key: minio
    secret-key: minio123
    bucket-name: data
```
### API 注入
```java
    @Autowired
    private MinioTemplate minioTemplate;            

```
### 问题处理
#### 启动报错
 - 依赖版本问题
```java
An attempt was made to call a method that does not exist. The attempt was made from the following location:

    io.minio.S3Base.<clinit>(S3Base.java:105)

The following method did not exist:

    okhttp3.RequestBody.create([BLokhttp3/MediaType;)Lokhttp3/RequestBody;

The method's class, okhttp3.RequestBody, is available from the following locations:

    jar:file:/G:/repository/m2/com/squareup/okhttp3/okhttp/3.14.9/okhttp-3.14.9.jar!/okhttp3/RequestBody.class

The class hierarchy was loaded from the following locations:

    okhttp3.RequestBody: file:/G:/repository/m2/com/squareup/okhttp3/okhttp/3.14.9/okhttp-3.14.9.jar
```
 - 解决方式
在项目的`<properties>`标签增加版本
```java
    <properties>
        <okhttp3.version>4.11.0</okhttp3.version>
    </properties>
```









