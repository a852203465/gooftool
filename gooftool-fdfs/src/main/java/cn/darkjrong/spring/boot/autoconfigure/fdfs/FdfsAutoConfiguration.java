package cn.darkjrong.spring.boot.autoconfigure.fdfs;

import cn.darkjrong.fdfs.FastDFSTemplate;
import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Fast DFS 加载配置
 * @author Rong.Jia
 * @date 2019/10/17 00:27
 */
@Configuration
@Import(FdfsClientConfig.class)
@ConditionalOnClass({FdfsProperties.class})
@EnableConfigurationProperties({FdfsProperties.class})
@ConditionalOnProperty(prefix = "goof.fdfs", name = "enabled", havingValue = "true")
public class FdfsAutoConfiguration {

    @Bean
    public FastDFSTemplate fastDFSUtils(FastFileStorageClient storageClient,
                                        AppendFileStorageClient appendFileStorageClient,
                                        ThumbImageConfig thumbImageConfig,
                                        FdfsWebServer fdfsWebServer,
                                        TrackerClient trackerClient) {
        return new FastDFSTemplate(trackerClient, storageClient, appendFileStorageClient, thumbImageConfig, fdfsWebServer);
    }


}
