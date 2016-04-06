# hi-r-iq
Solution to peg solitaire game variant, assignment 4 of Comp 250

Here is a link to the [assignment itself](http://crypto.cs.mcgill.ca/~crepeau/COMP250/HW4.pdf). It is based on the game *Peg Solitaire*

![](http://mathworld.wolfram.com/images/eps-gif/PegSolitaire_1000.gif)

HiRiQ is the name of the assignment. I am not entirely clear as to why this was the name of the assignment.

Solution uses a tree creation / traversal method to find the final solution. 

The tree we build is contained in `GameTree.java`. We use two other elements to traverse the tree towards the goal: a sorted indexed list (`ArrayList`) of the external nodes of the tree, as well as a sorted indexed list of the board configurations that have been previously seen. 

For a given node we find all possible children of the node (a child can be reached from the parent by applying a single board substitution). Once we find the children, we discard all that have been seen before, or are currently external nodes. We then add the remaining children to the external nodes and sort the external nodes in ascending order based on the number of white pegs. Selection of the next element is done from the front of the external node list, so every step we attempt to minimize the number of white pegs. 

Based on the assignment specification, this only needs to work for solvable configurations, which it does in very good time. 

The pitfall of this design is the algorithm **does not find the shortest path to the solvable configuration** in the general case.
