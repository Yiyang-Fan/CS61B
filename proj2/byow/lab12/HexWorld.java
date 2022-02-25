package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int startX = 16;
        int startY = 16;
        int size = 4;
        Queue<Node> containQ = new LinkedList<>();
        recurAddHexagon(world, startX, startY, size, containQ);

        /*
        addHexagon(world, startX, startY, size, Tileset.WATER);
        addHexagon(world, startX + 2 * size - 1, startY + size, size, Tileset.WALL);
        addHexagon(world, startX + 2 * size - 1, startY - size, size, Tileset.GRASS);
        addHexagon(world, startX - 2 * size + 1, startY - size, size, Tileset.FLOWER);
        addHexagon(world, startX - 2 * size + 1, startY + size, size, Tileset.WATER);

        */
        // draws the world to the screen
        ter.renderFrame(world);
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(8);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.AVATAR;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.WATER;
            case 5: return Tileset.MOUNTAIN;
            case 6: return Tileset.SAND;
            case 7: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }
    private static class Node {
        private int x;
        private int y;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean equals(int x, int y) {
            if (this.x == x && this.y == y) {
                return true;
            }
            return false;
        }
    }

    public static void recurAddHexagon(TETile[][] world, int x, int y, int size, Queue<Node> containQue) {
        if (validStart(x, y, size) && !startContains(x, y, containQue)) {
            addHexagon(world, x, y, size, randomTile());
            containQue.add(new Node(x, y));
            int[] xCandidate = {x + 2 * size - 1, x + 2 * size - 1, x - 2 * size + 1, x - 2 * size + 1};
            int[] yCandidate = {y + size, y - size, y + size, y - size};
            for (int i = 0; i < xCandidate.length; i++) {
                int xTemp = xCandidate[i];
                int yTemp = yCandidate[i];
                recurAddHexagon(world, xTemp, yTemp, size, containQue);
            }
        }
    }

    private static boolean startContains(int x, int y, Queue<Node> containQue) {
        for (int i = 0; i < containQue.size(); i++) {
            Node t = containQue.remove();
            containQue.add(t);
            if (t.equals(x, y)) {
                return true;
            }
        }
        return false;
    }

    // return false if the left low point of the hexagon will lead to a hexagon exceed the world
    public static boolean validStart(int x, int y, int size) {
        int maxLineLength = 3 * size - 2;
        if (y - size + 1 < 0 || y + size + 1 > HEIGHT) {
            return false;
        }
        if (x < 0 || x + maxLineLength > WIDTH) {
            return false;
        }
        return true;
    }
    // Add a Hexagon with the given size and tile to the world. size is the length of the lowest edge
    // The left low point of the hexagon is (x, y)
    public static void addHexagon(TETile[][] world, int x, int y, int size, TETile tile) {
        int maxLineLength = 3 * size - 2;
        if (!validStart(x, y, size)) {
            throw new IllegalArgumentException("Left Low Point not valid");
        }
        for (int i = 0; i < size; i++) {
            addLine(world, x, y - i, maxLineLength, i, tile);
        }
        for (int i = 0; i < size; i++) {
            addLine(world, x, y + 1 + i, maxLineLength, i, tile);
        }
    }

    // Add a Line start at x, y, for the size hexagon, with given left and right space
    public static void addLine(TETile[][] world, int x, int y, int maxLineLength, int space, TETile tile) {
        for (int i = space; i < maxLineLength - space; i++) {
            if (world[x + i][y] != null && !world[x + i][y].equals(Tileset.NOTHING)) {
                throw new IllegalArgumentException("Space is occupied");
                //System.out.printf("%d  %d \n", x, y);
            }
            world[x + i][y] = tile;
        }
    }

}
