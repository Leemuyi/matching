package top.woodenyi.window;

import javax.swing.*;


/**
 * Copyright (C), Tue,Mar,24,2020,
 * FileName: Window
 * Author:   WoodenYi
 * E-mail： curtainldy@163.com
 * Date:     Tue,Mar,24,2020 13:44
 * Description: 绘制窗口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号            描述
 * WoodenYi       2020/3/24 13:44      v_0.0.1
 * @author WoodenYi
 */
public class Window extends JFrame {

    public Window(){
//        创建顶层容器
        JFrame frame = new JFrame("文本匹配器");
        frame.setResizable(false);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        ImageIcon imageIcon = new ImageIcon("src/main/java/matching.jpg");
        frame.setIconImage(imageIcon.getImage());
//        窗口关闭
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ExecuteFrame executeFrame = new ExecuteFrame();
        frame.setContentPane(executeFrame.setExecuteFrame());
        frame.setVisible(true);
    }
}
