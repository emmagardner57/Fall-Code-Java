package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class typed {
    public TETile[][] world;
    public avatarMovement avatar;
    public TERenderer ter;
    public int height;
    public int width;
    public int lastX;
    public int lastY;
    public typed(TETile[][] w, avatarMovement a, TERenderer t, int h, int wi) {
        world = w;
        avatar = a;
        ter = t;
        height = h;
        width = wi;
        lastX = -1;
        lastY = -1;
    }
    public void moveAv() {
        if (StdDraw.hasNextKeyTyped()) {
            char typed = StdDraw.nextKeyTyped();
            world = avatar.moveAvatar(typed);
            ter.renderFrame(world);
        }
    }


    public  void giveDesc() {
        int x = Math.toIntExact(Math.round(Math.floor(StdDraw.mouseX())));
        int y = Math.toIntExact(Math.round(Math.floor(StdDraw.mouseY())));
        if (y < height && x < width && (x != lastX || y != lastY)) {
            lastY = y;
            lastX = x;
            String hi = world[x][y].description();
            StdDraw.clear(Color.BLACK);
            ter.renderFrame(world);
            StdDraw.setPenColor(Color.CYAN);
            Font fontBig = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(fontBig);
            StdDraw.text(5, height + 2, hi);
            StdDraw.show();
        }
    }


    public void firstOps() {}
}
