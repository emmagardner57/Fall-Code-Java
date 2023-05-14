package PlusWorld;

import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

import java.util.*;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        createEmtpyBoard(randomTiles);
        addPlus(randomTiles, new Position(20,20), 3);

        ter.renderFrame(randomTiles);
    }

    public static void addPlus(TETile[][] tiles, Position p, int size) {
        for (int horiz = p.coor1; horiz < (size * 3) + p.coor1; horiz++) {
            for (int vert = p.coor2; vert < size + p.coor2; vert++) {
                tiles[horiz][vert] = Tileset.FLOWER;
            }
        }

        p.coor1 += size;
        p.coor2 -= size;

        for (int x = p.coor1; x < size + p.coor1; x++) {
            for (int y = p.coor2; y < (size * 3) + p.coor2; y++) {
                tiles[x][y] = Tileset.FLOWER;
            }
        }
    }
    public static void createEmtpyBoard(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                tiles[w][h] = Tileset.NOTHING;
            }
        }
    }

    private static class Position {
        int coor1;
        int coor2;

        Position (int c1, int c2) {
            coor1 = c1;
            coor2 = c2;
        }

        public Position shift(int x, int y) {
            return new Position(x - coor1, y - coor2);
        }
    }
}
