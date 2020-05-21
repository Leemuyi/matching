package top.woodenyi.window.frame;

import top.woodenyi.window.listener.ButtonListener;
import top.woodenyi.window.listener.TooltipTextForArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import java.lang.*;

import static top.woodenyi.window.listener.ButtonListener.searchAction;

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
                ButtonListener.chooseFile(addressArea);
            }
        });
        panel.add(chooseFile,BorderLayout.NORTH);
        JButton clear = new JButton("清空");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonListener.clearAction(target,addressArea,textArea);
            }
        });
        panel.add(clear,BorderLayout.NORTH);
        JButton search = new JButton("搜索");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonListener.searchAction(target,addressArea,characterSet,textArea);
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
}
