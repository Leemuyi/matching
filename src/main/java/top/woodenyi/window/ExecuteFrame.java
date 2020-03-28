package top.woodenyi.window;

import top.woodenyi.read.PoiRead;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import java.lang.*;

/**
 * Copyright (C), Tue,Mar,24,2020,
 * FileName: ExecuteFrame
 * Author:   WoodenYi
 * E-mail： curtainldy@163.com
 * Date:     Tue,Mar,24,2020 15:08
 * Description: 操作框
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号            描述
 * WoodenYi       2020/3/24 15:08      v_
 * @author WoodenYi
 */
public class ExecuteFrame {

    JTextArea target = new JTextArea(10,50);
    JTextField characterSet = new JTextField(6);
    JTextArea addressArea = new JTextArea(13,50);
    JTextArea textArea = new JTextArea(13,50);
    public Box setExecuteFrame(){
        JTabbedPane tabbedPane_top = new JTabbedPane();
        JScrollPane scrollPane_top=new JScrollPane();
        tabbedPane_top.addTab("目标文本",null,scrollPane_top,null);
        target.addFocusListener(new TooltipTextForArea(target));
        scrollPane_top.setViewportView(target);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(new JLabel("字符集:"));
        characterSet.setText("UTF-8");
        panel.add(characterSet);
        JButton chooseFile = new JButton("选择文件");
        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile(addressArea);
            }
        });
        panel.add(chooseFile,BorderLayout.NORTH);
        JButton clear = new JButton("清空");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAction(target,addressArea,textArea);
            }
        });
        panel.add(clear,BorderLayout.NORTH);
        JButton search = new JButton("搜索");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAction(target,addressArea,characterSet,textArea);
            }
        });
        panel.add(search,BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        JScrollPane scrollPane1=new JScrollPane();
        tabbedPane.addTab("文件列表",null,scrollPane1,null);
        scrollPane1.setViewportView(addressArea);
        JScrollPane scrollPane2=new JScrollPane();
        tabbedPane.addTab("匹配结果",null,scrollPane2,null);
        scrollPane2.setViewportView(textArea);

        Box box = Box.createVerticalBox();
        box.add(tabbedPane_top,BorderLayout.CENTER);
        box.add(panel,BorderLayout.CENTER);
        box.add(tabbedPane,BorderLayout.CENTER);

        return box;
    }

    private void clearAction(JTextArea target, JTextArea addressArea, JTextArea textArea) {
        target.setText("");
        addressArea.setText("");
        textArea.setText("");
    }

    private void searchAction(JTextArea target, JTextArea address, JTextField characterSet, JTextArea textArea) {
        StringBuffer resulet = new StringBuffer("");
        String targetStr = target.getText();
        System.out.println(targetStr);
        String charSet = charSetHandler(characterSet.getText());
        String filePath = address.getText();
        String[] filePaths = filePath.split("\n");
        for (String path : filePaths) {
            resulet.append(fileHandler(path,targetStr,charSet));
        }

        textArea.setText(resulet.toString());
    }

    public void chooseFile(JTextArea field){
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

    public String fileHandler(String filePath,String targetStr,String charSet){
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

    public String charSetHandler(String arg){
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
}
