package utils;

import com.sun.java.swing.plaf.windows.WindowsToggleButtonUI;

import java.awt.*;

public class GameUtils {

    // 蛇头 四个方向图片
    public static Image upImage = Toolkit.getDefaultToolkit().getImage("img/up.png");
    public static Image downImage = Toolkit.getDefaultToolkit().getImage("img/down.png");
    public static Image leftImage = Toolkit.getDefaultToolkit().getImage("img/left.png");
    public static Image rightImage = Toolkit.getDefaultToolkit().getImage("img/right.png");
    // 蛇身
    public static Image bodyImage = Toolkit.getDefaultToolkit().getImage("img/body.png");
    // 食物
    public static Image foodImage = Toolkit.getDefaultToolkit().getImage("img/food.png");
    // 关卡
    public static int level = 1;

    // 绘制文字
    public static void drawWord(Graphics g, String str, Color color, int size, int x, int y) {
        // 设置颜色
        g.setColor(color);
        // 设置字体
        g.setFont(new Font("微软雅黑", Font.BOLD, size));
        g.drawString(str, x, y);
    }
}
