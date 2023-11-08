package cn.darkjrong.fdfs;

import cn.darkjrong.core.enums.ErrorEnum;
import cn.darkjrong.fdfs.exception.FdfsException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.*;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * FastDFS文件上传下载工具类
 * @author Rong.Jia
 * @date 2020/01/07 09:47
 */
@Slf4j
@SuppressWarnings("ALL")
public class FastDFSTemplate {

    public static final String HTTP_PREFIX = "http://";

    private TrackerClient trackerClient;
    private FastFileStorageClient storageClient;
    private AppendFileStorageClient appendFileStorageClient;
    private ThumbImageConfig thumbImageConfig;
    private FdfsWebServer fdfsWebServer;

    public FastDFSTemplate(TrackerClient trackerClient, FastFileStorageClient storageClient,
                           AppendFileStorageClient appendFileStorageClient,
                           ThumbImageConfig thumbImageConfig, FdfsWebServer fdfsWebServer) {
        this.trackerClient = trackerClient;
        this.storageClient = storageClient;
        this.appendFileStorageClient = appendFileStorageClient;
        this.thumbImageConfig = thumbImageConfig;
        this.fdfsWebServer = fdfsWebServer;
    }

    /**
     * 获取组名
     *
     * @return {@link String} 组名
     */
    private String getGroupName() {
        List<GroupState> groupStateList = trackerClient.listGroups();
        if (CollectionUtil.isNotEmpty(groupStateList)) {
            return groupStateList.get(RandomUtil.randomInt(groupStateList.size())).getGroupName();
        }
        return null;
    }

    /**
     *  上传文件
     * @param file 文件对象
     * @return String 文件路径
     * @throws FdfsException 文件上传异常
     */
    public String uploadFile(MultipartFile file) throws FdfsException {
        return this.uploadFile(file, Boolean.FALSE);
    }

    /**
     *  上传文件
     * @param file 文件对象
     * @param identifier 是否拼接全路径
     * @return String 文件路径
     * @throws FdfsException 文件上传异常
     */
    public String uploadFile(MultipartFile file, Boolean identifier) throws FdfsException {
        return this.uploadFile(file, identifier, Collections.emptyMap());
    }

    /**
     *  上传文件
     * @param file 文件对象
     * @param metaData  元数据
     * @return String 文件路径
     * @throws FdfsException 文件上传异常
     */
    public String uploadFile(MultipartFile file, Map<String, String> metaData) throws FdfsException {
        return this.uploadFile(file, Boolean.FALSE, metaData);
    }

