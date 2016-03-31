package solver;

import game.HiRiQ;
import java.util.ArrayList;

public class Node implements Comparable<Node> {
    public HiRiQ configuration;
    Edge parent;
    ArrayList<Edge> children;
    int layer;
    boolean[] config;

    Node(HiRiQ puzzle) {
        this.configuration = puzzle;
        this.parent = null;
        this.layer = 1;
    }

    Node(HiRiQ puzzle, int layer) {
        this.configuration = puzzle;
        this.parent = null;
        this.layer = layer;
    }

    public void setParent(Edge parent) {
        this.parent = parent;
    }

    public void resolveChildren() {
        this.children = new ArrayList<Edge>();
        this.config = this.configuration.load(new boolean[33]);

        for (int i = 0; i < HiRiQSolver.triplets.length; i++) {
            String tf = Tf.checkForTransform(this.config, HiRiQSolver.triplets[i]);
            if (tf != null) {
                // Then we configure the next node and its edge
                boolean[] temp = this.config.clone();
                for (int j = 0; j < HiRiQSolver.triplets[i].length; j++) {
                    int index = HiRiQSolver.triplets[i][j];
                    temp[index] = !temp[index];
                }

                HiRiQ childPuzzle = new HiRiQ((byte) 0);
                childPuzzle.store(temp);

                Node child = new Node(childPuzzle, this.layer + 1);
                boolean tfType = tf == "W";

                Tf transformation = new Tf(tfType, HiRiQSolver.triplets[i]);

                Edge edge = new Edge(this, child, transformation);
                child.parent = edge;

                this.children.add(edge);
            }
        }

        // Null out the actual configuration at the end
        this.config = null;
    }

    public int numberOfWhitePegs() {
        config = configuration.load(new boolean[33]);
        int whitePegs = 0;
        for (int i = 0; i < 33; i++) {
            if (config[i]) {
                whitePegs++;
            }
        }
        config = null;
        return whitePegs;
    }

    @Override
    public int compareTo(Node compareAgainst) {
        return this.numberOfWhitePegs() - compareAgainst.numberOfWhitePegs();
    }
}
