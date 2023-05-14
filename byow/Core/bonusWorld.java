package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class bonusWorld {
    public int WIDTH;
    public int HEIGHT;
    public TETile[][] world;
    public Space avatar;
    public TETile av;
    public Random rand = new Random();
    public int numFlowers;

    public bonusWorld(int w, int h, TETile a) {
        WIDTH = w;
        HEIGHT = h;
        numFlowers = 0;
        world = makeWorld();
        av = a;
        int x = rand.nextInt(0, WIDTH);
        int y = rand.nextInt(0, HEIGHT);
        while (!world[x][y].equals(Tileset.FLOOR)) {
            x = rand.nextInt(0, WIDTH);
            y = rand.nextInt(0, HEIGHT);
        }
        avatar = new Space(x, y);
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

    public TETile[][] makeWorld() {
        TETile[][] bonusWorld = new TETile[WIDTH][HEIGHT];

        for (int w = 0; w < WIDTH; w++) {
            for (int h = 0; h < HEIGHT; h++) {
                bonusWorld[w][h] = Tileset.NOTHING;
            }
        }
        for (int i = 5; i <= WIDTH - 5; i++) {
            bonusWorld[i][5] = Tileset.WALL;
            bonusWorld[i][HEIGHT - 5] = Tileset.WALL;
        }
        for (int t = 5; t <= HEIGHT - 5; t++) {
            bonusWorld[5][t] = Tileset.WALL;
            bonusWorld[WIDTH - 5][t] = Tileset.WALL;
        }

        //creates the floor
        for (int m = 5 + 1; m < WIDTH - 5; m++) {
            for (int n = 5 + 1; n < HEIGHT - 5; n++) {
                bonusWorld[m][n] = Tileset.FLOOR;
            }
        }

        for (int i = 0; i <= 20; i++) {
            int x = rand.nextInt(0, WIDTH);
            int y = rand.nextInt(0, HEIGHT);
            if (bonusWorld[x][y].equals(Tileset.FLOOR)) {
                bonusWorld[x][y] = Tileset.FLOWER;
                numFlowers++;
            }
        }
        return bonusWorld;
    }
}
