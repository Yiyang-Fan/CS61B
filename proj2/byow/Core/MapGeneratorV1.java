package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * A class contains methods needs to generate a map
 */
public class MapGeneratorV1 {
    private int WIDTH;
    private int HEIGHT;
    private long SEED;
    private Random RANDOM;
    private TETile[][] WORLD;
    TERenderer TER;

    /**
     * Constructor
     * @param width: Width of the world
     * @param height: Height of the world
     * @param seed: Seed of the randomizer
     */
    public MapGeneratorV1(int width, int height, int seed){
        WIDTH = width;
        HEIGHT = height;
        SEED = seed;
        RANDOM = new Random(SEED);
        WORLD = new TETile[WIDTH][HEIGHT];
        TER = new TERenderer();
        TER.initialize(WIDTH, HEIGHT);

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                WORLD[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Check if the space is valid to add a tile
     * @param x: x coordinate
     * @param y: y coordinate
     */
    private void checkValidSpace(int x, int y) {
        if (WORLD[x][y] != null && !WORLD[x][y].equals(Tileset.NOTHING)) {
            throw new IllegalStateException("Rectangle in the wrong place, " +
                    "creating will modify other object");
        }
        if (x < 0 || x > WIDTH - 1) {
            throw new IllegalStateException("Rectangle exceed x axis or leave no space for wall");
        }
        if (y < 0 || y > HEIGHT - 1) {
            throw new IllegalStateException("Rectangle exceed y axis or leave no space for wall");
        }
    }

    /**
     * Add a rectangle without wall to the world. The left low corner is WORLD[x][y]
     * @param x: the x-coordinate of the left edge
     * @param y: the y-coordinate of the lower side
     * @param width: width of the rectangle
     * @param height: height of the rectangle
     * @param tile: tile of the rectangle
     */
    public void addBareRectangle(int x, int y, int width, int height, TETile tile) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                checkValidSpace(x + i, y + j);
                WORLD[x + i][y + j] = tile;
            }
        }
    }

    // Add wall to rectangle
    public void addWallToRectangle(int x, int y, int width, int height, TETile tile) {
        for (int j = 0; j < height; j++) {
            checkValidSpace(x - 1, y + j);
            checkValidSpace(x + width, y + j);
            WORLD[x - 1][y + j] = Tileset.WALL;
            WORLD[x + width][y + j] = Tileset.WALL;
        }
        for (int i = -1; i < width + 1; i++) {
            checkValidSpace(x + i, y - 1);
            checkValidSpace(x + i, y + height);
            WORLD[x + i][y - 1] = Tileset.WALL;
            WORLD[x + i][y + height] = Tileset.WALL;
        }
    }

    /**
     * Add a rectangle with wall to the world. The left low corner is WORLD[x][y]
     */
    public void addRectangle(int x, int y, int width, int height, TETile tile) {
        addBareRectangle(x, y, width, height, tile);
        addWallToRectangle(x, y, width, height, tile);
    }

    /**
     * Add Right hall way
     * @param x: the x-coordinate of the left point
     * @param y: the y-coordinate of the left point
     * @param width: the width of this hall way
     * @param tile: the tile of the hall way
     */
    public void addRightHallWay(int x, int y, int width, TETile tile) {
        if (width <= 1) {
            throw new IllegalArgumentException("Length should larger than 1");
        }
        addBareRectangle(x, y, width, 1, tile);
        for (int i = 1; i < width - 1; i++) {
            checkValidSpace(x + i, y + 1);
            checkValidSpace(x + i, y - 1);
            WORLD[x + i][y + 1] = Tileset.WALL;
            WORLD[x + i][y - 1] = Tileset.WALL;
        }
    }

    /**
     * Add Left hall way
     * @param x: the x-coordinate of the right point
     * @param y: the y-coordinate of the right point
     * @param width: the width of this hall way
     * @param tile: the tile of the hall way
     */
    public void addLeftHallWay(int x, int y, int width, TETile tile) {
        if (width <= 1) {
            throw new IllegalArgumentException("Length should larger than 1");
        }
        addBareRectangle(x - width + 1, y, width, 1, tile);
        for (int i = 1; i < width - 1; i++) {
            checkValidSpace(x - i, y + 1);
            checkValidSpace(x - i, y - 1);
            WORLD[x - i][y + 1] = Tileset.WALL;
            WORLD[x - i][y - 1] = Tileset.WALL;
        }
    }

    /**
     * Add Up hall way
     * @param x: the x-coordinate of the lower point
     * @param y: the y-coordinate of the lower point
     * @param height: the height of this hall way
     * @param tile: the tile of the hall way
     */
    public void addUpHallWay(int x, int y, int height, TETile tile) {
        if (height <= 1) {
            throw new IllegalArgumentException("Height should larger than 1");
        }
        addBareRectangle(x, y, 1, height, tile);
        for (int j = 1; j < height - 1; j++) {
            checkValidSpace(x - 1, y + j);
            checkValidSpace(x + 1, y + j);
            WORLD[x + 1][y + j] = Tileset.WALL;
            WORLD[x - 1][y + j] = Tileset.WALL;
        }
    }

    /**
     * Add Down hall way
     * @param x: the x-coordinate of the Upper point
     * @param y: the y-coordinate of the Upper point
     * @param height: the height of this hall way
     * @param tile: the tile of the hall way
     */
    public void addDownHallWay(int x, int y, int height, TETile tile) {
        if (height <= 1) {
            throw new IllegalArgumentException("Height should larger than 1");
        }
        addBareRectangle(x, y - height + 1, 1, height, tile);
        for (int j = 1; j < height - 1; j++) {
            checkValidSpace(x - 1, y - j);
            checkValidSpace(x + 1, y - j);
            WORLD[x + 1][y - j] = Tileset.WALL;
            WORLD[x - 1][y - j] = Tileset.WALL;
        }
    }

    /**
     * Add a hallway
     * @param x: x coordinate of start point of the hallway
     * @param y: y coordinate of start point of the hallway
     * @param length: length of the hallway
     * @param dir: direction of the hallway, up, down, left, right
     */
    public void addHallWay(int x, int y, int length, String dir, TETile tile) {
        if (dir.equals("up")) {
            addUpHallWay(x, y, length, tile);
        } else if (dir.equals("down")) {
            addDownHallWay(x, y, length, tile);
        } else if (dir.equals("left")) {
            addLeftHallWay(x, y, length, tile);
        } else {
            addRightHallWay(x, y, length, tile);
        }
    }

    // generate the world
    public void generatWorld() {
        TER.renderFrame(WORLD);
    }


}
