package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.Tileset;

import java.nio.file.WatchEvent;

/**
 * Test the MapGenerator
 */
public class MapMain {
    public static void main(String[] args) {
        int WIDTH, HEIGHT;
        WIDTH = 100;
        HEIGHT = 60;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        MapGenerator mg = new MapGenerator(WIDTH, HEIGHT, 2342);
        World map = mg.buildMap(5, 1, 25, 31, Tileset.FLOOR);
        Hero h = new Hero(27, 3, map, Tileset.FLOOR, Tileset.AVATAR);
        ter.renderFrame(map.FIELD);
        pause(500);
        h.move('w');
        ter.renderFrame(map.FIELD);
        pause(500);
        /*
        MapGenerator mg = new MapGenerator(80, 60, 2342);
        mg.addRectangle(33, 35, 5, 10, Tileset.SAND);
        //mg.addRectangle(3, 18, 5, 10, Tileset.SAND);
        //mg.addRightHallWay(8, 6, 12, Tileset.WATER);
        //mg.addLeftHallWay(12 ,29, 12, Tileset.WATER);
        mg.addUpHallWay(4, 15, 13, Tileset.WATER);
        mg.addDownHallWay(9, 15, 13, Tileset.WATER);
        mg.addHallWay(5, 2, 3, "up", Tileset.FLOWER);
        mg.generatWorld();
         */
    }
    private static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
