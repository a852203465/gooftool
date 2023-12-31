package cn.darkjrong.swagger.common.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.Collections;
import java.util.List;

/**
 * securitySchemes 支持方式之一 ApiKey
 *
 * @author Rong.Jia
 * @date 2022/02/11
 */
@Configuration
@EnableConfigurationProperties(SwaggerAuthorizationProperties.class)
public class SwaggerAuthorizationConfiguration {

    public SwaggerAuthorizationProperties properties;

    public SwaggerAuthorizationConfiguration(SwaggerAuthorizationProperties properties) {
        this.properties = properties;
    }

    /**
     * 配置默认的全局鉴权策略的开关，以及通过正则表达式进行匹配
     *
     * @return SecurityContext
     */
    public SecurityContext securityContext() {
        // 配置默认的全局鉴权策略的开关，以及通过正则表达式进行匹配；默认 ^.*$ 匹配所有URL
        // 其中 securityReferences 为配置启用的鉴权策略
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> defaultAuth = Collections.singletonList(
            SecurityReference.builder().reference(properties.getName()).scopes(authorizationScopes).build());

        return SecurityContext.builder().securityReferences(defaultAuth)
            .forPaths(PathSelectors.regex(properties.getAuthRegex())).build();
    }

    /**
     * Authorization 配置项
     *
     * @return
     */
    public List<SecurityScheme> getSecuritySchemes() {
        if ("BasicAuth".equalsIgnoreCase(getType())) {
            return Collections.singletonList(basicAuth());
        } else if (!"None".equalsIgnoreCase(getType())) {
            return Collections.singletonList(apiKey());
        }
        return null;
    }

    private ApiKey apiKey() {
        // 配置基于 ApiKey 的鉴权对象
        return new ApiKey(properties.getName(), properties.getKeyName(), ApiKeyVehicle.HEADER.getValue());
    }

    private BasicAuth basicAuth() {
        // 配置基于 BasicAuth 的鉴权对象
        return new BasicAuth(properties.getName());
    }

    private String getType() {
        return properties.getType();
    }
}
