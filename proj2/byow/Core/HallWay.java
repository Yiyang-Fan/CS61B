package byow.Core;

import byow.TileEngine.TETile;

public class HallWay extends Rectangle{
    /**
     * Constructor
     * Pre:
     * The rectangle with wall should not exceed the world. Otherwise, throw Illegal StateException
     *
     * @param up
     * @param down
     * @param left
     * @param right
     * @param world
     * @param tile
     */
    // A char represents the direction of the hallway, 'v' is vertical, 'h' is horizontal
    private char direction;

    // An int represents the available end to add a rectangle.
    // For vertical, 0 means lower side is available, 1 means upper side is available
    // For horizontal, 0 means left side is available, 1 means right side is available
    // -1 means both end not available
    private int availableEnd;

    public HallWay(int up, int down, int left, int right, World world, TETile tile) {
        super(up, down, left, right, world, tile);
        if (right - left == 1) {
            direction = 'v';
        } else if (up - down == 1) {
            direction = 'h';
        } else {
            throw new IllegalArgumentException("This isn't a hall way");
        }
    }

    public void build(String dir) {
        if (dir.equals("up") || dir.equals("right")) {
            availableEnd = 1;
        } else if (dir.equals("left") || dir.equals("down")) {
            availableEnd = 0;
        } else {
            throw new IllegalArgumentException("direction not known, should be up, down, left, right");
        }
        super.build();
    }

    public char getDirection() {
        return direction;
    }

    public void occupied() {
        availableEnd = -1;
    }

    public int getAvailableEnd() {
        return availableEnd;
    }
}
