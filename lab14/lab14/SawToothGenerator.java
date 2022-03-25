package lab14;

import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1);
        double temp = (state * 1.0 / period) % 1;
        return normalize(temp);
    }

    public double normalize(double temp) {
        return -1 + 2 * temp;
    }
}