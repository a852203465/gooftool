# rabbitmq队列和交换机配置模块

## 使用方式
### 引入依赖
```xml
<dependency>
    <groupId>cn.darkjrong</groupId>
    <artifactId>gooftool-rabbitmq-exque</artifactId>
</dependency>
```
### 配置参数(application.properties)  yml配置
```yaml
spring:

  # rabbitmq
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

    # 交换机
    exchanges:
      - name: exchange
        type: DIRECT

    # 队列
    queues:
      # 直连队列
      - name: SPC_RET_MFG_DATA
        exchange-name: exchange
      # 直连队列
      - name: SPC_RET_MFG_DATA_ERROR
        exchange-name: exchange

```














