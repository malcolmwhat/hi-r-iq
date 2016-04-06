package solver;

import game.HiRiQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

/**
 * Tree used to solve puzzles
 */
public class GameTree {
    private Node root; // The starting configuration

    // ArrayLists allow easy sorting, which is a big part of the solution.
    private ArrayList<Node> leaves; // Current leaves of the tree
    private ArrayList<HiRiQ> visited; // Puzzle configs that have been seen

    /**
     * Constructs the GameTree.
     */
    GameTree(HiRiQ puzzle) {
        root = new Node(puzzle);
        leaves = new ArrayList<Node>();
        leaves.add(root);
        visited = new ArrayList<HiRiQ>();
        visited.add(puzzle);
    }

    /**
     * Recursive binary search method that checks for the calling instance's
     * configuration in the visited ArrayList.
     */
    public boolean wasVisited(int config, byte weight, int low, int high) {
        if (low > high) {
            return false;
        }
        int middle = low + (high - low) / 2;
        HiRiQ puzzle = visited.get(middle);

        // I have chosen config as my dominant sorting mechanism, so I check
        // it first
        if (puzzle.config > config) {
            return wasVisited(config, weight, low, middle - 1);
        } else if (puzzle.config < config) {
            return wasVisited(config, weight, middle + 1, high);
        } else {
            // Configs match, weight is the secondary sorting mechanism.
            if (puzzle.weight > weight) {
                return wasVisited(config, weight, low, middle - 1);
            } else if (puzzle.weight < weight) {
                return wasVisited(config, weight, middle + 1, high);
            } else {
                return true;
            }
        }
    }

    /**
     * Since the leaves are sorted by the number of white pegs, we need to
     * traverse the ArrayList in a linear fashion to check for the current
     * configuration's existence (we can not use binary search).
     */
    public boolean isLeaf(int config, byte weight) {
        for (int i = 0; i < leaves.size(); i++) {
            HiRiQ puzzle = leaves.get(i).configuration;
            if (puzzle.config == config && puzzle.weight == weight) {
                return true;
            }
        }
        return false;
    }

    /**
     * I apologize for over-commenting this method, but I really wanted it to be
     * explained in plain English.
     * 
     * This is the major logic for the program. We start at the root, and build
     * the tree looking for the solved configuration. For a particular node, we
     * find all possible children. Then we ignore all children that have been
     * seen before, we also ignore children who's configurations are already
     * present in the tree.
     * 
     * The remaining children are added to the leaves of the tree. Then the
     * leaves are sorted in ascending order based on the number of white pegs in
     * their configurations. We take the first element of the ArrayList (i.e.
     * the fewest number of white pegs) as our next Node to analyze.
     */
    public Node solvePuzzle() {
        Node currentNode = leaves.get(0);
        leaves.remove(0);
        while (!currentNode.configuration.IsSolved()) {
            /*
             * Take a node: Get all of its children
             * 
             * Create all of the children nodes
             * 
             * Ignore all of the children who's configurations are already
             * visited
             * 
             * Also ignore all of the children who's configurations are in the
             * leaves
             * 
             * Add the remaining children to the leaves ArrayList
             * 
             * Sort the leaves ArrayList in ascending order by number of white
             * pegs
             * 
             * Take the first element of the leaves arraylist and process it
             * using the above process
             */

            currentNode.resolveChildren(); // Gets ALL of the possible
                                           // children of this configuration

            // Iterators allow us to traverse the list and remove elements
            // without indexing issues
            ListIterator<Edge> iter = currentNode.children.listIterator();
            Node child;
            while (iter.hasNext()) {
                // check if this child needs to be included in leaves
                child = iter.next().child;

                // Child's config and weight
                int configNumeric = child.configuration.config;
                byte weightNumeric = child.configuration.weight;

                if (wasVisited(configNumeric, weightNumeric, 0, visited.size() - 1)) {
                    iter.remove(); // Remove this child if it was already
                                   // visited
                } else if (isLeaf(configNumeric, weightNumeric)) {
                    iter.remove(); // Remove this child if it is already a
                                   // leaf
                } else {
                    leaves.add(child); // Otherwise we should process it, so
                                       // we add it to the leaves
                }
            }

            // Both node (leaves) and HiRiQ (visited) implement Comparable,
            // sort we can use the built in ArrayList sort method
            Collections.sort(leaves);

            visited.add(currentNode.configuration);
            Collections.sort(visited);

            currentNode = leaves.get(0); // Next node to process.
            leaves.remove(0);
        }
        return currentNode;
    }
}