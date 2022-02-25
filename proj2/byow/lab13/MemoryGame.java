package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(CHARACTERS[rand.nextInt(26)]);
        }
        return result.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 2, s);
        //TODO: If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {
            StdDraw.line(0, height * 9. / 10, width, height * 9. / 10);
            StdDraw.text(width * 4. / 5, height * 19. / 20, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.text(width * 1. / 10, height * 19. / 20, "Round " + String.valueOf(round));
            if (!playerTurn) {
                StdDraw.text(width / 2, height * 19. / 20, "Watch!");
            } else {
                StdDraw.text(width / 2, height * 19. / 20, "Write!");
            }
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            char t = letters.charAt(i);
            drawFrame(String.valueOf(t));
            pause(1000);
            drawFrame("");
            StdDraw.show();
            pause(500);
        }
    }

    private static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String result = "";
        for (int i = 0; i < n; i++) {
            do {
                pause(1);
            } while (!StdDraw.hasNextKeyTyped());
            result += StdDraw.nextKeyTyped();
            drawFrame(result);
        }

        return result;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 0;
        String result;
        String rs;
        playerTurn = false;
        do {
            round += 1;
            rs = generateRandomString(round);
            System.out.println(rs);
            drawFrame("ROUND " + round);
            pause(1500);
            flashSequence(rs);

            playerTurn = true;
            drawFrame("");
            result = solicitNCharsInput(round);
            pause(500);
            playerTurn = false;
            if (result.equals(rs)) {
                drawFrame("Good Job!");
                pause(1000);
            }
        } while (result.equals(rs));
        drawFrame("Game Over! You made it to round" + round);
        pause(1000);

        //TODO: Establish Engine loop
    }

}