    /**
     *  上传文件
     * @param file 文件对象
     * @param identifier 是否拼接全路径
     * @param metaData  元数据
     * @return String 文件路径
     * @throws FdfsException 文件上传异常
     */
    public String uploadFile(MultipartFile file, Boolean identifier, Map<String, String> metaData) throws FdfsException {

        InputStream inputStream= null;
        try {
            inputStream = file.getInputStream();
            StorePath storePath = storageClient.uploadFile(inputStream, file.getSize(),
                    FileUtil.extName(file.getOriginalFilename()), getMetaData(metaData));
            return identifier ? getResAccessUrl(storePath) : storePath.getFullPath();
        }catch (Exception e) {
            log.error("uploadFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }

    }

    /**
     *  上传文件
     * @param bytes 文件数据
     * @param format 文件格式
     * @return String 文件路径
     */
    public String uploadFile(byte[] bytes, String format) throws FdfsException {
       return this.uploadFile(bytes, format, Boolean.FALSE);
    }

    /**
     *  上传文件
     * @param bytes 文件数据
     * @param identifier 是否拼接全路径
     * @param format 文件格式
     * @return String 文件路径
     */
    public String uploadFile(byte[] bytes, String format, Boolean identifier) throws FdfsException {
        return this.uploadFile(bytes, format, identifier, Collections.emptyMap());
    }

    /**
     *  上传文件
     * @param bytes 文件数据
     * @param metadataMap 元数据
     * @param format 文件格式
     * @return String 文件路径
     */
    public String uploadFile(byte[] bytes, String format, Map<String, String> metadataMap) throws FdfsException {
        return this.uploadFile(bytes, format, Boolean.FALSE, metadataMap);
    }

    /**
     *  上传文件
     * @param bytes 文件数据
     * @param identifier 是否拼接全路径
     * @param metadataMap 元数据
     * @param format 文件格式
     * @return String 文件路径
     */
    public String uploadFile(byte[] bytes, String format, Boolean identifier, Map<String, String> metadataMap) throws FdfsException {
        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            StorePath storePath = storageClient.uploadFile(inputStream, bytes.length, format, getMetaData(metadataMap));
            return identifier ? getResAccessUrl(storePath) : storePath.getFullPath();
        }catch (Exception e) {
            log.error("uploadFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }

    }

    /**
     *  上传文件
     * @param file 文件对象
     * @return String 文件路径
     */
    public String uploadFile(File file) throws FdfsException {

        return this.uploadFile(file, Boolean.FALSE);
    }

    /**
     *  上传文件
     * @param file 文件对象
     * @param identifier 是否拼接全路径
     * @return String 文件路径
     */
    public String uploadFile(File file, Boolean identifier) throws FdfsException {
        return this.uploadFile(file, identifier, Collections.emptyMap());
    }

    /**
     *  上传文件
     * @param file 文件对象
     * @param metadataMap 元数据
     * @return String 文件路径
     */
    public String uploadFile(File file, Map<String, String> metadataMap) throws FdfsException {

        return this.uploadFile(file, Boolean.FALSE, metadataMap);
    }

    /**
     *  上传文件
     * @param file 文件对象
     * @param metadataMap 元数据
     * @param identifier 是否拼接全路径
     * @return String 文件路径
     */
    public String uploadFile(File file, Boolean identifier, Map<String, String> metadataMap) throws FdfsException {

        InputStream inputStream = null;

        try {
            inputStream = FileUtil.getInputStream(file);
            StorePath storePath = storageClient.uploadFile(inputStream, file.length(),
                    FileUtil.extName(file.getName()), getMetaData(metadataMap));
            return  identifier ? getResAccessUrl(storePath) : storePath.getFullPath();
        }catch (Exception e) {
            log.error("uploadFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     *  把字符串作为指定格式的文件上传
     * @param content 字符串
     * @param fileExtension 文件格式
     * @return String 文件路径
     */
    public String uploadFile(String content, String fileExtension) throws FdfsException {
        return this.uploadFile(content, fileExtension, Boolean.FALSE);
    }

    /**
     *  把字符串作为指定格式的文件上传
     * @param content 字符串
     * @param identifier 是否拼接全路径
     * @param fileExtension 文件格式
     * @return String 文件路径
     */
    public String uploadFile(String content, String fileExtension, Boolean identifier) throws FdfsException {
        return this.uploadFile(content, fileExtension, identifier, Collections.emptyMap());
    }

    /**
     *  把字符串作为指定格式的文件上传
     * @param content 字符串
     * @param metadataMap 元数据
     * @param fileExtension 文件格式
     * @return String 文件路径
     */
    public String uploadFile(String content, String fileExtension,  Map<String, String> metadataMap) throws FdfsException {
        return this.uploadFile(content, fileExtension, Boolean.FALSE, metadataMap);
    }

    /**
     *  把字符串作为指定格式的文件上传
     * @param content 字符串
     * @param identifier 是否拼接全路径
     * @param metadataMap 元数据
     * @param fileExtension 文件格式
     * @return String 文件路径
     */
    public String uploadFile(String content, String fileExtension, Boolean identifier, Map<String, String> metadataMap) throws FdfsException {

        ByteArrayInputStream stream = null;
        try {
            byte[] buff = content.getBytes(UTF_8);
            stream = new ByteArrayInputStream(buff);
            StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, getMetaData(metadataMap));
            return identifier ? getResAccessUrl(storePath) : storePath.getFullPath();
        }catch (Exception e) {
            log.error("uploadFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(stream);
        }
    }

    /**
     *  上传图片
     * @param file 文件对象
     * @return String 文件路径
     * @throws FdfsException String
     */
    public String uploadImageAndCrtThumbImage(MultipartFile file) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(file, Boolean.FALSE);
    }

    /**
     *  上传图片
     * @param file 文件对象
     * @param identifier 是否拼接全路径
     * @return String 文件路径
     * @throws FdfsException String
     */
    public String uploadImageAndCrtThumbImage(MultipartFile file, Boolean identifier) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(file, identifier, Collections.emptyMap());
    }

    /**
     *  上传图片
     * @param file 文件对象
     * @param metadataMap 元数据
     * @return String 文件路径
     * @throws FdfsException String
     */
    public String uploadImageAndCrtThumbImage(MultipartFile file,  Map<String, String> metadataMap) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(file, Boolean.FALSE, metadataMap);
    }

    /**
     *  上传图片
     * @param file 文件对象
     * @param identifier 是否拼接全路径
     * @param metadataMap 元数据
     * @return String 文件路径
     * @throws FdfsException String
     */
    public String uploadImageAndCrtThumbImage(MultipartFile file, Boolean identifier, Map<String, String> metadataMap) throws FdfsException {

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, file.getSize(),
                    FileUtil.extName(file.getOriginalFilename()), getMetaData(metadataMap));
            return identifier ? getResAccessUrl(storePath) : storePath.getFullPath();
        }catch (Exception e) {
            log.error("uploadImageAndCrtThumbImage {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     *  上传图片
     * @param file 文件对象
     * @return String 文件路径
     * @throws FdfsException String
     */
    public String uploadImageAndCrtThumbImage(File file) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(file, Boolean.FALSE);
    }

    /**
     *  上传图片
     * @param file 文件对象
     * @param identifier 是否拼接全路径
     * @return String 文件路径
     * @throws FdfsException String
     */
    public String uploadImageAndCrtThumbImage(File file, Boolean identifier) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(file, identifier, Collections.emptyMap());
    }

    /**
     *  上传图片
     * @param file 文件对象
     * @param metadataMap 元数据
     * @return String 文件路径
     * @throws FdfsException String
     */
    public String uploadImageAndCrtThumbImage(File file,  Map<String, String> metadataMap) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(file, Boolean.FALSE, metadataMap);
    }

    /**
     *  上传图片
     * @param file 文件对象
     * @param identifier 是否拼接全路径
     * @param metadataMap 元数据
     * @return String 文件路径
     * @throws FdfsException String
     */
    public String uploadImageAndCrtThumbImage(File file, Boolean identifier, Map<String, String> metadataMap) throws FdfsException {

        InputStream inputStream = null;
        try {
            inputStream = FileUtil.getInputStream(file);
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, file.length(),
                    FileUtil.extName(file), getMetaData(metadataMap));
            return identifier ? getResAccessUrl(storePath) : storePath.getFullPath();
        }catch (Exception e) {
            log.error("uploadImageAndCrtThumbImage {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     *  上传文件
     * @param bytes 文件数据
     * @param format 文件格式
     * @return String 文件路径
     */
    public String uploadImageAndCrtThumbImage(byte[] bytes, String format) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(bytes, format, Boolean.FALSE);
    }

    /**
     *  上传文件
     * @param bytes 文件数据
     * @param identifier 是否拼接全路径
     * @param format 文件格式
     * @return String 文件路径
     */
    public String uploadImageAndCrtThumbImage(byte[] bytes, String format, Boolean identifier) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(bytes, format, identifier, Collections.emptyMap());
    }

    /**
     *  上传文件
     * @param bytes 文件数据
     * @param metadataMap 元数据
     * @param format 文件格式
     * @return String 文件路径
     */
    public String uploadImageAndCrtThumbImage(byte[] bytes, String format, Map<String, String> metadataMap) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(bytes, format, Boolean.FALSE, metadataMap);
    }

