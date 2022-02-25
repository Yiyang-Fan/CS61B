package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Set;

/**
 * A class of rectangle
 */
public class Rectangle {
    // y coordinate of upside edge of the rectangle -1
    private int up;
    // y coordinate of downside edge of the rectangle
    private int down;
    // x coordinate of left side edge of the rectangle
    private int left;
    // x coordinate of right side edge of the rectangle -1
    private int right;

    // the world rectangle belong
    public World world;
    // tile type of the rectangle
    private TETile tile;


    /**
     * Constructor
     * Pre:
     *  The rectangle with wall should not exceed the world. Otherwise, throw Illegal StateException
     */
    public Rectangle(int up, int down, int left, int right, World world, TETile tile) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.world = world;
        this.tile = tile;
    }

    public int up() {
        return up;
    }
    public int down() {
        return down;
    }
    public int left() {
        return left;
    }
    public int right() {
        return right;
    }
    public TETile tile() {
        return tile;
    }

    // Return true if it is safe to build the rectangle
    public boolean goodRec() {
        if (left <= 0 || down <= 0 || right >= world.width() || up >= world.height()) {
            return false;
        }
        for (int i = left; i < right; i++) {
            for (int j = down; j < up; j++) {
                if (world.FIELD[i][j] != null && !world.FIELD[i][j].equals(Tileset.NOTHING)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Add the rectangle with wall to the world.
     */
    public void build() {
        buildBare();
        buildWall();
    }

    /**
     * Add a hall way to the rectangle.
     * Pre:
     *   add a hallway but not a rectangle. Otherwise, throw IllegalStateException
     */
    public void addHallway(HallWay rec) {
        if (!rec.isHallway()) {
            throw new IllegalStateException("Try to add a rectangle not hallway");
        }
        if (rec.getDirection() == 'h') {
            if (rec.left() == right) {
                rec.build("right");
            } else if (rec.right() == left) {
                rec.build("left");
            }

        } else {
            if (rec.down() == up) {
                rec.build("up");
            } else if (rec.up() == down) {
                rec.build("down");
            }
        }
    }

    // Remove the wall to add the hallway
    public void removeWallAdjHallWay(HallWay rec) {
        if (!rec.isHallway()) {
            throw new IllegalStateException("Try to add a rectangle not hallway");
        }
        if (rec.up() - rec.down() == 1) {
            if (rec.left() == right && world.FIELD[right][rec.down()] == Tileset.WALL) {
                world.FIELD[right][rec.down()] = Tileset.NOTHING;
            } else if (rec.right() == left && world.FIELD[left - 1][rec.down()] == Tileset.WALL) {
                world.FIELD[left - 1][rec.down()] = Tileset.NOTHING;
            }

        } else if (rec.right() - rec.left() == 1) {
            if (rec.down() == up && world.FIELD[rec.left()][up] == Tileset.WALL) {
                world.FIELD[rec.left()][up] = Tileset.NOTHING;
            } else if (rec.up() == down && world.FIELD[rec.left()][down - 1] == Tileset.WALL) {
                world.FIELD[rec.left()][down - 1] = Tileset.NOTHING;
            }
        }
    }

    // Add back the wall which is removed to build the hallway
    public void addWallAdjHallWay(HallWay rec) {
        if (!rec.isHallway()) {
            throw new IllegalStateException("Try to add a rectangle not hallway");
        }
        if (rec.up() - rec.down() == 1) {
            if (rec.left() == right) {
                if (world.FIELD[right][rec.down()] == Tileset.NOTHING) {
                    world.FIELD[right][rec.down()] = Tileset.WALL;
                }
            } else if (rec.right() == left) {
                if (world.FIELD[left - 1][rec.down()] == Tileset.NOTHING) {
                    world.FIELD[left - 1][rec.down()] = Tileset.WALL;
                }
            }

        } else if (rec.right() - rec.left() == 1) {
            if (rec.down() == up) {
                if (world.FIELD[rec.left()][up] == Tileset.NOTHING) {
                    world.FIELD[rec.left()][up] = Tileset.WALL;
                }
            } else if (rec.up() == down) {
                if (world.FIELD[rec.left()][down - 1] == Tileset.NOTHING) {
                    world.FIELD[rec.left()][down - 1] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * Return if the rectangle is a hallway.
     */
    public boolean isHallway() {
        if (right - left == 1 || up - down == 1) {
            return true;
        }
        return false;
    }

    /**
     * Check if the space is valid to add a tile
     */
    private void checkValidSpace(int x, int y, TETile[][] WORLD) {
        if (WORLD[x][y] != null && !WORLD[x][y].equals(Tileset.NOTHING)) {
            throw new IllegalStateException("Rectangle in the wrong place, " +
                    "creating will modify other object");
        }
    }

    /**
     * Check if the rectangle is valid to build
     */
    private void checkRec() {
        if (left <= 0 || down <= 0 || right >= world.width() || up >= world.height()) {
            throw new IllegalStateException("Rectangle exceeding the world");
        }
        for (int i = left; i < right; i++) {
            for (int j = down; j < up; j++) {
                checkValidSpace(i, j, world.FIELD);
            }
        }
    }

    /**
     * Pre:
     *  The space is not occupied. Otherwise, throw IllegalStateException
     * Add the rectangle without wall to the world.
     */
    private void buildBare() {
        checkRec();
        for (int i = left; i < right; i++) {
            for (int j = down; j < up; j++) {
                world.FIELD[i][j] = tile;
            }
        }
    }

    /**
     * Add the wall of this rectangle
     */
    private void buildWall() {
        for (int i = left - 1; i <= right; i++) {
            if (world.FIELD[i][up].equals(Tileset.NOTHING)) {
                world.FIELD[i][up] = Tileset.WALL;
            }
            if (world.FIELD[i][down - 1].equals(Tileset.NOTHING)) {
                world.FIELD[i][down - 1] = Tileset.WALL;
            }
        }
        for (int j = down - 1; j <= up; j++) {
            if (world.FIELD[left - 1][j].equals(Tileset.NOTHING)) {
                world.FIELD[left - 1][j] = Tileset.WALL;
            }
            if (world.FIELD[right][j].equals(Tileset.NOTHING)) {
                world.FIELD[right][j] = Tileset.WALL;
            }
        }
    }

}
