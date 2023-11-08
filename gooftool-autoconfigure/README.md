# 自动配置模块

## 使用方式
### 引入依赖
```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>gooftool-autoconfigure</artifactId>
</dependency>
```

### 配置参数(application.properties)yml配置
#### 线程池配置
```yaml
goof:
  task:
    pool:
      # 必须为:true
      enabled: true
      core-pool-size: 8
      max-pool-size: 16
      keep-alive-seconds: 30
      queue-capacity: 500
```
#### 任务执行器配置
```yaml
goof:
  task:
    executor:
      core-pool-size: 8
      max-pool-size: 16
      keep-alive-seconds: 30
      queue-capacity: 500
```
#### 跨域配置
```yaml
goof:
  cors:
    enabled: true
```
#### 文件上传限制配置(仅WebMvc生效)
```yaml
goof:
  multipart:
    enabled: true  # 必须为true, 否则不会生效
    location: /data/tmp 
    is-project-root: true   # 路径是否在当前项目的根目录生成
    max-request-size: 10
    max-file-size: 10
    file-size-threshold: 10
```
#### 定时器连接池
```yaml
goof:
  scheduled:
    enabled: true  # 是否开启，默认：true
    pool-size: 10
```





























