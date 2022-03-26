import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    public static void main(String[] args) {
        System.out.println(-1 % 7);
    }
    public SeamCarver(Picture picture) {
        this.picture = picture;
        width = picture.width();
        height = picture.height();
    }

    public Picture picture() {
        return picture;
    }
    public int width() {
        return width;
    }
    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        Color xright = picture.get((x + 1) % width, y);
        Color xleft = picture.get((x - 1 + width) % width, y);
        Color yup = picture.get(x, (y - 1 + height) % height);
        Color ydown = picture.get(x, (y + 1) % height);

        int redDiffx = xright.getRed() - xleft.getRed();
        int redDiffy = yup.getRed() - ydown.getRed();
        int greenDiffx = xright.getGreen() - xleft.getGreen();
        int greenDiffy = yup.getGreen() - ydown.getGreen();
        int blueDiffx = xright.getBlue() - xleft.getBlue();
        int blueDiffy = yup.getBlue() - ydown.getBlue();

        int sqDiffx = redDiffx * redDiffx + greenDiffx * greenDiffx + blueDiffx * blueDiffx;
        int sqDiffy = redDiffy * redDiffy + greenDiffy * greenDiffy + blueDiffy * blueDiffy;

        return sqDiffx + sqDiffy;
    }

    public int[] findVerticalSeam() {
        double[][] M = new double[width][height];
        int[] result = new int[height];
        for (int col = 0; col < width; col++) {
            M[col][0] = energy(col, 0);
        }
        for (int row = 1; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (col == 0) {
                    M[col][row] = Math.min(M[col][row - 1], M[col + 1][row - 1]);
                } else if (col == width - 1) {
                    M[col][row] = Math.min(M[col - 1][row - 1], M[col][row - 1]);
                } else {
                    M[col][row] = Math.min(
                            Math.min(M[col - 1][row - 1], M[col][row - 1]),
                            M[col + 1][row - 1]
                    );
                }
                M[col][row] += energy(col, row);
            }
        }
        int ind = 0;
        double minEnergy = Double.MAX_VALUE;
        for (int col = 0; col < width; col++) {
            if (M[col][height - 1] < minEnergy) {
                minEnergy = M[col][height - 1];
                ind = col;
            }
        }
        double target = minEnergy - energy(ind, height - 1);
        result[height - 1] = ind;
        for (int row = height - 2; row >= 0; row--) {
            for (int col = ind - 1; col <= ind + 1; col++) {
                if (col >= 0 && col < width && M[col][row] == target) {
                    target = M[col][row] - energy(col, row);
                    ind = col;
                    break;
                }
            }
            result[row] = ind;
        }
        return result;
    }
    public int[] findHorizontalSeam() {
        Picture temp = new Picture(height, width);
        for (int col = 0; col < temp.width(); col++) {
            for (int row = 0; row < temp.height(); row++) {
                temp.set(col, row, picture.get(row, col));
            }
        }
        SeamCarver trans = new SeamCarver(temp);
        return trans.findVerticalSeam();
    }
    public void removeHorizontalSeam(int[] seam) {

    }
    public void removeVerticalSeam(int[] seam) {

    }
}
