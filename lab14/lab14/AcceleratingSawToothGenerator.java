package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double acc;

    public AcceleratingSawToothGenerator(int period, double acc) {
        state = 0;
        this.period = period;
        this.acc = acc;
    }

    public double next() {
        state = (state + 1);
        if (state > period) {
            state = 0;
        }
        double temp = state * 1.0 / period;
        if (state == 0) {
            period *= acc;
        }
        return normalize(temp);
    }

    public double normalize(double temp) {
        return -1 + 2 * temp;
    }
}
