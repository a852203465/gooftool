package cn.darkjrong.swagger.gateway;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * swagger网关自动配置
 *
 * @author Rong.Jia
 * @date 2021/07/09
 */
@ComponentScan
@Configuration
public class SwaggerGatewayAutoConfiguration {

    @Configuration
    @ConditionalOnClass({WebFluxConfigurer.class})
    public class GatewaySwaggerConfiguration {

        @Bean
        @Primary
        public GatewaySwaggerProvider gatewaySwaggerProvider(org.springframework.cloud.gateway.route.RouteLocator routeLocator,
                                                             GatewayProperties gatewayProperties, CustomGatewayProperties customGatewayProperties) {
            return new GatewaySwaggerProvider(routeLocator, gatewayProperties, customGatewayProperties);
        }

        @Bean
        public SwaggerHeaderFilter swaggerHeaderFilter(CustomGatewayProperties customGatewayProperties){
            return new SwaggerHeaderFilter(customGatewayProperties.getSwagger());
        }
    }





}
