# FastDFS模块

## 说明
 - 本Spring Starter只是基于com.github.tobato.fastdfs-client 进行使用的封装

## 使用方式
```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>gooftool-fdfs</artifactId>
</dependency>
```

## 配置参数(application.properties) yml配置
```yaml
  goof:
    fdfs:
      # 是否开启
      enabled: true
      so-timeout: 1501
      connect-timeout: 601
      thumb-image:             #缩略图生成参数
        width: 150
        height: 150
      tracker-list:            #TrackerList参数,支持多个
        - 192.168.160.129:22122
      web-server-url: 192.168.160.129:8080
      pool:
        #从池中借出的对象的最大数目（配置为-1表示不限制）
        max-total: -1
        #获取连接时的最大等待毫秒数(默认配置为5秒)
        max-wait-millis: 5000
        #每个key最大连接数
        max-total-per-key: 50
        #每个key对应的连接池最大空闲连接数
        max-idle-per-key: 10
        #每个key对应的连接池最小空闲连接数
        min-idle-per-key: 5
```


## API 注入
```java
    @Autowired
    private FastDFSTemplate fastDFSTemplate;
```
