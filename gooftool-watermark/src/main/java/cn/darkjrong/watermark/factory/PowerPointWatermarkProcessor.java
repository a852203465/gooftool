package cn.darkjrong.watermark.factory;

import cn.darkjrong.watermark.FileTypeUtils;
import cn.darkjrong.watermark.domain.WatermarkParam;
import cn.darkjrong.watermark.enums.PictureTypeEnum;
import cn.darkjrong.watermark.exceptions.WatermarkException;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.xslf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * ppt处理器
 *
 * @author Rong.Jia
 * @date 2021/08/13 17:45:49
 */
@Slf4j
public class PowerPointWatermarkProcessor extends AbstractWatermarkProcessor {

    @Override
    public Boolean supportType(File file) {
        return FileTypeUtils.isPpts(file);
    }

    @Override
    public byte[] watermark(WatermarkParam watermarkParam) throws WatermarkException {
        XMLSlideShow pptx = null;
        ByteArrayOutputStream output = null;
        InputStream inputStream = null;
        try {
            inputStream = getInputStream(watermarkParam.getFile());
            pptx = new XMLSlideShow(inputStream);
            XSLFPictureData pictureData = pptx.addPicture(watermarkParam.getImageFile(), PictureType.forNativeID(PictureTypeEnum.forContentType(FileTypeUtils.getFileType(watermarkParam.getImageFile())).nativeId));
            BufferedImage image = ImageIO.read(watermarkParam.getImageFile());
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            for (int i = 0; i < pptx.getSlideMasters().size(); i++) {
                XSLFSlideMaster slideMaster = pptx.getSlideMasters().get(i);
                Dimension pageSize = pptx.getPageSize();
                int srcWidth = pageSize.width;
                int srcHeight = pageSize.height;
                if (!watermarkParam.getBespread()) {
                    setAnchor(slideMaster, pictureData, srcWidth / 2.0 - watermarkParam.getXMove(),
                            srcHeight / 2.0 - watermarkParam.getYMove(), imageWidth, imageHeight);
                } else {
                    for (double y = 0; y < srcHeight; y = y + imageHeight + watermarkParam.getYMove()) {
                        for (double x = 0; x < srcWidth; x = x + imageWidth + watermarkParam.getXMove()) {
                            setAnchor(slideMaster, pictureData, x, y, imageWidth, imageHeight);
                        }
                    }
                }
            }
            output = new ByteArrayOutputStream();
            pptx.write(output);
            return output.toByteArray();
        } catch (FileNotFoundException e) {
            log.error("No file found {}", e.getMessage());
            throw new WatermarkException(e.getMessage());
        } catch (IOException e) {
            log.error("A watermark is incorrectly added to the PPT {}", e.getMessage());
            throw new WatermarkException(e.getMessage());
        } finally {
            IoUtil.close(output);
            IoUtil.close(pptx);
            IoUtil.close(inputStream);
            delete(watermarkParam.getImageFile());
        }
    }

    /**
     * 设置锚
     *
     * @param slideMaster ppt
     * @param pictureData 图片数据
     * @param x           x
     * @param y           y
     * @param w           w
     * @param h           h
     */
    private void setAnchor(XSLFSlideMaster slideMaster, XSLFPictureData pictureData, double x, double y, double w, double h) {
        XSLFSlideLayout[] slideLayouts = slideMaster.getSlideLayouts();
        for (XSLFSlideLayout slidelayout : slideLayouts) {
            XSLFPictureShape pictureShape = slidelayout.createPicture(pictureData);
            pictureShape.setAnchor(new Rectangle2D.Double(x, y, w, h));
        }
    }


}
