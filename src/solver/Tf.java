package solver;

public class Tf {
    private boolean type; // W <- true, B <- False
    private int[] triplet;

    Tf(boolean type, int[] triplet) {
        this.type = type;
        this.triplet = triplet;
    }

    public boolean getType() {
        return this.type;
    }

    public int[] getTriplet() {
        return this.triplet;
    }

    public String getStringRepresentation() {
        return this.triplet[0] + "@" + this.triplet[2];
    }

    public static String checkForTransform(boolean[] puzzle, int[] triplet) {
        boolean x0 = puzzle[triplet[0]];
        boolean x1 = puzzle[triplet[1]];
        boolean x2 = puzzle[triplet[2]];

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
