package solver;

import game.HiRiQ;
import java.util.Stack;
import java.util.LinkedList;

/**
 * This is the class that we interact with from the highest level. It sets up
 * the game tree and calls the appropriate solving methods. Its method
 * getSolution(boolean[]) is the method you will want to interact with.
 */
public class HiRiQSolver {
    private GameTree game;
    private LinkedList<String> solution;
    private boolean solved;
    private Stack<String> reversedSolution; // Used to reverse the solution
                                            // when we travel backwards up
                                            // the tree.

    /**
     * Constructs a solver object which decouples the HiRiQ configuration from
     * the actual solution of these configurations.
     */
    public HiRiQSolver(HiRiQ puzzle) {
        this.game = new GameTree(puzzle);
        this.solved = false;
    }

    /**
     * Sets up a dummy solver.
     */
    public HiRiQSolver() {
        HiRiQ temp = new HiRiQ((byte) 0);
        this.setPuzzle(temp);
    }

    /**
     * Allows the use of the same solver for different puzzles.
     */
    public void setPuzzle(HiRiQ puzzle) {
        this.game = new GameTree(puzzle);
        this.solved = false;
    }

    /**
     * Wrapper that checks if it is solved and returns the solution as a string.
     */
    public String getSolution() {
        if (!this.solved) {
            this.solvePuzzle();
            this.solved = true;
        }
        return this.solution.toString();
    }

    /**
     * Another wrapper, except this one allows you to pass in an array (assumed
     * to be an array of 33 booleans), and returns the solution to the
     * corresponding puzzle (assuming it is a solvable configuration).
     */
    public String getSolution(boolean[] puzzle) {
        HiRiQ temp = new HiRiQ((byte) 0); // To allow puzzle creation
        temp.store(puzzle);
        this.setPuzzle(temp);
        this.solvePuzzle();
        this.solved = true;
        return this.solution.toString();
    }

    /**
     * Builds a tree while looking for the solution. The path from the found
     * solution to the start is the reverse of what we want, so this method uses
     * a stack to reverse the path.
     */
    public void solvePuzzle() {
        Node solvedState = this.game.solvePuzzle(); // Returns the node w/
                                                    // solved configuration
        this.solution = new LinkedList<String>();
        this.reversedSolution = new Stack<String>();
        Node currentNode = solvedState;
        while (currentNode.parent != null) {
            // Traverse back up the tree until the root (which has a null
            // parent), pushing the transformations onto the stack
            reversedSolution.push(currentNode.parent.tfRep());

            // first parent points to the Edge, second to the node
            currentNode = currentNode.parent.parent;
        }

        // Reverse the solution to give the path from root -> solution
        while (!reversedSolution.empty()) {
            this.solution.add(this.reversedSolution.pop());
        }

        solvedState.configuration.print(); // PRINTS CONFIG
    }
}