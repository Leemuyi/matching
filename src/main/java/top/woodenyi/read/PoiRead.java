package top.woodenyi.read;

import org.apache.poi.hwpf.extractor.WordExtractor;
import top.woodenyi.window.listener.TooltipTextForArea;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (C), Wed,Mar,25,2020,
 * FileName: PoiRead
 * Author:   WoodenYi
 * E-mail： curtainldy@163.com
 * Date:     Wed,Mar,25,2020 15:38
 * Description: 通过POI读取文件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号            描述
 * WoodenYi       2020/3/25 15:38      v_0.0.1        通过POI读取文件(Word,excel...)
 * @author WoodenYi
 */
public class PoiRead {
    public String readerOtherFile(String fullFilePath,String targetStr,String charSet){
        boolean matchingState = false;
        StringBuffer buffer = new StringBuffer("");
        File file = new File(fullFilePath);
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),charSet));
            String tempStr = null;
            int line = 1;
            if ((TooltipTextForArea.getHintText()).equals(targetStr)){
                buffer.append("无匹配结果(空值匹配)!\n");
                return buffer.toString();
            }
            while ((tempStr = reader.readLine()) != null){
                System.out.println(line+" : "+tempStr);
                if (tempStr.contains(targetStr)){
                    buffer.append("在第"+line+"行发现目标值! ["+tempStr+"]\n");
                    matchingState = true;
                }
                line++;
            }
            if (!matchingState){
                buffer.append("无匹配结果.\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }
    public String readWordForPoi(String fullFilePath,String targetStr){
        StringBuilder builder = new StringBuilder("");
        // 解析fullFilePath
        String filePath = fullFilePath.substring(0,fullFilePath.lastIndexOf("\\")+1);
        String fileName = fullFilePath.substring(fullFilePath.lastIndexOf("\\")+1);
        System.out.println("filePath = " + filePath + ", fileName = " + fileName);
        try {
            if (fileName.contains(".docx")) {
                builder.append(readDocx(targetStr, filePath, fileName));
            }else {
                builder.append(readDoc(targetStr,filePath,fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public String readDocx(String targetStr, String filePath, String fileName) throws IOException, OpenXML4JException, XmlException {
        StringBuilder stringBuilder = new StringBuilder("");
        XWPFWordExtractor extractor;
        extractor = new XWPFWordExtractor(OPCPackage.open(new FileInputStream(filePath+fileName)));
        String wordText = extractor.getText();
        System.out.println(wordText);
        stringBuilder.append(fileHandler(filePath,fileName,wordText,targetStr));
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String readDoc(String targetStr, String filePath, String fileName) throws IOException, OpenXML4JException, XmlException {
        StringBuilder stringBuilder = new StringBuilder("");
        WordExtractor extractor;
        extractor = new WordExtractor(new FileInputStream(new File(filePath+fileName)));
        String wordText = extractor.getText();
        wordText = wordText.replaceAll("\u200B","").replaceAll(" \\("," {").replaceAll("\\)","}");
        String regx = "\\{http://office.microsoft.com/zh-cn/excel-help/redir/{1}.+\\}";
        Matcher matcher = Pattern.compile(regx).matcher(wordText);
        wordText = matcher.replaceAll("").replaceAll("}","\\)");
//        wordText.replaceAll("(\u200Bhttp:\u200B/\u200B\u200B/\u200Boffice.microsoft.com\u200B/");
        System.out.println(wordText);
        stringBuilder.append(fileHandler(filePath,fileName,wordText,targetStr));
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String fileHandler(String filePath, String fileName, String wordText, String targetStr) throws IOException {
        StringBuffer buffer = new StringBuffer("");
        // 创建临时文件
        File file = new File(filePath+"copy"+fileName.substring(0,fileName.lastIndexOf("."))+".txt");
        BufferedWriter writer = null;
        BufferedReader reader = null;
        if (file.exists()){
            file.delete();
            file.createNewFile();
        }
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        // 数据写出
        writer.write(wordText,0,wordText.length());
        // 刷新缓冲区
        writer.flush();
        // 关闭writer流
        writer.close();
        // 数据写入内存
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        buffer.append(readerFile(reader,targetStr));
        // 关闭reader流
        reader.close();
        // 删除临时文件
        file.delete();
        return buffer.toString();
    }

    public String readerFile(BufferedReader reader,String targetStr) throws IOException {
        boolean matchingState = false;
        StringBuffer result = new StringBuffer("");
        int line = 1;
        String tempStr = null;
        if ((TooltipTextForArea.getHintText()).equals(targetStr)){
            result.append("无匹配结果(空值匹配)!\n");
            return result.toString();
        }
        while ((tempStr = reader.readLine()) != null){
            System.out.println(line+" : "+tempStr);
            if (tempStr.contains(targetStr)){
                result.append("在第"+line+"行发现目标值! ["+tempStr+"]\n");
                matchingState = true;
            }
            line++;
        }
        if (!matchingState){
            result.append("无匹配结果.\n");
        }
        return result.toString();
    }
//    public static void main(String[] args) {
//        System.out.println(new PoiRead().readerOtherFile("C:\\Users\\woodenYi\\Desktop\\测试.txt","swing","utf8"));
//    }
}
