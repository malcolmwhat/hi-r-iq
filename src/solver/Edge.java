package solver;

class Edge {
    // Upper Node
    // Lower Node
    // TF (transformation)
    Node parent;
    Node child;
    Tf tf;
    
    public Edge (Node parent, Node child, Tf tf) {
        this.parent = parent;
        this.child = child;
        this.tf = tf;
    }
    
    public String tfRep() {
        return tf.getStringRepresentation();
    }
}
