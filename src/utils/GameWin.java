package utils;

import obj.BodyObj;
import obj.FoodObj;
import obj.HeadObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWin extends JFrame {

    // 游戏状态 0 未开始 1 游戏中 2 暂停 3 失败 4 通关 5 失败后重启 6 下一关
    public static int state = 0;

    // 分数
    public  int score = 0;
    // 窗口宽高
    int winWidth = 800;
    int winHeight = 600;
    // 定义双缓存图片
    Image offScreenImage = null;

    // 蛇身体的集合
    public List<BodyObj> bodyObjlist = new ArrayList<BodyObj>();
    // 蛇头对象
    HeadObj headObj = new HeadObj(GameUtils.rightImage, 60, 570, this);

    // 食物
    public FoodObj foodObj = new FoodObj().getFood();
    public void launch() {
        // 设置窗口是否可见，默认为false
        this.setVisible(true);
        // 设置窗口大小
        this.setSize(winWidth, winHeight);
        // 设置窗口的位置在屏幕上居中
        this.setLocationRelativeTo(null);
        // 设置窗口标题
        this.setTitle("Snake");

        // 蛇身体的初始化 两节身体
        bodyObjlist.add(new BodyObj(GameUtils.bodyImage, 30 ,570, this));
        bodyObjlist.add(new BodyObj(GameUtils.bodyImage, 0 ,570, this));


        // 控制游戏开始，中止，继续
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    switch (state) {
                        // 未开始，和从暂停开始
                        case 0:
                        case 2:
                            // 游戏暂停
                            state = 1;
                            break;
                        // 游戏中
                        case 1:
                            state = 2;
                            repaint();
                            break;
                        // 失败后重新开始
                        case 3:
                            state = 5;
                            break;
                        case 4:
                            state = 6;
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        while (true) {
            // 游戏中才调用
            if (state == 1) {
                repaint();
            }

            // 失败重启
            if (state == 5) {
                state = 0;
                resetGame();
            }
            if (state == 6 && GameUtils.level != 3) {
                state = 1;
                GameUtils.level++;
                resetGame();
            }
            try {
                // 间隔
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void paint(Graphics graphics) {
        //初始化双缓冲图片
        if (offScreenImage == null) {
            offScreenImage = this.createImage(winWidth, winHeight);
        }
        // 获取图片对应的graphics对象
        Graphics gImage = offScreenImage.getGraphics();
        // 灰色背景
        gImage.setColor(Color.gray);
        gImage.fillRect(0, 0, winWidth, winHeight);
        // 网格线
        gImage.setColor(Color.BLACK);

        for (int i = 0; i <= 20; i++) {
            // 横线
            gImage.drawLine(0, i * 30, 600, i * 30);
            // 竖线
            gImage.drawLine(i * 30, 0, i * 30, 600);
        }
        // 防止身体重叠，反向遍历
        for (int i = bodyObjlist.size() - 1; i >= 0; i--) {
            bodyObjlist.get(i).paintSelf(gImage);
        }
        // 绘制蛇头
        headObj.paintSelf(gImage);
        // 绘制食物
        foodObj.paintSelf(gImage);
        // 分数绘制
        GameUtils.drawWord(gImage, score + " 分", Color.blue, 40,675, 320);
        // 关卡绘制
        GameUtils.drawWord(gImage, "第" + GameUtils.level + "关", Color.orange, 40, 650, 260);
        // 绘制提示语
        gImage.setColor(Color.gray);
        prompt(gImage);
        // 将双缓存图片绘制到窗口中
        graphics.drawImage(offScreenImage,0 ,0, null);
    }
    // 绘制提示语
    void prompt(Graphics g) {
        // 游戏未开始
        if (state == 0) {
            g.fillRect(120, 240, 400, 70);
            GameUtils.drawWord(g, "按下空格开始游戏", Color.yellow, 35, 150, 290);
        }
        // 游戏暂停
        if (state == 2) {
            g.fillRect(120, 240, 400, 70);
            GameUtils.drawWord(g, "按下空格继续游戏", Color.yellow, 35, 150, 290);
        }
        // 游戏失败
        if (state == 3) {
            g.fillRect(120, 240, 400, 70);
            GameUtils.drawWord(g, "GameOver 按空格重新开始", Color.red, 25, 150, 290);
        }
        // 游戏通关
        if (state == 4) {
            g.fillRect(120, 240, 400, 70);

            if (GameUtils.level == 3) {
                GameUtils.drawWord(g, "恭喜您游戏通关!!!", Color.green, 35, 150, 290);
            }
            else {
                GameUtils.drawWord(g, "达成条件，空格下一关", Color.green, 35, 150, 290);
            }
        }


    }

    // 游戏重置
    void resetGame() {
        // 关闭当前窗口
        this.dispose();
        // 开启新窗口
        String [] args = {};
        main(args);
    }

    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
