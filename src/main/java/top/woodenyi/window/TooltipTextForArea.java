package top.woodenyi.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Copyright (C), Wed,Mar,25,2020,
 * FileName: TooltipTextForArea
 * Author:   WoodenYi
 * E-mail： curtainldy@163.com
 * Date:     Wed,Mar,25,2020 1:46
 * Description: 文本域提示文字
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号            描述
 * WoodenYi       2020/3/25 1:46      v_0.0.1
 * @author WoodenYi
 */
public class TooltipTextForArea implements FocusListener {
    private static String hintText = "1.目标文本框不能为空;\n" +
            "2.默认字符集为UTF-8;\n" +
            "3.文件选择操作不支持回退功能,即选错只能点击清空按钮进行重置;";
    private JTextArea textArea;

    public static String getHintText() {
        return hintText;
    }
    public TooltipTextForArea(){}
    public TooltipTextForArea(JTextArea textArea) {
        this.textArea = textArea;
        textArea.setText(TooltipTextForArea.hintText);
        textArea.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        //获取焦点时，清空提示内容
        String temp = textArea.getText();
        if(temp.equals(TooltipTextForArea.hintText)) {
            textArea.setText("");
            textArea.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点时，没有输入内容，显示提示内容
        String temp = textArea.getText();
        if(temp.equals("")) {
            textArea.setForeground(Color.GRAY);
            textArea.setText(TooltipTextForArea.hintText);
        }
    }
}
