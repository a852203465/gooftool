package cn.darkjrong.watermark;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Rart {

    @Test
    void asda() throws Exception {
        FileInputStream is = new FileInputStream("F:/test.doc");
        // XWPFDocument docx
        // HWPFDocument doc

        XWPFDocument doc = new XWPFDocument(is);
//        addWaterMark(doc, watermark, color, fontSize, rotation);

        DocxUtil.makeFullWaterMarkByWordArt(doc, "小i机器人", "#d8d8d8", "0.5pt", "-30");
        String filePath = "F://2.docx";
        OutputStream os = new FileOutputStream(filePath);
        doc.write(os);


















//        addWordWaterMark("F:/邮寄委托书-贾荣 - 副本.docx", "F://output.docx","小i机器人");
    }



    public static void main(String[] args) {
        try {
            // 读取Word文档
            File file = new File("F:/邮寄委托书-贾荣 - 副本.docx");
            XWPFDocument document = new XWPFDocument(new FileInputStream(file));

            // 创建水印图片
            BufferedImage watermarkImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = watermarkImage.createGraphics();
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            g2d.drawString("小i机器人", 500, 500);

            // 在Word文档中插入水印图片
            XWPFRun run = document.createParagraph().createRun();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(watermarkImage, "png", baos);
            run.addPicture(new ByteArrayInputStream(baos.toByteArray()), Document.PICTURE_TYPE_PNG, "watermark.png", 200, 100);

            // 保存并关闭文档
            document.write(new FileOutputStream("F://output.docx"));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addWordWaterMark(String inputPath, String outPath, String markStr) {
        // 读取原始文件
        File inputFile = new File(inputPath);
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(new FileInputStream(inputFile));
        } catch (FileNotFoundException var24) {
            throw new RuntimeException("源文件不存在");
        } catch (IOException var25) {
            throw new RuntimeException("读取源文件IO异常");
        } catch (Exception var26) {
            throw new RuntimeException("不支持的文档格式");
        }
        // 使用自带工具类完成水印填充
        XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
        headerFooterPolicy.createWatermark(markStr);
        // 设置文档只读
        doc.enforceReadonlyProtection();
        // 生成输出文件
        File file = new File(outPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var23) {
                throw new RuntimeException("创建输出文件失败");
            }
        }
        // 打开输出流，将doc写入输出文件
        OutputStream os = null;
        try {
            os = new FileOutputStream(outPath);
            doc.write(os);
        } catch (FileNotFoundException var21) {
            throw new RuntimeException("创建输出文件失败");
        } catch (Exception var22) {
            throw new RuntimeException("添加文档水印失败");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException var20) {
                    var20.printStackTrace();
                }
            }
        }
    }



}
