package solver;

/**
 * Object that defines an applied transformation.
 */
public class Tf {
    private boolean type; // W <- true, B <- False
    private int[] triplet;
    // List of all of the possible triplets where moves can occur on the board.
    public static final int[][] triplets = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 7, 8, 9 }, { 8, 9, 10 },
            { 9, 10, 11 }, { 10, 11, 12 }, { 13, 14, 15 }, { 14, 15, 16 }, { 15, 16, 17 }, { 16, 17, 18 },
            { 17, 18, 19 }, { 20, 21, 22 }, { 21, 22, 23 }, { 22, 23, 24 }, { 23, 24, 25 }, { 24, 25, 26 },
            { 27, 28, 29 }, { 30, 31, 32 }, { 12, 19, 26 }, { 11, 18, 25 }, { 2, 5, 10 }, { 5, 10, 17 }, { 10, 17, 24 },
            { 17, 24, 29 }, { 24, 29, 32 }, { 1, 4, 9 }, { 4, 9, 16 }, { 9, 16, 23 }, { 16, 23, 28 }, { 23, 28, 31 },
            { 0, 3, 8 }, { 3, 8, 15 }, { 8, 15, 22 }, { 15, 22, 27 }, { 22, 27, 30 }, { 7, 14, 21 }, { 6, 13, 20 } };

    /**
     * Constructor
     */
    Tf(boolean type, int[] triplet) {
        this.type = type;
        this.triplet = triplet;
    }

    /**
     * Allows us to determine the type (white or black)
     */
    public boolean getType() {
        return this.type;
    }

    /**
     * Allows us to determine the triplet of the TF
     */
    public int[] getTriplet() {
        return this.triplet;
    }

    /**
     * Returns a string representing the TF.
     */
    public String getStringRepresentation() {
        return this.triplet[0] + "@" + this.triplet[2];
    }

    /**
     * Check the input puzzle for the possibility of a transformation at
     * triplet.
     */
    public static String checkForTransform(boolean[] puzzle, int[] triplet) {
        boolean x0 = puzzle[triplet[0]]; // Gets the three values
        boolean x1 = puzzle[triplet[1]]; // of puzzle at the triplets
        boolean x2 = puzzle[triplet[2]]; // indices

        // If the two extremes are the same, there can be no sub
        if (x0 == x2) {
            return null;
        }

        if (x1) {
            return "W";
        } else {
            return "B";
        }
    }
}