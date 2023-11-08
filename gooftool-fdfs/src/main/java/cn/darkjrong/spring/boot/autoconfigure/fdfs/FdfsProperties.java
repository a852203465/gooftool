package cn.darkjrong.spring.boot.autoconfigure.fdfs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Fast DFS  配置类
 * @author Rong.Jia
 * @date 2019/10/17 00:23
 */
@ConfigurationProperties(prefix = "goof.fdfs")
public class FdfsProperties {

    /**
     * 是否开启 Fast DFS
     */
    private Boolean enabled = Boolean.FALSE;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
