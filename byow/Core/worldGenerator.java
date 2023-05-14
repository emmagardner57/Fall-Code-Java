package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


import java.util.HashSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Random;

public class worldGenerator {
    public int HEIGHT;
    public int WIDTH;
    public int numRooms;
    public HashMap<Integer, Position> roomCoordinates;
    public HashMap<Integer, Integer> closestRoom;
    public List<Integer> unconnectedRooms;
    public Space avatar;
    public TERenderer ter;

    public int move;
    public static int seed;
    public TETile[][] world;

    public static Random rand;


    public worldGenerator(TETile[][] whirld, int s, int h, int w, TERenderer t) {
        world = whirld;
        seed = s;
        HEIGHT = h;
        WIDTH = w;
        ter = t;


    }


   // creating random inst variable
    public TETile[][] makeWorld() {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
       // rand = new Random(seed); //this may be the problem
        rand = new Random(seed);
        numRooms = 0;
        roomCoordinates = new HashMap<>();
        closestRoom = new HashMap<>();

        for (int i = 0; i < 50; i++) {
            makeRooms(world, rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
        }

        for (int i = 1; i < numRooms && roomCoordinates.get(i) != null; i++) {
            int bestDistance = computeDistance(roomCoordinates.get(i), roomCoordinates.get(i + 1));
            int bestRoom = i + 1;
            for (int j = i + 2; j <= numRooms && roomCoordinates.get(j) != null; j++) {
                int newDistance = computeDistance(roomCoordinates.get(i), roomCoordinates.get(j));
                int newRoom = j;
                if (newDistance < bestDistance) {
                    bestRoom  = newRoom;
                    bestDistance = newDistance;
                }
                if (!closestRoom.containsKey(newRoom) || closestRoom.get(newRoom) < newDistance) {
                    closestRoom.put(newRoom, newDistance);
                }
            }
            closestRoom.put(i, bestRoom);
        }
        if (numRooms > 1) {
            primsAlg(1, world);
        }
        randTrees();


        // draws the world to the screen
        return world;

    }



    public class Position {
        public int startX;
        public int startY;
        public int endX;
        public int endY;
        public Position(int sx, int sy, int ex, int ey) {
            startX = sx;
            startY = sy;
            endX = ex;
            endY = ey;
        }
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

    public void makeRooms(TETile[][] world, int x, int y) {

        //checks if tile at coordinates (x, y) is empty, if not exits out of function
        if (!world[x][y].equals(Tileset.NOTHING) || x >= WIDTH - 4 || y >= HEIGHT - 4) {
            return;
        }

        //creates a random endX value that will be used for x + endX to get the width of the room. The same for endY
        int endX = rand.nextInt(2,15) + x;
        int endY = rand.nextInt(2, 15) + y;

        //Checks if endY + y and endX + x are within the bounds of the world's width and height.
        //If either are false, then decrease endY/endX by 1 and check again
        while (endY >= HEIGHT) {
            endY--;
        }
        while (endX >= WIDTH) {
            endX--;
        }

        //Checks that the tile at coordinates(m)(n) is empty, if it's not, checks if a tile one row down is empty and a tile one column to the left.
        //If either are false, then decrease endY/endX by 1 and check again
        boolean changed = false;
        for (int m = x; m <= endX; m++) {
            for (int n = y; n <= endY; n++) {
                if (!world[m][n].equals(Tileset.NOTHING)) {
                    changed = false;
                    if (m > 0 && world[m - 1][n].equals(Tileset.NOTHING)) {
                        endY = n - 1;
                        changed = true;
                    }
                    if (n > 0 && world[m][n - 1].equals(Tileset.NOTHING)) {
                        endX = m - 1;
                        changed = true;
                    }
                    if (!changed) {
                        return;
                    }
                }
            }
        }

        //checks that there are at least 2 spaces between the start and ends of the room. If not exit this function
        //Must be greater than 3 or else there would be no floor or they would be hallways
        if (endY <= y + 3 || endX <= x + 3) {
            return;
        }

        //creates the walls
        for (int i = x; i <= endX; i++) {
            world[i][y] = Tileset.WALL;
            world[i][endY] = Tileset.WALL;
        }
        for (int t = y; t <= endY; t++) {
            world[x][t] = Tileset.WALL;
            world[endX][t] = Tileset.WALL;
        }

        //creates the floor
        for (int m = x + 1; m < endX; m++) {
            for (int n = y + 1; n < endY; n++) {
                world[m][n] = Tileset.FLOOR;
            }
        }
        numRooms++;
        //only
        Position curr = new Position(x, y, endX, endY);
        roomCoordinates.put(numRooms, curr);
        if (numRooms > 1) {
            unconnectedRooms.add(numRooms);
        }
        else {
            unconnectedRooms = new ArrayList<>();
        }
    }

    public void horizHall(Space startSpace, Space endSpace, TETile[][] world) {
        while (startSpace.x != endSpace.x + 1) {
            world[startSpace.x][startSpace.y] = Tileset.FLOOR;
            int up = startSpace.y + 1;
            int down = startSpace.y - 1;
            if (up < HEIGHT && !world[startSpace.x][up].equals(Tileset.FLOOR)) {
                world[startSpace.x][up] = Tileset.WALL;
            }
            if (down >= 0 && !world[startSpace.x][down].equals(Tileset.FLOOR)) {
                world[startSpace.x][down] = Tileset.WALL;
            }
            startSpace.move(1, 0);
        }
    }
    public void vertHall(Space startSpace, Space endSpace, TETile[][] world) {
        while (startSpace.y != endSpace.y + 1) {
            world[startSpace.x][startSpace.y] = Tileset.FLOOR;
            int right = startSpace.x + 1;
            int left = startSpace.x - 1;
            if (right < WIDTH && !world[right][startSpace.y].equals(Tileset.FLOOR)) {
                world[right][startSpace.y] = Tileset.WALL;
            }
            if (left >= 0 && !world[left][startSpace.y].equals(Tileset.FLOOR)) {
                world[left][startSpace.y] = Tileset.WALL;
            }
            startSpace.move(0, 1);
        }
    }

    public void makeHall(int code1, int code2, TETile[][] world) {
        Position room1 = roomCoordinates.get(code1);
        int x1 = (room1.startX + room1.endX) / 2;
        int y1 = (room1.startY + room1.endY) / 2;
        Position room2 = roomCoordinates.get(code2);
        int x2 = (room2.startX + room2.endX) / 2;
        int y2 = (room2.startY + room2.endY) / 2;
        Space start;
        Space end;

        if (x1 < room2.endX && x1 > room2.startX) {
            if (y1 < y2) {
                start = new Space(x1, y1);
                end = new Space(x1, y2);
            }
            else {
                start = new Space(x1, y2);
                end = new Space(x1, y1);
            }
            vertHall(start, end, world);
        }
        else if (x2 < room1.endX && x2 > room1.startX) {
            if (y1 < y2) {
                start = new Space(x2, y1);
                end = new Space(x2, y2);
            }
            else {
                start = new Space(x2, y2);
                end = new Space(x2, y1);
            }
            vertHall(start, end, world);
        }
        else if (y2 < room1.endY && y2 > room1.startY) {
            if (x1 < x2) {
                start = new Space(x1, y2);
                end = new Space(x2, y2);
            }
            else {
                start = new Space(x2, y2);
                end = new Space(x1, y2);
            }
            horizHall(start, end, world);
        }
        else if (y1 < room2.endY && y1 > room2.startY) {
            if (x1 < x2) {
                start = new Space(x1, y1);
                end = new Space(x2, y1);
            }
            else {
                start = new Space(x2, y1);
                end = new Space(x1, y1);
            }
            horizHall(start, end, world);
        }
        else if (x1 < room2.startX) {
            if (y1 < room2.startY) {
                Space lowLeft = new Space(room1.endX, y1);
                Space highRight = new Space(x2, room2.startY);
                upRight(lowLeft, highRight, world);
            }
            else if (y1 > room2.endY) {
                Space highLeft = new Space(room1.endX, y1);
                Space lowRight = new Space(x2, room2.endY);
                downRight(highLeft, lowRight, world);
            }
        }
        else if (x1 > room2.endX) {
            if (y1 < room2.startY) {
                Space highLeft = new Space(room2.endX, y2);
                Space lowRight = new Space(x1, room1.endY);
                downRight(highLeft, lowRight, world);
            }
            else if (y1 > room2.endY) {
                Space lowLeft = new Space(room2.endX, y2);
                Space highRight = new Space(x1, room1.startY);
                upRight(lowLeft, highRight, world);
            }
        }
    }
    public void primsAlg(int roomCode, TETile[][] world) {
        Position room1 = new Position(roomCoordinates.get(roomCode).startX, roomCoordinates.get(roomCode).endX, roomCoordinates.get(roomCode).startY, roomCoordinates.get(roomCode).endY);
        int closestUnconnectedRoom = 0;
        int closestDis = -1;
        int index = -1;
        for (int p = 0; p < unconnectedRooms.size(); p++) {
            int i = unconnectedRooms.get(p);
            if (roomCoordinates.containsKey(i)) {
                Position room2 = new Position(roomCoordinates.get(i).startX, roomCoordinates.get(i).endX, roomCoordinates.get(i).startY, roomCoordinates.get(i).endY);
                int newDis = computeDistance(room1, room2);
                if (closestDis == -1 || newDis < closestDis || index == -1) {
                    closestDis = newDis;
                    closestUnconnectedRoom = i;
                    index = p;
                }
            }
        }

        makeHall(roomCode, closestUnconnectedRoom, world);
        unconnectedRooms.remove(index);
        if (!unconnectedRooms.isEmpty()) {
            primsAlg(closestUnconnectedRoom, world);
        }



        // draws the world to the screen
        ter.renderFrame(world);

    }

    public void upRight(Space lowLeft, Space highRight, TETile[][] world) {
        int x = highRight.x;
        int y = lowLeft.y - 1;
        horizHall(lowLeft, highRight, world);
        vertHall(lowLeft, highRight, world);
        if (!world[x][y].equals(Tileset.FLOOR)) {
            world[x][y] = Tileset.WALL;
        }
        x++;
        if (!world[x][y].equals(Tileset.FLOOR)) {
            world[x][y] = Tileset.WALL;
        }
        x++;
        if (!world[x][y].equals(Tileset.FLOOR)) {
            world[x][y] = Tileset.WALL;
        }
    }

    public void downRight(Space highLeft, Space lowRight, TETile[][] world) {
        int x = lowRight.x + 1;
        int y = highLeft.y + 1;
        horizHall(highLeft, lowRight, world);
        vertHall(lowRight, highLeft, world);
        if (!world[x][y].equals(Tileset.FLOOR)) {
            world[x][y] = Tileset.WALL;
        }
    }

    public void randTrees() {
        for (int i = 0; i <= 20; i++) {
            int x = rand.nextInt(0, WIDTH);
            int y = rand.nextInt(0, HEIGHT);
            if (world[x][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.TREE;
            }
        }
    }

    public int distanceX(Position room1, Position room2) {
        int x1 = (room1.startX + room1.endX) / 2;
        int x2 = (room2.startX + room2.endX) / 2;
        int distanceX = 0;
        if (x1 > x2) {
            if (room1.startX < room2.endX) {
                distanceX = x1 - x2;
            }
            else {
                distanceX = room1.startX - room2.endX;
            }
        }
        else if (x2 > x1) {
            if (room1.endX > room2.startX) {
                distanceX = x2 - x1;
            }
            else {
                distanceX = room2.startX - room1.endX;
            }
        }
        else {
            distanceX = 0;
        }
        return distanceX;
    }

    public int distanceY(Position room1, Position room2) {
        int y1 = (room1.startY + room1.endY) / 2;
        int y2 = (room2.startY + room2.endY) / 2;
        int distanceY = 0;
        if (y1 > y2) {
            if (room1.startY < room2.endY) {
                distanceY = y1 - y2;
            }
            else {
                distanceY = room1.startY - room2.endY;
            }
        }
        else if (y2 > y1) {
            if (room1.endY > room2.startY) {
                distanceY = y2 - y1;
            }
            else {
                distanceY = room2.startY - room1.endY;
            }
        }
        else {
            distanceY = 0;
        }
        return distanceY;
    }
    public int computeDistance(Position room1, Position room2) {
        int distanceX = distanceX(room1, room2);
        int distance = 0;
        int distanceY = distanceY(room1, room2);
        distance = distanceX + distanceY;
        return distance;
    }
}
