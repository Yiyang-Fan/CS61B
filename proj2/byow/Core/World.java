package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/* A class of the world */
public class World {
    private int WIDTH;
    private int HEIGHT;
    public TETile[][] FIELD;
    public World(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        FIELD = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                FIELD[x][y] = Tileset.NOTHING;
            }
        }
    }
    public int width() {
        return WIDTH;
    }
    public int height() {
        return HEIGHT;
    }
}