    /**
     *  上传文件
     * @param bytes 文件数据
     * @param identifier 是否拼接全路径
     * @param metadataMap 元数据
     * @param format 文件格式
     * @return String 文件路径
     */
    public String uploadImageAndCrtThumbImage(byte[] bytes, String format, Boolean identifier, Map<String, String> metadataMap) throws FdfsException {
        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, bytes.length, format, getMetaData(metadataMap));
            return identifier ? getResAccessUrl(storePath) : storePath.getFullPath();
        }catch (Exception e) {
            log.error("uploadImageAndCrtThumbImage {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }

    }

    /**
     *  把字符串作为指定格式的文件上传
     * @param content 字符串
     * @param fileExtension 文件格式
     * @return String 文件路径
     */
    public String uploadImageAndCrtThumbImage(String content, String fileExtension) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(content, fileExtension, Boolean.FALSE);
    }

    /**
     *  把字符串作为指定格式的文件上传
     * @param content 字符串
     * @param identifier 是否拼接全路径
     * @param fileExtension 文件格式
     * @return String 文件路径
     */
    public String uploadImageAndCrtThumbImage(String content, String fileExtension, Boolean identifier) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(content, fileExtension, identifier, Collections.emptyMap());
    }

    /**
     *  把字符串作为指定格式的文件上传
     * @param content 字符串
     * @param metadataMap 元数据
     * @param fileExtension 文件格式
     * @return String 文件路径
     */
    public String uploadImageAndCrtThumbImage(String content, String fileExtension,  Map<String, String> metadataMap) throws FdfsException {
        return this.uploadImageAndCrtThumbImage(content, fileExtension, Boolean.FALSE, metadataMap);
    }

    /**
     *  把字符串作为指定格式的文件上传
     * @param content 字符串
     * @param identifier 是否拼接全路径
     * @param metadataMap 元数据
     * @param fileExtension 文件格式
     * @return String 文件路径
     */
    public String uploadImageAndCrtThumbImage(String content, String fileExtension, Boolean identifier, Map<String, String> metadataMap) throws FdfsException {

        ByteArrayInputStream stream = null;
        try {
            byte[] buff = content.getBytes(UTF_8);
            stream = new ByteArrayInputStream(buff);
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(stream, buff.length, fileExtension, getMetaData(metadataMap));
            return identifier ? getResAccessUrl(storePath) : storePath.getFullPath();
        }catch (Exception e) {
            log.error("uploadImageAndCrtThumbImage {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(stream);
        }
    }

    /**
     * 上传支持断点续传的文件
     *
     * @param file 文件
     * @return {@link StorePath} 上传信息
     * @throws FdfsException 上传异常
     */
    public StorePath uploadAppenderFile(File file) throws FdfsException {

        if (!FileUtil.exist(file)) {
            log.error("File not found");
            throw new IllegalArgumentException(ErrorEnum.FILE_NOT_FOUND.getMessage());
        }

        return uploadAppenderFile(FileUtil.readBytes(file), FileUtil.extName(file));
    }

    /**
     *  上传支持断点续传的文件
     * @param file 文件对象
     * @return {@link StorePath} 上传信息
     * @throws FdfsException 文件上传异常
     */
    public StorePath uploadAppenderFile(MultipartFile file) throws FdfsException {

        if (ObjectUtil.isNull(file) || file.isEmpty()) {
            log.error("File not found");
            throw new IllegalArgumentException(ErrorEnum.FILE_NOT_FOUND.getMessage());
        }

        InputStream inputStream= null;
        try {
            inputStream = file.getInputStream();
            return uploadAppenderFile(IoUtil.readBytes(inputStream), FileUtil.extName(file.getOriginalFilename()));
        }catch (IOException e) {
            log.error("uploadAppenderFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     *  上传支持断点续传的文件
     * @param bytes 文件数据
     * @param format 文件格式
     * @return {@link StorePath} 上传信息
     */
    public StorePath uploadAppenderFile(byte[] bytes, String format) throws FdfsException {

        if (ArrayUtil.isEmpty(bytes)) {
            log.error("File not found");
            throw new IllegalArgumentException(ErrorEnum.FILE_NOT_FOUND.getMessage());
        }

        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            return appendFileStorageClient.uploadAppenderFile(getGroupName(), inputStream, bytes.length, format);
        }catch (Exception e) {
            log.error("uploadAppenderFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     *  追加文件
     *
     *  追加方式实际实用如果中途出错多次,可能会出现重复追加情况,这里改成修改模式,即时多次传来重复文件块,依然可以保证文件拼接正确
     *
     * @param file 文件
     * @param storePath  文件存储信息
     * @param historyUpload 历史上传大小（上一个文件的结束点）
     * @throws FdfsException 上传异常
     */
    public void appendFile(StorePath storePath, File file, Long historyUpload) throws FdfsException {

        if (!FileUtil.exist(file)) {
            log.error("The appended file does not exist");
            throw new IllegalArgumentException(ErrorEnum.THE_APPENDED_FILE_DOES_NOT_EXIST.getMessage());
        }

        appendFile(storePath, FileUtil.readBytes(file), historyUpload);
    }

    /**
     *  追加文件
     *
     *  追加方式实际实用如果中途出错多次,可能会出现重复追加情况,这里改成修改模式,即时多次传来重复文件块,依然可以保证文件拼接正确
     *
     * @param file 文件
     * @param storePath  文件存储信息
     * @param historyUpload 历史上传大小（上一个文件的结束点）
     * @throws FdfsException 文件上传异常
     */
    public void appendFile(StorePath storePath, MultipartFile file, Long historyUpload) throws FdfsException {

        if (ObjectUtil.isNull(file) || file.isEmpty()) {
            log.error("The appended file does not exist");
            throw new IllegalArgumentException(ErrorEnum.THE_APPENDED_FILE_DOES_NOT_EXIST.getMessage());
        }

        InputStream inputStream= null;
        try {
            inputStream = file.getInputStream();
            appendFile(storePath, IoUtil.readBytes(inputStream), historyUpload);
        }catch (IOException e) {
            log.error("appendFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     *  追加文件
     *
     *  追加方式实际实用如果中途出错多次,可能会出现重复追加情况,这里改成修改模式,即时多次传来重复文件块,依然可以保证文件拼接正确
     *
     * @param bytes 文件字节
     * @param storePath  文件存储信息
     * @param historyUpload 历史上传大小（上一个文件的结束点）
     */
    public void appendFile(StorePath storePath, byte[] bytes, Long historyUpload) throws FdfsException {

        if (ArrayUtil.isEmpty(bytes)) {
            log.error("The appended file does not exist");
            throw new IllegalArgumentException(ErrorEnum.THE_APPENDED_FILE_DOES_NOT_EXIST.getMessage());
        }

        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            appendFileStorageClient.modifyFile(storePath.getGroup(), storePath.getPath(), inputStream, bytes.length, historyUpload);
        }catch (Exception e) {
            log.error("appendFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     *  封装图片完整URL地址
     * @param storePath  文件路径
     * @return String 完成文件路径
     */
    private String getResAccessUrl(StorePath storePath) {
        return HTTP_PREFIX + fdfsWebServer.getWebServerUrl() + StrUtil.SLASH +  storePath.getFullPath();
    }

    /**
     *  封装图片完整URL地址
     * @param fullPath  文件路径
     * @return String 完成文件路径
     */
    public String getResAccessUrl(String fullPath) {
        return  HTTP_PREFIX + fdfsWebServer.getWebServerUrl() + StrUtil.SLASH +  fullPath;
    }

    /**
     *  根据图片路径获取缩略图路径（使用uploadImageAndCrtThumbImage方法上传图片）
     * @param filePath 图片路径
     * @return String 缩略图路径
     */
    public String getThumbImagePath(String filePath) {
        return thumbImageConfig.getThumbImagePath(filePath);
    }

    /**
     *  根据文件路径下载文件
     * @param filePath 文件路径
     * @return byte[] 文件字节数据
     */
    public byte[] downFile(String filePath) throws FdfsException {

        filePath = StrUtil.replace(filePath, HTTP_PREFIX + fdfsWebServer.getWebServerUrl() + StrUtil.SLASH, StrUtil.EMPTY);

        try {
            StorePath storePath = StorePath.parseFromUrl(filePath);
            return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), IoUtil::readBytes);
        }catch (Exception e) {
            log.error("downFile {}", e.getMessage());
            throw new FdfsException(e.getMessage());
        }

    }

    /**
     *  根据文件地址删除文件
     * @param filePath 文件访问地址`
     * @return true/false 成功/失败
     */
    public Boolean deleteFile(String filePath) {
        try {
            filePath = StrUtil.replace(filePath, HTTP_PREFIX + fdfsWebServer.getWebServerUrl() + StrUtil.SLASH, StrUtil.EMPTY);
            StorePath storePath = StorePath.parseFromUrl(filePath);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            return Boolean.TRUE;
        }catch (Exception e) {
            log.error("Delete file failed {}", e.getMessage());
        }
        return Boolean.FALSE;
    }

    /**
     *  查询文件信息
     * @param filePath 件访问地址
     * @return 文件信息
     */
    public FileInfo findFileInfo(String filePath){
        StorePath storePath = StorePath.parseFromUrl(filePath);
        return storageClient.queryFileInfo(storePath.getGroup(), storePath.getPath());
    }

    private Set<MetaData> getMetaData(Map<String, String> metaDataMap) {
        if (CollectionUtil.isNotEmpty(metaDataMap)) {
            return metaDataMap.entrySet().stream().map(a ->{
                MetaData metaData = new MetaData();
                metaData.setName(a.getKey());
                metaData.setValue(a.getValue());
                return metaData;
            }).collect(Collectors.toSet());
        }

        return null;
    }










}
