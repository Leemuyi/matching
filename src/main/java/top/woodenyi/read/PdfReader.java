package top.woodenyi.read;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;

import java.io.*;

/**
 * Copyright (C), Fri,Apr,03,2020,
 * FileName: PdfReader
 * Author:   WoodenYi
 * E-mail： curtainldy@163.com
 * Date:     Fri,Apr,03,2020 19:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号            描述
 * WoodenYi       2020/4/3 19:54      v_
 * @author WoodenYi
 */
public class PdfReader {
    /**
    pdf转换html
     */
    public static void main(String[] args)  {
        String outputPath = "C:\\Users\\woodenYi\\Desktop\\DateBase.html";
        byte[] bytes = getBytes("C:\\Users\\woodenYi\\Desktop\\DateBase.pdf");
//        try() 写在()里面会自动关闭流
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputPath)),"utf8"));){
            //加载PDF文档
            PDDocument document = PDDocument.load(bytes);
            PDFDomTree pdfDomTree = new PDFDomTree();
            pdfDomTree.writeText(document,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
    将文件转换为byte数组
     */
    private static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
