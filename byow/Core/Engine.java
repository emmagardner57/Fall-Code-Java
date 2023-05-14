package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Out;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import java.util.Random;
import java.util.ArrayList;

import java.awt.*;
import java.util.*;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 90;
    public static final int HEIGHT = 50;

    public static ArrayList<Character> actionTracker = new ArrayList<>();






    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        TETile[][] loadedWorld = new TETile[WIDTH][HEIGHT];
        boolean done = false;
        ter.initialize(WIDTH, HEIGHT + 10);
        mainMenu menu = new mainMenu(HEIGHT, WIDTH);
        menu.firstScreen();
        boolean choosen = false;
        while (!choosen) {
            if (StdDraw.hasNextKeyTyped()) {
                char mo = StdDraw.nextKeyTyped();
                if (mo == 'L' || mo == 'l') {
                    choosen = true;
                    loadedWorld = interactWithInputString("savedWorld.txt");
                    return;

                }
                else if (mo == 'N' || mo == 'n') {
                    clearFile();
                    menu.enterSeed();
                    choosen = true;
                }
                else if (mo == 'Q' || mo == 'q') {
                    choosen = true;
                    return;
                }
            }
        }
        menu.enterSeed();
        boolean finishedSeed = false;
        String userInput = "";
        ArrayList<Integer> seedList = new ArrayList<>();
        boolean afterN = false;
        String written = new String();
        while (!finishedSeed) {
            while (StdDraw.hasNextKeyTyped()) {
                char letter = StdDraw.nextKeyTyped();
                userInput += letter;
                actionTracker.add(letter); // saving seed chars to arrayList
                if (letter == 's'|| letter == 'S') {
                    finishedSeed = true;
                }
                else if (afterN && Character.isDigit(letter)) {
                    int num = letter;
                    seedList.add(num);
                    if (seedList.size() <= 1) {
                        written = "n";
                    }
                    written = written + letter;
                    menu.enteringSeed(written);
                }
                else if (letter == 'n') {
                    afterN = true;
                    written = "n";
                    menu.enteringSeed(written);
                }

            }
        }
        String result = "";

        for (int i = 0; i < userInput.length(); i++) {
            Character character = userInput.charAt(i);
            if (Character.isDigit(character)) {
                result += character;
            }
        }
        int seed = Integer.parseInt(result);
        System.out.println(seed);

        ter.initialize(WIDTH, HEIGHT + 5);
        TETile[][] world= new TETile[WIDTH][HEIGHT];
        for (int w = 0; w < WIDTH; w++) {
            for (int h = 0; h < HEIGHT; h++) {
                world[w][h] = Tileset.NOTHING;
            }
        }

        StdDraw.setFont();
        worldGenerator maker = new worldGenerator(world, seed, HEIGHT, WIDTH, ter);
        world = maker.makeWorld();
        avatarMovement avatar = new avatarMovement(world, HEIGHT, WIDTH, ter, maker.rand);

        ter.renderFrame(world);
        typed ty = new typed(world, avatar, ter, HEIGHT, WIDTH);
        while (!done) {
            ty.moveAv();
            ty.giveDesc();
            StdDraw.setFont();
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        boolean done = false;
      // parse and store seed in an int list
        int x = WIDTH;
        int y = HEIGHT;
     //  In in = new In(input);
        if (input.length() < 3) {
            return null;
        }
        In in = new In(input);
        String line =  in.readAll();
        in.close();
        if (line.length() == 0) {
            System.exit(0);
        }
        boolean firstS = false;
        String result = "";
        String keyPresses = "";
        for (int i = 0; i < line.length(); i++) {
            Character character = line.charAt(i);
            if (Character.isDigit(character)) {
                result += character;
            } else if (character != 'n') {
                if ((character == 's' || character == 'S') && firstS == false) {
                    firstS = true;
                } else {
                    keyPresses += character;
                }
            }
        }
        int seed = Integer.parseInt(result);
        System.out.println(seed);

        ter.initialize(WIDTH, HEIGHT + 5);
        TETile[][] world= new TETile[WIDTH][HEIGHT];
        for (int w = 0; w < WIDTH; w++) {
            for (int h = 0; h < HEIGHT; h++) {
                world[w][h] = Tileset.NOTHING;
            }
        }
      //  ter.renderFrame(world);

        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
      /**  worldGenerator maker = new worldGenerator(world, seed, HEIGHT, WIDTH, ter);
        world = maker.makeWorld();
        avatarMovement avatar = new avatarMovement(world, HEIGHT, WIDTH, ter, maker.rand); **/

       // // doesnt reach this prob bc rand is null



     /**   for (int i = 0; i < keyPresses.length(); i++) { //for loop
            char typedChar = keyPresses.charAt(i);
            world = avatar.moveAvatar(typedChar);

        } **/
        StdDraw.setFont();


        actionTracker.clear();
        TETile[][] finalWorldFrame = world;

        worldGenerator maker = new worldGenerator(world, seed, HEIGHT, WIDTH, ter);
        world = maker.makeWorld();
        avatarMovement avatar = new avatarMovement(world, HEIGHT, WIDTH, ter, maker.rand);

        for (int i = 0; i < keyPresses.length(); i++) { //for loop
            char typedChar = keyPresses.charAt(i);
            world = avatar.moveAvatar(typedChar);

        }

        ter.renderFrame(world);
        typed ty = new typed(world, avatar, ter, HEIGHT, WIDTH);
        while (!done) {
            ty.moveAv();
            ty.giveDesc();
            StdDraw.setFont();
        }


        return finalWorldFrame;

    } //nothing being added to action tracker in round 2??

        public static void saveGame() {
        // write seed into a file
        //read it and then call interact w input string on it
          //  System.out.println("Writing to " + "savedWorld;

            In inFirst = new In("savedWorld.txt");
            String prevData = inFirst.readAll();
            System.out.print("previous data " + prevData);




            Out out = new Out("savedWorld.txt");
            out.print(prevData);
            for (char action: actionTracker) {
                out.print(action);
            }



            In in = new In("savedWorld.txt");
            String s = in.readAll();
            System.out.print(s);

            actionTracker.clear();

        }

        public static void clearFile(){
            Out out = new Out("savedWorld.txt");
            out.print();
            out.close();


        }


       public static void main(String[] args){
       //  Engine test = new Engine();

         //

           System.out.print("");



       }


}