package cn.darkjrong.aliyun.oss.impl;

import cn.darkjrong.aliyun.oss.VideoApi;
import cn.darkjrong.aliyun.oss.common.builder.StyleBuilder;
import cn.darkjrong.aliyun.oss.common.constants.FileConstant;
import cn.darkjrong.aliyun.oss.common.enums.VideoProcessingEnum;
import cn.darkjrong.aliyun.oss.common.exception.AliyunOSSClientException;
import cn.darkjrong.aliyun.oss.common.pojo.dto.VideoSnapshotDTO;
import cn.darkjrong.aliyun.oss.common.utils.ExceptionUtils;
import cn.hutool.core.io.IoUtil;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 *  视频处理API实现
 * @author Rong.Jia
 * @date 2021/02/23 21:48
 */
@Slf4j
@Service
public class VideoApiImpl extends BaseApiImpl implements VideoApi {

    @Override
    public boolean snapshot(String bucketName, String objectName, VideoSnapshotDTO videoSnapshotDTO, File desFile) {

        String style = StyleBuilder.custom().video()
                .processMode(VideoProcessingEnum.SNAPSHOT)
                .time(videoSnapshotDTO.getTime()).width(videoSnapshotDTO.getWidth())
                .height(videoSnapshotDTO.getHeight()).mode(videoSnapshotDTO.getMode())
                .snapshotFormat(videoSnapshotDTO.getFormat()).rotate(videoSnapshotDTO.getRotate())
                .build();

        return super.processing(bucketName, objectName, style, desFile);
    }

    @Override
    public byte[] snapshot(String bucketName, String objectName, VideoSnapshotDTO videoSnapshotDTO) throws AliyunOSSClientException {

        String style = StyleBuilder.custom().video()
                .processMode(VideoProcessingEnum.SNAPSHOT)
                .time(videoSnapshotDTO.getTime()).width(videoSnapshotDTO.getWidth())
                .height(videoSnapshotDTO.getHeight()).mode(videoSnapshotDTO.getMode())
                .snapshotFormat(videoSnapshotDTO.getFormat()).rotate(videoSnapshotDTO.getRotate())
                .build();

        GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);

        try {
            OSSObject object = getOssClient().getObject(request);
            return IoUtil.readBytes(object.getObjectContent());
        } catch (Exception e) {
            log.error("snapshot {}", e.getMessage());
            throw new AliyunOSSClientException(ExceptionUtils.exception(e));
        }
    }

    @Override
    public String snapshot(VideoSnapshotDTO videoSnapshotDTO, String bucketName, String objectName) {
        return this.snapshot(videoSnapshotDTO, bucketName, bucketName, FileConstant.EXPIRATION_TIME);
    }

    @Override
    public String snapshot(VideoSnapshotDTO videoSnapshotDTO, String bucketName, String objectName, Long expirationTime) {

        String style = StyleBuilder.custom().video()
                .processMode(VideoProcessingEnum.SNAPSHOT)
                .time(videoSnapshotDTO.getTime()).width(videoSnapshotDTO.getWidth())
                .height(videoSnapshotDTO.getHeight()).mode(videoSnapshotDTO.getMode())
                .snapshotFormat(videoSnapshotDTO.getFormat()).rotate(videoSnapshotDTO.getRotate())
                .build();

        return super.processing(bucketName, bucketName, style, expirationTime);
    }


}
