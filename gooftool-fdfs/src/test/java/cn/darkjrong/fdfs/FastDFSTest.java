package cn.darkjrong.fdfs;

import cn.hutool.core.util.ReflectUtil;
import com.github.tobato.fastdfs.domain.conn.*;
import com.github.tobato.fastdfs.domain.fdfs.DefaultThumbImageConfig;
import com.github.tobato.fastdfs.domain.fdfs.TrackerLocator;
import com.github.tobato.fastdfs.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;

/**
 *  FastDFS 测试类
 *
 * @author Rong.Jia
 * @date 2021/07/25 09:24:55
 */
public class FastDFSTest {

    private FastDFSTemplate fastDfsTemplate;

    @BeforeEach
    public void before() {

        ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setCharsetName("UTF-8");
        pooledConnectionFactory.setConnectTimeout(601);
        pooledConnectionFactory.setSoTimeout(1501);

        FdfsConnectionPool fdfsConnectionPool = new FdfsConnectionPool(pooledConnectionFactory, connectionPoolConfig);

        FdfsWebServer fdfsWebServer = new FdfsWebServer();
        fdfsWebServer.setWebServerUrl("192.168.160.129:8080");

        DefaultThumbImageConfig thumbImageConfig = new DefaultThumbImageConfig();
        thumbImageConfig.setHeight(150);
        thumbImageConfig.setWidth(150);

        TrackerLocator trackerLocator = new TrackerLocator(Collections.singletonList("192.168.160.129:22122"));

        TrackerConnectionManager trackerConnectionManager = new TrackerConnectionManager();
        trackerConnectionManager.setTrackerList(Collections.singletonList("192.168.160.129:22122"));
        trackerConnectionManager.setPool(fdfsConnectionPool);
        ReflectUtil.setFieldValue(trackerConnectionManager, "trackerLocator", trackerLocator);

        TrackerClient trackerClient = new DefaultTrackerClient();
        ReflectUtil.setFieldValue(trackerClient, "trackerConnectionManager", trackerConnectionManager);

        FastFileStorageClient fastFileStorageClient = new DefaultFastFileStorageClient();
        ReflectUtil.setFieldValue(fastFileStorageClient, "trackerClient", trackerClient);
        ReflectUtil.setFieldValue(fastFileStorageClient, "fdfsConnectionManager", trackerConnectionManager);
        ReflectUtil.setFieldValue(fastFileStorageClient, "thumbImageConfig", thumbImageConfig);

        AppendFileStorageClient appendFileStorageClient = new DefaultAppendFileStorageClient();
        ReflectUtil.setFieldValue(appendFileStorageClient, "trackerClient", trackerClient);
        ReflectUtil.setFieldValue(appendFileStorageClient, "fdfsConnectionManager", trackerConnectionManager);

        fastDfsTemplate = new FastDFSTemplate(trackerClient, fastFileStorageClient, appendFileStorageClient, thumbImageConfig, fdfsWebServer);
    }

    @Test
    public void uploadFile() {

        String uploadFile = fastDfsTemplate.uploadFile(new File("F:\\我的图片\\美女\\1.jpg"), true);
        System.out.println(uploadFile);

    }











}
