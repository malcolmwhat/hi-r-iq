package solver;

import java.util.LinkedList;
import java.util.Stack;
import game.HiRiQ;

/*
 * This class handles the solving of Hi-R-IQ puzzles
 */
public class HiRiQSolver {
    private GameTree game;
    private LinkedList<String> solution;
    private boolean solved;
    private Stack<String> reversedSolution;
    public static final int[][] triplets = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 7, 8, 9 }, { 8, 9, 10 },
            { 9, 10, 11 }, { 10, 11, 12 }, { 13, 14, 15 }, { 14, 15, 16 }, { 15, 16, 17 }, { 16, 17, 18 },
            { 17, 18, 19 }, { 20, 21, 22 }, { 21, 22, 23 }, { 22, 23, 24 }, { 23, 24, 25 }, { 24, 25, 26 },
            { 27, 28, 29 }, { 30, 31, 32 }, { 12, 19, 26 }, { 11, 18, 25 }, { 2, 5, 10 }, { 5, 10, 17 }, { 10, 17, 24 },
            { 17, 24, 29 }, { 24, 29, 32 }, { 1, 4, 9 }, { 4, 9, 16 }, { 9, 16, 23 }, { 16, 23, 28 }, { 23, 28, 31 },
            { 0, 3, 8 }, { 3, 8, 15 }, { 8, 15, 22 }, { 15, 22, 27 }, { 22, 27, 30 }, { 7, 14, 21 }, { 6, 13, 20 } };

    /*
     * Constructs a solver object which decouples the HiRiQ configuration from
     * the actual solution of these configurations.
     */
    public HiRiQSolver(HiRiQ puzzle) {
        this.game = new GameTree(puzzle);
        this.solved = false;
    }

    /*
     * Allows the use of the same solver for different puzzles.
     */
    public void setPuzzle(HiRiQ puzzle) {
        this.game = new GameTree(puzzle);
        this.solved = false;
    }

    /*
     * Wrapper that checks if it is solved and returns the solution as a string.
     */
    public String getSolution() {
        if (!this.solved) {
            this.solvePuzzle();
            this.solved = true;
        }
        return this.solution.toString();
    }

    /*
     * Builds a tree while looking for the solution. The current puzzle's
     * configuration is the root of the tree. The path from the found solution
     * to the start is the reverse of what we want, so this method uses a stack
     * to reverse the path.
     */
    public void solvePuzzle() {
        Node solvedState = this.game.solvePuzzle();
        this.solution = new LinkedList<String>();
        this.reversedSolution = new Stack<String>();
        Node currentNode = solvedState;
        while (currentNode.parent != null) {
            // Traverse back up the tree
            reversedSolution.push(currentNode.parent.tfRep());
            
            // This was done to decouple the edges from the nodes.
            currentNode = currentNode.parent.parent;
        }
        while (!reversedSolution.empty()) {
            // Pop off the stack, and create the linked list
            this.solution.add(this.reversedSolution.pop());
        }
        solvedState.configuration.print();
    }
}
