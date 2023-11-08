# 网关聚合服务Swagger文档

## 1. 说明
### 1.1 适配说明
 - 1. zuul, gateway 等网关使用
 - 2. 已适配 web, webflux

### 1.2 版本说明
 - knife4j: 3.0.3
 - swagger: 3.0.0
 - Spring Cloud: 2021.0.4
 - Spring Boot: 2.7.4
 
## 2. 使用注意
 - jar中已根据环境切换加载方式，只需要引入对应环境的maven即可

## 3. 使用方式
### 3.1 下载源码 
 - 下载源码 并install，或者推送到私库引入使用
### 3.2 引用依赖
```xml
        <!-- swagger 文档 -->
        <dependency>
            <groupId>cn.darkjrong</groupId>
            <artifactId>swagger-gateway-spring-boot-starter</artifactId>
            <version>1.0</version>
        </dependency>    

        <!-- gateway -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
```

### 3.3 配置参数(application.properties) yml配置
#### 3.3.1 Spring Cloud Gateway
```yaml
spring:
  cloud:
    gateway:
      swagger:
        # header name， 默认： X-Forwarded-Prefix
        header-name: X-Forwarded-Prefix
        # swagger 版本 ，默认： 3.0
        swagger-version: 3.0
        # swagger api docs 默认： /v3/api-docs
        swagger-api-docs: /v3/api-docs
```

## 4. 访问
 - http://ip:port/doc.html








































