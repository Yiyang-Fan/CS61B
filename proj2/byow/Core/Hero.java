package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hero {
    private World world;
    private int x;
    private int y;
    private TETile floor;
    private TETile hero;
    private TETile under;
    public Hero(int x, int y, World world, TETile floor, TETile hero) {
        if (x < 0 || x >= world.width() || y < 0 || y >= world.height()) {
            throw new IllegalArgumentException("Bad place to place hero. Exceeding the world!");
        }
        if (world.FIELD[x][y] != floor) {
            throw new IllegalArgumentException("Bad place to place hero. Place on the floor!");
        }
        world.FIELD[x][y] = hero;
        this.x = x;
        this.y = y;
        this.world = world;
        this.hero = hero;
        this.under = floor;
    }
    public boolean move(char dir) {
        int dx = 0;
        int dy = 0;
        int tx, ty;
        if (dir == 'w') {
            dx = 0;
            dy = 1;
        }
        if (dir == 's') {
            dx = 0;
            dy = -1;
        }
        if (dir == 'a') {
            dx = -1;
            dy = 0;
        }
        if (dir == 'd') {
            dx = 1;
            dy = 0;
        }
        tx = x + dx;
        ty = y + dy;
        if (world.FIELD[tx][ty] != Tileset.WALL) {
            changePosition(tx, ty);
            return true;
        }
        System.out.println("IllegalMove");
        return false;
    }
    private void changePosition(int nx, int ny) {
        TETile next = world.FIELD[nx][ny];
        world.FIELD[x][y] = under;
        x = nx;
        y = ny;
        world.FIELD[nx][ny] = hero;
        under = next;
    }

}
