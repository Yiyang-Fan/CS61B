package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MapGenerator {
    private int WIDTH;
    private int HEIGHT;
    private long SEED;
    private Random RANDOM;
    private World WORLD;
    TERenderer TER;

    /**
     * Constructor
     * @param width: Width of the world
     * @param height: Height of the world
     * @param seed: Seed of the randomizer
     */
    public MapGenerator(int width, int height, int seed){
        WIDTH = width;
        HEIGHT = height;
        SEED = seed;
        RANDOM = new Random(SEED);
        WORLD = new World(width, height);
    }

    /**
     * Generate the random map
     * @param up the up edge of initial room
     * @param down the down edge of initial room
     * @param left the left edge of initial room
     * @param right the right edge of initial room
     * @param tile the tile of room and hallway
     */
    public World buildMap(int up, int down, int left, int right, TETile tile) {
        Rectangle r = buildStartRec(up, down, left, right, tile);
        r.build();
        Queue<Rectangle> waitingRec = new LinkedList<>();
        Queue<HallWay> waitingHall = new LinkedList<>();
        waitingRec.add(r);
        for (int i = 0; i < 50; i++) {
            randomGenerateOneRound(waitingRec, waitingHall, tile);
        }
        return WORLD;
    }

    /**
     * Build the initial room
     * @param up the up edge of initial room
     * @param down the down edge of initial room
     * @param left the left edge of initial room
     * @param right the right edge of initial room
     * @param tile the tile of room and hallway
     * @return a Rectangle which is the initial room
     */
    private Rectangle buildStartRec(int up, int down, int left, int right, TETile tile) {
        Rectangle rec = new Rectangle(up, down, left, right, WORLD, tile);
        return rec;
    }

    /**
     * Randomly generate a hallway for the given Rectangle rec. Return the hallway generated
     * The length of hallway is at least 2 and at most 5.
     * The hallway could be generated from each edge of the rectangle.
     * If the generation is failed, return null
     */
    private HallWay recGenerateAdjHallWayRand (Rectangle rec, TETile tile) {
        int d = RANDOM.nextInt(4);
        int length = 2 + RANDOM.nextInt(4);
        double prop = RANDOM.nextDouble();
        String dir = null;
        HallWay result = null;
        switch (d) {
            case 0 :
                dir = "up";
                break;
            case 1 :
                dir = "down";
                break;
            case 2 :
                dir = "left";
                break;
            case 3 :
                dir = "right";
                break;
        }
        result = generateAdjHallWay(rec, dir, length, prop, tile);
        rec.removeWallAdjHallWay(result);
        if (result.goodRec()) {
            rec.addHallway(result);
            return result;
        } else {
            rec.addWallAdjHallWay(result);
            return null;
        }
    }

    /**
     * Randomly generated a rectangle for the given hallway. Return the rectangle generated
     * The width and height of the rectangle is at least 1 and at most 5
     * The rectangle can only be generated from the end of the hallway
     * If the generation is failed, return null.
     */
    private Rectangle hallWayGenerateAdjRecRand(HallWay hallWay, TETile tile) {
        int width = RANDOM.nextInt(5) + 1;
        int height = RANDOM.nextInt(5) + 1;
        double prop = RANDOM.nextDouble();
        Rectangle result = generateAdjRec(hallWay, width, height, prop, tile);
        removeWallAdjRec(hallWay);
        if (result.goodRec()) {
            buildAdjRec(hallWay, result);
            return result;
        } else {
            addWallAdjRec(hallWay);
            return null;
        }
    }

    /**
     * Generate 10 hallways for each new rectangle which hasn't generated any rectangles.
     * And generate one rectangle for each new hallway.
     */
    private void randomGenerateOneRound(Queue<Rectangle> waitingRec, Queue<HallWay> waitingHall, TETile tile) {
        Rectangle r = null;
        HallWay h = null;
        while (!waitingRec.isEmpty()) {
            r = waitingRec.remove();
            if (r != null) {
                for (int i = 1; i < 10; i++) {
                    h = recGenerateAdjHallWayRand(r, tile);
                    waitingHall.add(h);
                }
            }
        }
        while (!waitingHall.isEmpty()) {
            h = waitingHall.remove();
            if (h != null) {
                r = hallWayGenerateAdjRecRand(h, tile);
                waitingRec.add(r);
            }
        }
    }

    /**
     * Generate an adjacent hallway for a given rectangle on the edge of given direction. Length and start point
     * of proportion on the edge is given.
     */
    private HallWay generateAdjHallWay(Rectangle rec, String dir, int length, double prop, TETile tile) {
        HallWay hallWay;
        int hallUp;
        int hallDown;
        int hallLeft;
        int hallRight;
        int tmp;
        if (dir.equals("up")) {
            tmp = (int) Math.floor(prop * (rec.right() - rec.left()));
            hallUp = rec.up() + length;
            hallDown = rec.up();
            hallLeft = rec.left() + tmp;
            hallRight = rec.left() + tmp + 1;
        } else if (dir.equals("down")) {
            tmp = (int) Math.floor(prop * (rec.right() - rec.left()));
            hallUp = rec.down();
            hallDown = rec.down() - length;
            hallLeft = rec.left() + tmp;
            hallRight = rec.left() + tmp + 1;
        } else if (dir.equals("left")) {
            tmp = (int) Math.floor(prop * (rec.up() - rec.down()));
            hallUp = rec.down() + tmp + 1;
            hallDown = rec.down() + tmp;
            hallLeft = rec.left() - length;
            hallRight = rec.left();
        } else {
            tmp = (int) Math.floor(prop * (rec.up() - rec.down()));
            hallUp = rec.down() + tmp + 1;
            hallDown = rec.down() + tmp;
            hallLeft = rec.right();
            hallRight = rec.right() + length;
        }
        hallWay = new HallWay(hallUp, hallDown, hallLeft, hallRight, rec.world, tile);
        return hallWay;
    }

    // Remove the wall to build the adjacent rectangle for the hallway
    private void removeWallAdjRec (HallWay hallWay) {
        int remove;
        if (hallWay.getDirection() == 'v') {
            if (hallWay.getAvailableEnd() == 0) {
                remove = hallWay.down() - 1;
            } else {
                remove = hallWay.up();
            }
            if (hallWay.world.FIELD[hallWay.left()][remove] == Tileset.WALL) {
                hallWay.world.FIELD[hallWay.left()][remove] = Tileset.NOTHING;
            }
            if (hallWay.world.FIELD[hallWay.left() - 1][remove] == Tileset.WALL) {
                hallWay.world.FIELD[hallWay.left() - 1][remove] = Tileset.NOTHING;
            }
            if (hallWay.world.FIELD[hallWay.left() + 1][remove] == Tileset.WALL) {
                hallWay.world.FIELD[hallWay.left() + 1][remove] = Tileset.NOTHING;
            }
        } else if (hallWay.getDirection() == 'h') {
            if (hallWay.getAvailableEnd() == 1) {
                remove = hallWay.right();
            } else {
                remove = hallWay.left() - 1;
            }
            if (hallWay.world.FIELD[remove][hallWay.down()] == Tileset.WALL) {
                hallWay.world.FIELD[remove][hallWay.down()] = Tileset.NOTHING;
            }
            if (hallWay.world.FIELD[remove][hallWay.down() - 1] == Tileset.WALL) {
                hallWay.world.FIELD[remove][hallWay.down() - 1] = Tileset.NOTHING;
            }
            if (hallWay.world.FIELD[remove][hallWay.down() + 1] == Tileset.WALL) {
                hallWay.world.FIELD[remove][hallWay.down() + 1] = Tileset.NOTHING;
            }
        }
    }

    // Add back the wall for hallway
    private void addWallAdjRec (HallWay hallWay) {
        int remove;
        if (hallWay.getDirection() == 'v') {
            if (hallWay.getAvailableEnd() == 0) {
                remove = hallWay.down() - 1;
            } else {
                remove = hallWay.up();
            }
            if (hallWay.world.FIELD[hallWay.left()][remove] == Tileset.NOTHING) {
                hallWay.world.FIELD[hallWay.left()][remove] = Tileset.WALL;
            }
            if (hallWay.world.FIELD[hallWay.left() - 1][remove] == Tileset.NOTHING) {
                hallWay.world.FIELD[hallWay.left() - 1][remove] = Tileset.WALL;
            }
            if (hallWay.world.FIELD[hallWay.left() + 1][remove] == Tileset.NOTHING) {
                hallWay.world.FIELD[hallWay.left() + 1][remove] = Tileset.WALL;
            }
        } else if (hallWay.getDirection() == 'h') {
            if (hallWay.getAvailableEnd() == 1) {
                remove = hallWay.right();
            } else {
                remove = hallWay.left() - 1;
            }
            if (hallWay.world.FIELD[remove][hallWay.down()] == Tileset.NOTHING) {
                hallWay.world.FIELD[remove][hallWay.down()] = Tileset.WALL;
            }
            if (hallWay.world.FIELD[remove][hallWay.down() - 1] == Tileset.NOTHING) {
                hallWay.world.FIELD[remove][hallWay.down() - 1] = Tileset.WALL;
            }
            if (hallWay.world.FIELD[remove][hallWay.down() + 1] == Tileset.NOTHING) {
                hallWay.world.FIELD[remove][hallWay.down() + 1] = Tileset.WALL;
            }
        }
    }

    // Build the adjacent rectangle for the hallway
    private void buildAdjRec (HallWay hallWay, Rectangle rec) {
        rec.build();
        hallWay.occupied();
    }

    // Generate the adjacent rectangle for a hallway given the width, height and the position
    // Return the rectangle
    private Rectangle generateAdjRec(HallWay hallWay, int width, int height, double prop, TETile tile) {
        Rectangle rec = null;
        int up, down, left, right;
        if (hallWay.getDirection() == 'v') {
            left = (int) (hallWay.left() - Math.floor(prop * width));
            right = left + width;
            if (hallWay.getAvailableEnd() == 0) {
                up = hallWay.down();
                down = up - height;
            } else if (hallWay.getAvailableEnd() == 1) {
                down = hallWay.up();
                up = down + height;
            } else {
                return null;
            }
            rec = new Rectangle(up, down, left, right, WORLD, tile);
        } else if (hallWay.getDirection() == 'h') {
            down = (int) (hallWay.down() - Math.floor(prop * height));
            up = down + height;
            if (hallWay.getAvailableEnd() == 1) {
                left = hallWay.right();
                right = left + width;
            } else if (hallWay.getAvailableEnd() == 0) {
                right = hallWay.left();
                left = right - width;
            } else {
                return null;
            }
            rec = new Rectangle(up, down, left, right, WORLD, tile);
        }
        return rec;
    }

}
