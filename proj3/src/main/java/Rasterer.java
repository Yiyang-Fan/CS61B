import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private static final int[] edgeTiles = {1, 2, 4, 8, 16, 32, 64, 128};

    private double diffLon;
    private double diffLat;
    private double[] LonDPPs;

    /**
     public static void main(String[] args) {
     Map<String, Double> params = new HashMap<>();
     params.put("lrlon", -122.20908713544797);
     params.put("ullon", -122.3027284165759);
     params.put("w", 305.0);
     params.put("h", 300.0);
     params.put("ullat", 37.88708748276975);
     params.put("lrlat", 37.848731523430196);
     Rasterer rs = new Rasterer();
     Map<String, Object> r = rs.getMapRaster(params);
     System.out.println(r);
     }
     **/
    public Rasterer() {
        diffLon = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        diffLat = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        LonDPPs = new double[edgeTiles.length];
        for (int i = 0; i < LonDPPs.length; i++) {
            LonDPPs[i] = diffLon / (edgeTiles[i] * MapServer.TILE_SIZE * 1.0);
        }
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        int depth = getRasterLevel(params);

        double lonEachTile = diffLon / edgeTiles[depth];
        double latEachTile = diffLat / edgeTiles[depth];
        int colTileStart = Math.min(Math.max(((int) Math.floor((params.get("ullon") - MapServer.ROOT_ULLON) / lonEachTile)), 0), edgeTiles[depth] - 1);
        int colTileEnd = Math.min(Math.max(((int) Math.ceil((params.get("lrlon") - MapServer.ROOT_ULLON) / lonEachTile)), 0), edgeTiles[depth]);
        int rowTileStart = Math.min(Math.max(((int) Math.floor((MapServer.ROOT_ULLAT - params.get("ullat")) / latEachTile)), 0), edgeTiles[depth] - 1);
        int rowTileEnd = Math.min(Math.max(((int) Math.ceil((MapServer.ROOT_ULLAT - params.get("lrlat")) / latEachTile)), 0), edgeTiles[depth]);
        boolean query_success = getQueryStatus(params);

        Map<String, Object> results = new HashMap<>();

        if (query_success) {
            updateSuccessResult(results, lonEachTile, latEachTile, colTileStart, colTileEnd, rowTileStart, rowTileEnd, depth);
        } else {
            updateFailReuslt(results, depth);
        }

        return results;
    }

    private int getRasterLevel(Map<String , Double> params) {
        double rasterLonDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");
        int depth = -1;
        for (int i = 0; i < LonDPPs.length; i++) {
            if (rasterLonDPP >= LonDPPs[i]) {
                depth = i;
                break;
            }
        }
        if (depth == -1) {
            depth = LonDPPs.length - 1;
        }
        return depth;
    }

    private boolean getQueryStatus(Map<String, Double> params) {
        boolean query_success = true;
        if (
                params.get("ullon") > MapServer.ROOT_LRLON
                        || params.get("ullat") < MapServer.ROOT_LRLAT
                        || params.get("lrlon") < MapServer.ROOT_ULLON
                        || params.get("lrlat") > MapServer.ROOT_ULLAT
        ) {
            query_success = false;
        }
        return query_success;
    }

    private void updateSuccessResult(
            Map<String, Object> results,
            double lonEachTile,
            double latEachTile,
            int colTileStart,
            int colTileEnd,
            int rowTileStart,
            int rowTileEnd,
            int depth
    ) {
        double raster_ul_lon = MapServer.ROOT_ULLON + colTileStart * lonEachTile;
        double raster_lr_lon = MapServer.ROOT_ULLON + colTileEnd * lonEachTile;
        double raster_ul_lat = MapServer.ROOT_ULLAT - rowTileStart * latEachTile;
        double raster_lr_lat = MapServer.ROOT_ULLAT - rowTileEnd * latEachTile;
        String[][] render_grid = new String[rowTileEnd - rowTileStart][colTileEnd - colTileStart];
        for (int col = 0; col < colTileEnd - colTileStart; col++) {
            for (int row = 0; row < rowTileEnd - rowTileStart; row++) {
                render_grid[row][col] = "d" + depth + "_x" + (col + colTileStart) + "_y" + (row + rowTileStart) + ".png";
            }
        }
        results.put("depth", depth);
        results.put("query_success", true);
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);
    }
    private void updateFailReuslt(Map<String, Object> results, int depth) {
        results.put("depth", depth);
        results.put("query_success", false);
        results.put("render_grid", null);
        results.put("raster_ul_lon", null);
        results.put("raster_lr_lon", null);
        results.put("raster_ul_lat", null);
        results.put("raster_lr_lat", null);
    }
}
