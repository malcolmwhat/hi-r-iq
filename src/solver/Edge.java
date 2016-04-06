package solver;

/**
 * Object that describes the link between a parent and a child, with the
 * transformation describing the transformation required to get from one to the
 * other
 */
class Edge {
    Node parent;
    Node child;
    Tf tf;

    /**
     * Constructor
     */
    public Edge(Node parent, Node child, Tf tf) {
        this.parent = parent;
        this.child = child;
        this.tf = tf;
    }

    /**
     * returns the string representation of the edges transform.
     */
    public String tfRep() {
        return tf.getStringRepresentation();
    }
}