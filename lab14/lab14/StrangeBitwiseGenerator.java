package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1);
        int weirdState = state & (state >> 3) & (state >> 10) % period;
        double temp = (weirdState * 1.0 / period) % 1;
        return normalize(temp);
    }

    public double normalize(double temp) {
        return -1 + 2 * temp;
    }
}
