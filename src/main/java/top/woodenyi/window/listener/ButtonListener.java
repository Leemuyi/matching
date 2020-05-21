package top.woodenyi.window.listener;

import top.woodenyi.read.PoiRead;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (C), Wed,May,20,2020,
 * FileName: ButtonListener
 * Author:   WoodenYi
 * E-mail： curtainldy@163.com
 * Date:     Wed,May,20,2020 22:25
 * Description: 按钮监听
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号            描述
 * WoodenYi       2020/5/20 22:25      v_0.0.1        处理“文件选择”、“清空”以及“搜索”按钮的点击事件
 * @author WoodenYi
 */
public class ButtonListener {
    private static final String TITLE = "提示信息";
    private static final String ERROR_MESSAGE = "目标栏数据异常或文件栏数据异常";
    private static final String EXPORT_PATH = System.getProperty("user.dir");

    /**
     * 文件选择按钮点击事件的处理
     * @param field 控件
     */
    public static void chooseFile(JTextArea field){
//        System.out.println("----------"+field.getText());
        StringBuffer buffer = new StringBuffer(field.getText());
        //初始化文件选择框
        JFileChooser fDialog = new JFileChooser();
        //设置文件选择框的标题
        fDialog.setDialogTitle("请选择文件");
        fDialog.setMultiSelectionEnabled(true);
        //弹出选择框
        int returnVal = fDialog.showOpenDialog(null);
        // 如果是选择了文件
        if(JFileChooser.APPROVE_OPTION == returnVal){
            //打印出文件的路径，你可以修改位 把路径值 写到 textField 中
            File[] files = fDialog.getSelectedFiles();
            for (File file : files) {
                String fileAddress = file.toString();
                if ("".equals(field.getText())) {
                    buffer.append(fileAddress);
                } else {
                    if (!(field.getText()).contains(fileAddress)) {
                        buffer.append(fileAddress);
                    }
                }
                buffer.append("\n");
            }

        }
        field.setText(buffer.toString());
    }

    /**
     * 清空按钮点击事件的处理
     * @param target
     * @param addressArea
     * @param textArea
     */
    public static void clearAction(JTextArea target, JTextArea addressArea, JTextArea textArea) {
        new TooltipTextForArea();
        addressArea.setText("");
        textArea.setText("");
    }

    /**
     * 搜素按钮点击事件处理
     * @param target
     * @param address
     * @param characterSet
     * @param textArea
     */
    public static void searchAction(JTextArea target, JTextArea address, JTextField characterSet, JTextArea textArea) {
        StringBuffer resulet = new StringBuffer("");
        String targetStr = target.getText();
        System.out.println("目标栏数据："+targetStr);
        String filePath = address.getText();
        System.out.println("地址栏数据："+filePath);
        // 对目标栏中的数据和地址栏中的数据进行判断，如果为空则弹出提示
        boolean isAbnormalData = (TooltipTextForArea.getHintText().equals(targetStr)) || ("".equals(address));
        if (isAbnormalData){
            JOptionPane.showMessageDialog(null,ERROR_MESSAGE,TITLE,JOptionPane.ERROR_MESSAGE);
        }else {
            String charSet = charSetHandler(characterSet.getText());
            String[] filePaths = filePath.split("\n");
            for (String path : filePaths) {
                resulet.append(fileHandler(path, targetStr, charSet));
            }
            String exportFilePath = exportFile(resulet.toString());
            resulet.append("详细信息已导出至").append(exportFilePath);
            textArea.setText(resulet.toString());
        }
    }

    /**
     * 文件处理类
     * @param filePath
     * @param targetStr
     * @param charSet
     * @return
     */
    private static String fileHandler(String filePath,String targetStr,String charSet){
        StringBuffer result = new StringBuffer("");
        boolean matchingState = false;
        // 常见File对象
        File file = new File(filePath);
        if ("".equals(filePath)){
            result.append("ERROR===========================================================================\n");
            result.append("未选择文件!\n");
            result.append("END==============================================================================\n");
            return result.toString();
        }
        result.append("文件信息:\n");
        result.append("=================================================================================\n");
        if (!file.exists()){
            result.append("未在["+filePath.substring(0,filePath.lastIndexOf("\\"))+"]找到文件名为["+filePath.substring(filePath.lastIndexOf("\\")+1)+"]的文件!\n");
            result.append("文件不存在!\n");
            result.append("匹配信息:\n");
            result.append("=================================================================================\n");
            result.append("无匹配结果.\n");
            result.append("=================================================================================\n");
        }else {
            result.append("文件位置: "+filePath+".\n");
            result.append("文件名称: "+file.getName()+".\n");
            result.append("最后修改时间: "+new Date(file.lastModified())+".\n");
            result.append("文件大小: "+file.length()+"字节.\n");
            result.append("是否隐藏：" + (file.isHidden() ? "是隐藏文件.\n" : "不是隐藏文件.\n"));
            result.append("=================================================================================\n");
            result.append("匹配信息:\n");
            result.append("=================================================================================\n");
            if (filePath.contains(".doc")||filePath.contains(".docx")){
                result.append(new PoiRead().readWordForPoi(filePath,targetStr));
            }else {
                result.append(new PoiRead().readerOtherFile(filePath, targetStr, charSet));
            }
            result.append("=================================================================================\n\n");
        }
        return result.toString();
    }

    /**
     * 字符集处理方法
     * @param arg 传入的字符参数
     * @return 处理后的字符参数
     */
    private static String charSetHandler(String arg){
        String result = "";
        switch (arg){
            case "GBK":case  "gbk":
                result = "GBK";
                break;
            case "UTF-8": case "utf-8": case "utf8": default:
                result="utf8";
        }
        return result;
    }

    /**
     * 导出匹配信息到文件
     * @param text 匹配信息
     * @return 文件地址
     */
    private static String exportFile(String text){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss");

        String exportFilePath = ButtonListener.EXPORT_PATH +"/"+sdf.format(date)+".txt";
        File file = new File(exportFilePath);
        Writer writer = null;
        try{
            writer = new FileWriter(file,true);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 释放资源
        date = null;
        sdf=null;
        writer = null;
        file = null;
        return exportFilePath;
    }
}
