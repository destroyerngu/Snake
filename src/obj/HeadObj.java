package obj;

import utils.GameUtils;
import utils.GameWin;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HeadObj extends GameObj {
    // 方向 up down left right 默认右
    private String direction = "right";

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }



    public HeadObj(Image img, int x, int y, GameWin frame) {
        super(img, x, y, frame);
        // 键盘监听事件
        this.frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                changeDirection(e);
            }
        });
    }

    // 控制移动方向   W A S D
    public void changeDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            // A
            case KeyEvent.VK_A:
                // 蛇头方向不能直接变成相反，所以需要判断
                if (!"right".equals(direction)) {
                    direction = "left";
                    // 蛇头图片也要随之改变
                    img = GameUtils.leftImage;
                }
                break;
            case KeyEvent.VK_D:
                if (!"left".equals(direction)) {
                    direction = "right";
                    img = GameUtils.rightImage;
                }
                break;
            case KeyEvent.VK_W:
                if (!"down".equals(direction)) {
                    direction = "up";
                    img = GameUtils.upImage;
                }
                break;
            case KeyEvent.VK_S:
                if (!"up".equals(direction)) {
                    direction = "down";
                    img = GameUtils.downImage;
                }
                break;
            default:
                break;
        }
    }

    // 蛇的移动
    // 根据键盘操作，上，下，左，右，对蛇的头部实现移动
    // 画布上向右为x轴, 画布向下为y轴正方向
    public void move() {
        // 与蛇头非直连的身体的移动
        java.util.List<BodyObj> bodyObjList = this.frame.bodyObjlist;
        for (int i = bodyObjList.size() - 1; i >= 1; i--) {
            bodyObjList.get(i).x = bodyObjList.get(i - 1).x;
            bodyObjList.get(i).y = bodyObjList.get(i - 1).y;
            // 判断蛇头与身体的碰撞
            if (this.x == bodyObjList.get(i).x && this.y == bodyObjList.get(i).y) {
                // 失败
                GameWin.state = 3;
            }
        }
        // 与蛇头相邻的那一个部分应该为蛇头的坐标
        bodyObjList.get(0).x = this.x;
        bodyObjList.get(0).y = this.y;
        switch (direction) {

            // 蛇头的移动
            case "up":
                y -= height;
                break;
            case "down":
                y +=  height;
                break;
            case "left":
                x -= width;
                break;
            case "right":
                x += width;
                break;
            default:
                break;
        }
    }
    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
        // 食物
        FoodObj food = this.frame.foodObj;
        // 身体最后一节的坐标
        Integer newX = null;
        Integer newY = null;

        // 蛇头与食物重合，食物应当被吃掉
        if (this.x == food.x && this.y == food.y) {
            this.frame.foodObj = food.getFood();
            // 获取蛇身的最后一个元素
            BodyObj lastBody = this.frame.bodyObjlist.get(this.frame.bodyObjlist.size() - 1);
            newX = lastBody.x;
            newY = lastBody.y;
            // 分数+1
            this.frame.score++;
        }
        // 游戏通关判断,根据游戏得分判断是否通关,同时改变状态
        if (this.frame.score >= 3) {
            GameWin.state = 4;
        }
        move();
        // move结束后，新的bodyObj对象添加到bodyObjList
        if (newX != null && newY != null) {
            this.frame.bodyObjlist.add(new BodyObj(GameUtils.bodyImage, newX, newY, this.frame));
        }
        // 越界
        // 蛇头从左消失和右消失
        if (x < 0) {
            x = 570;
        } else if (x > 570) {
            x = 0;
        } else if (y < 30) {
            y = 570;
        } else if (y > 570) {
            y = 30;
        }
    }
}
