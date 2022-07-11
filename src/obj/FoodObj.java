package obj;

import utils.GameUtils;
import utils.GameWin;

import java.awt.*;
import java.util.Random;

public class FoodObj extends GameObj {

    // 定义随机函数
    Random r = new Random();

    public FoodObj(Image img, int x, int y, GameWin frame) {
        super(img, x, y, frame);
    }

    // 获取食物
    public FoodObj getFood() {
        return new FoodObj(GameUtils.foodImage, r.nextInt(20) * 30, (r.nextInt(19) + 1) * 30, this.frame);
    }
    public FoodObj() {
        super();
    }

    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
    }
}
