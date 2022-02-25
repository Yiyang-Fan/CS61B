package gh2;

import java.util.ArrayList;
import java.util.List;

public class GuitarHero {
    public static final String KEYBOARD =
            "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
    private static List<GuitarString> guitar_strings;
    private int ticTimes;

    /*
     *  Constructor
     *  Post: Initialize the 37 guitar strings. Initialize the tic time.
     *  Modify the nums to track how many guitar strings are initialized.
     */
    public GuitarHero() {
        guitar_strings = new ArrayList<>();
        for (int i = 0; i < KEYBOARD.length(); i++) {
            GuitarString t = new GuitarString(440.0 * Math.pow(2.0, (i - 24.0) / 12.0));
            guitar_strings.add(t);
        }
        ticTimes = 0;
    }

    /*
     * Post: Pluck the guitar string for the given pitch. If string not found, pass.
     */
    public void playNote(int pitch) {
        if (pitch >= -24 && pitch <= 12) {
            guitar_strings.get(pitch + 24).pluck();
        }
    }

    /*
     * Post: Return if the guitar has the string represented by the key.
     */
    public boolean hasString(char key) {
        return (KEYBOARD.indexOf(key) >= 0);
    }

    /*
     * Pre: The string represented by the key should be in the guitar.
     * Throws IllegalArgumentException if preconditions not met.
     * Post: Pluck the guitar string for the given key.
     */
    public void pluck(char key) {
        if (!hasString(key)) {
            throw new IllegalArgumentException("key not in KEYBOARD");
        }
        playNote(KEYBOARD.indexOf(key) - 24);
    }

    /*
     * Post: Return the current sound sample of this guitar.
     */
    public double sample() {
        double sampleSum = 0;
        for (GuitarString guitar_string : guitar_strings) {
            sampleSum += guitar_string.sample();
        }
        return sampleSum;
    }

    /*
     * Post: Perform one step Karplus-Strong update for all strings in the guitar.
     */
    public void tic() {
        for (GuitarString guitar_string : guitar_strings) {
            guitar_string.tic();
        }
        ticTimes += 1;
    }

    /*
     * Post: Return the current time.
     */
    public int time() {
        return ticTimes;
    }
}
