package solver;

import game.HiRiQ;
import java.util.ArrayList;

/**
 * Node object for our tree.
 */
public class Node implements Comparable<Node> {
    public HiRiQ configuration; // This node's puzzle config
    Edge parent; // One parent
    ArrayList<Edge> children; // Multiple children
    int layer;
    boolean[] config;

    /**
     * Constructor
     */
    Node(HiRiQ puzzle) {
        this.configuration = puzzle;
        this.parent = null;
    }

    /**
     * Constructor
     */
    Node(HiRiQ puzzle, int layer) {
        this.configuration = puzzle;
        this.parent = null;
        this.layer = layer;
    }

    /**
     * Sets the parent of the Node.
     */
    public void setParent(Edge parent) {
        this.parent = parent;
    }

    /**
     * Another work horse method, this one resolves all of the possible child
     * configurations of the current Node and adds the Tranformation (Edge) that
     * leads to them to the children ArrayList.
     */
    public void resolveChildren() {
        this.children = new ArrayList<Edge>();
        this.config = this.configuration.load(new boolean[33]);

        for (int i = 0; i < Tf.triplets.length; i++) {
            // Checks to see if the current triplet would be a valid
            // transformation
            String tf = Tf.checkForTransform(this.config, Tf.triplets[i]);

            // Technically this should never be null, but this is just a
            // sanity check
            if (tf != null) {

                // configure the next node and its edge
                boolean[] temp = this.config.clone();
                for (int j = 0; j < Tf.triplets[i].length; j++) {
                    int index = Tf.triplets[i][j]; // Performs the tf on
                                                   // the current config
                                                   // to get the
                                                   // resulting
                                                   // configuration
                    temp[index] = !temp[index];
                }

                HiRiQ childPuzzle = new HiRiQ((byte) 0);
                childPuzzle.store(temp);

                // Creates new child node
                Node child = new Node(childPuzzle, this.layer + 1);

                // Creates the transformations
                boolean tfType = tf == "W";
                Tf transformation = new Tf(tfType, Tf.triplets[i]);

                // Creates the edge, that is, the link between the parent
                // and child, with the associated transformation to get from
                // the parent to the child
                Edge edge = new Edge(this, child, transformation);
                child.parent = edge;

                this.children.add(edge);
            }
        }

        // Null out the actual configuration at the end (save space)
        this.config = null;
    }

    /**
     * Traverses the current configuration and determines the number of white
     * pegs.
     */
    public int numberOfWhitePegs() {
        config = this.configuration.load(new boolean[33]);
        int whitePegs = 0;
        for (int i = 0; i < 33; i++) {
            if (config[i]) {
                whitePegs++;
            }
        }
        config = null;
        return whitePegs;
    }

    /**
     * Allows built in sort method to be used on Node object ArrayLists.
     */
    @Override
    public int compareTo(Node compareAgainst) {
        return this.numberOfWhitePegs() - compareAgainst.numberOfWhitePegs();
    }
}