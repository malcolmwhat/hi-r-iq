package solver;

import java.util.ArrayList;
import java.util.Collections;
import game.HiRiQ;
import java.util.ListIterator;

class GameTree {
    private Node root;
    private ArrayList<Node> leaves; // Current leaves of the tree
    private ArrayList<HiRiQ> visited; // Puzzle configs that have been seen

    /*
     * Constructs the GameTree.
     */
    GameTree(HiRiQ puzzle) {
        root = new Node(puzzle);
        leaves = new ArrayList<Node>();
        leaves.add(root);
        visited = new ArrayList<HiRiQ>();
        visited.add(puzzle);
    }

    /*
     * Recursive binary search method that checks for the calling instance's
     * configuration in the visited ArrayList.
     */
    public boolean wasVisited(int config, byte weight, int low, int high) {
        if (low > high) {
            return false;
        }
        int middle = low + (high - low) / 2;
        HiRiQ puzzle = visited.get(middle);

        // I have chosen config as my dominant sorting mechanism
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

    /*
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

    /*
     * This is the major logic for the program. We start at the root, and build
     * the tree looking for the solved configuration. For a particular node, we
     * find all possible children. Then we ignore all children that have been
     * seen before, we also ignore children who's configurations are already
     * present in the tree.
     */
    public Node solvePuzzle() {
        Node currentNode = leaves.get(0);
        leaves.remove(0);
        while (!currentNode.configuration.IsSolved()) {
            // Main solution goes here

            /*
             * Take a node: Get all of its children - Done
             * 
             * Create all of the children nodes - Done
             * 
             * Ignore all of the children who's configurations are already
             * visited - Done
             * 
             * Also ignore all of the children who's configurations are in the
             * leaves - Done
             * 
             * Add the remaining children to the leaves of the ArrayList - Done
             * 
             * Sort the ArrayList in ascending order by number of white pegs -
             * Done
             * 
             * Take the first element and then process it.
             */

            currentNode.resolveChildren();
            ListIterator<Edge> iter = currentNode.children.listIterator();
            Node child;
            while (iter.hasNext()) {
                // We are going to check if this
                // child needs to be included in
                // leaves
                child = iter.next().child;
                int configNumeric = child.configuration.config;
                byte weightNumeric = child.configuration.weight;
                int visUpBound = visited.size() - 1;
                if (wasVisited(configNumeric, weightNumeric, 0, visUpBound)) {
                    iter.remove();
                } else if (isLeaf(configNumeric, weightNumeric)) {
                    iter.remove();
                } else {
                    leaves.add(child); // Otherwise we should process it, so we
                                       // add it to the leaves
                }
            }
            Collections.sort(leaves);
            Collections.sort(visited);
            visited.add(currentNode.configuration);
            currentNode = leaves.get(0);
            leaves.remove(0);
        }
        return currentNode;
    }
}
