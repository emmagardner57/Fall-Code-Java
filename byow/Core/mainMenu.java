package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class mainMenu {
    public static int height;
    public static int weight;
    public static String typed;
    public mainMenu(int h, int w) {
        height = h;
        weight = w;
        typed = "Enter 'n' + seed + 's' to generate a world";
        StdDraw.setCanvasSize(weight * 16, height * 16);
        Font font = new Font("Monaco", Font.TRUETYPE_FONT, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, weight);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void makeMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.CYAN);
        Font fontBig = new Font("Monaco", Font.TRUETYPE_FONT, 30);
        StdDraw.setFont(fontBig);
    }

    public void enterSeed() {
        makeMenu();
        StdDraw.text(weight/2, height/2, typed);
        StdDraw.show();
    }

    public void enteringSeed(String t) {
        typed = t;
        enterSeed();
    } 

    public void firstScreen() {
        makeMenu();
        StdDraw.text(weight/2, height - 3, "CS61B: THE GAME");
        StdDraw.text(weight/2, height - 8, "New Game (N)");
        StdDraw.text(weight/2, height - 13, "Load Game (L)");
        StdDraw.text(weight/2, height - 18, "Quit (Q)");
        StdDraw.text(weight/2, height - 23, "to change avatar during game, press 'c' then press f for flower,");
        StdDraw.text(weight/2, height - 25, "'t' for tree, 's' for sand, 'g' for grass, 'm' for mountain, or 'w' for waves");
        StdDraw.show();

    }



    }

