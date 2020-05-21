package top.woodenyi.window.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Copyright (C), Wed,Mar,25,2020,
 * FileName: JTextFieldTooltipText
 * Author:   WoodenYi
 * E-mail： curtainldy@163.com
 * Date:     Wed,Mar,25,2020 1:27
 * Description: 文本域和文本框的默认提示文本
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号            描述
 * WoodenYi       2020/3/25 1:27      v_0.0.1
 * @author WoodenYi
 */
public class TooltipTextForField implements FocusListener {
    private static final String HINT_TEXT = "1.目标文本框不能为空;\n" +
            "2.默认字符集为UTF-8;\n" +
            "3.文件选择操作不支持回退功能,即选错只能点击清空按钮进行重置;";
    private JTextField textField;
    public TooltipTextForField(JTextField jTextField) {
        this.textField = jTextField;
        jTextField.setText(TooltipTextForField.HINT_TEXT);
        jTextField.setForeground(Color.GRAY);
    }



    @Override
    public void focusGained(FocusEvent e) {
        //获取焦点时，清空提示内容
        String temp = textField.getText();
        if(temp.equals(TooltipTextForField.HINT_TEXT)) {
            textField.setText("");
            textField.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点时，没有输入内容，显示提示内容
        String temp = textField.getText();
        if(temp.equals("")) {
            textField.setForeground(Color.GRAY);
            textField.setText(TooltipTextForField.HINT_TEXT);
        }
    }
}
