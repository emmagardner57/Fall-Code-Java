package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

import static byow.Core.Engine.actionTracker;
import static byow.Core.Engine.saveGame;


public class avatarMovement {
    public TETile[][] world;
    public int numFlowers;
    public TETile av;
    public int move;
    public int HEIGHT;
    public int WIDTH;
    public Space avatar;
    public boolean sC;
    public TETile[][] regWorld;
    public TETile[][] bWorld;
    public TERenderer ter;
    public Random rand;
    public boolean changeChar;
    public boolean ateTree;
    public Space treeAv;
    public Space bAv;
    public avatarMovement(TETile[][] whirld, int h, int w, TERenderer t, Random r) {
        numFlowers = 0;
        av = Tileset.MOUNTAIN;
        world = whirld;
        regWorld = whirld;
        HEIGHT = h;
        WIDTH = w;
        ter = t;
        move = 0;
        rand = r;
        changeChar = false;
        sC = false;
        int x = rand.nextInt(0, WIDTH);
        int y = rand.nextInt(0, HEIGHT);
        while (!world[x][y].equals(Tileset.FLOOR)) {
            x = rand.nextInt(0, WIDTH); //not being set to new random object in make world
            y = rand.nextInt(0, HEIGHT);
        }
        avatar = new Space(x, y);
        treeAv = avatar;
        world[avatar.x][avatar.y] = av;

    }
    public class Space {
        public int x;
        public int y;
        public Space(int h, int v) {
            x = h;
            y = v;
        }

        public void move(int dx, int dy) {
            x += dx;
            y += dy;
        }
    }

    public TETile[][] moveAvatar(char typed) {
        ateTree = false;
        if (sC && (typed == 'q' || typed == 'Q')) {
           // StdDraw.save("CS Game; Move: " + move);
           saveGame();
           System.exit(0); // quits
        }
        else if (changeChar) {
            changeAvatar(typed);
            world[avatar.x][avatar.y] = av;
            changeChar = false;
        }
        else if (typed == ':') {
            sC = true;
        }
        else if (typed == 'w' || typed == 'W') {
            moveUp();
        }
        else if (typed == 's' || typed == 'S') {
            moveDown();
        }
        else if (typed == 'a' || typed == 'A') {
            moveLeft();
        }
        else if (typed == 'd' || typed == 'D') {
            moveRight();
        }
        else if (typed == 'c') {
            actionTracker.add('c');
            sC = false;
            changeChar = true;
        }

        if (numFlowers < 1) {
            System.out.println("num flowers less than 1");
            world = regWorld;
            avatar = treeAv;
        }
        if (ateTree) {
            makeBonus();
        }
        return world;
    }


    public void makeBonus() {
        bonusWorld bonusWorldMaker = new bonusWorld(WIDTH, HEIGHT, av);
        bWorld = bonusWorldMaker.world;
        ter.renderFrame(world);
        world = bWorld;
        bAv = new Space(bonusWorldMaker.avatar.x, bonusWorldMaker.avatar.y);
        avatar = bAv;
        numFlowers = bonusWorldMaker.numFlowers;
    }


    public void changeAvatar(char typed) {
        if (typed == 'w' || typed == 'W') {
            actionTracker.add('o');
            av = Tileset.WATER;
        }
        else if (typed == 'm' || typed == 'M') {
            actionTracker.add('m');
            av = Tileset.MOUNTAIN;
        }
        else if (typed == 'g' || typed == 'G') {
            actionTracker.add('g');
            av = Tileset.GRASS;
        }
        else if (typed == 't' || typed == 'T') {
            actionTracker.add('t');
            av = Tileset.TREE;
        }
        else if (typed == 's'  || typed == 'S') {
            actionTracker.add('c');
            av = Tileset.SAND;
        }
        else if (typed == 'f'  || typed == 'F') {
            actionTracker.add('f');
            av = Tileset.FLOWER;
        }
    }

    public void moveUp() {
        actionTracker.add('w');
        sC = false;
        changeChar = false;
        move++;
        if (avatar.y + 1 < HEIGHT && !world[avatar.x][avatar.y + 1].equals(Tileset.WALL)) {
            world[avatar.x][avatar.y] = Tileset.FLOOR;
            avatar.move(0, 1);
            if (world[avatar.x][avatar.y].equals(Tileset.TREE)) {
                ateTree = true;
            }
            if (world[avatar.x][avatar.y].equals(Tileset.FLOWER)) {
                numFlowers--;
                System.out.println(numFlowers);
            }
            world[avatar.x][avatar.y] = av;
        }
    }
    public void moveDown() {
        actionTracker.add('s');
        sC = false;
        changeChar = false;
        move++;
        if (avatar.y - 1 >= 0 && !world[avatar.x][avatar.y - 1].equals(Tileset.WALL)) {
            world[avatar.x][avatar.y] = Tileset.FLOOR;
            avatar.move(0, -1);
            if (world[avatar.x][avatar.y].equals(Tileset.TREE)) {
                ateTree = true;
            }
            if (world[avatar.x][avatar.y].equals(Tileset.FLOWER)) {
                numFlowers--;
                System.out.println(numFlowers);
            }
            world[avatar.x][avatar.y] = av;
        }
    }
    public void moveLeft() {
        actionTracker.add('a');
        sC = false;
        changeChar = false;
        move++;
        if (avatar.x - 1 >= 0 && !world[avatar.x - 1][avatar.y].equals(Tileset.WALL)) {
            world[avatar.x][avatar.y] = Tileset.FLOOR;
            avatar.move(-1, 0);
            if (world[avatar.x][avatar.y].equals(Tileset.TREE)) {
                ateTree = true;
            }
            if (world[avatar.x][avatar.y].equals(Tileset.FLOWER)) {
                numFlowers--;
                System.out.println(numFlowers);
            }
            world[avatar.x][avatar.y] = av;
        }
    }
    public void moveRight() {
        actionTracker.add('d');
        sC = false;
        changeChar = false;
        move++;
        if (avatar.x + 1 < WIDTH && !world[avatar.x + 1][avatar.y].equals(Tileset.WALL)) {
            world[avatar.x][avatar.y] = Tileset.FLOOR;
            avatar.move(1, 0);
            if (world[avatar.x][avatar.y].equals(Tileset.TREE)) {
                ateTree = true;
            }
            if (world[avatar.x][avatar.y].equals(Tileset.FLOWER)) {
                numFlowers--;
                System.out.println(numFlowers);
            }
            world[avatar.x][avatar.y] = av;
        }
    }

}
