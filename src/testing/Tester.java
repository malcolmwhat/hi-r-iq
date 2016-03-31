package testing;

import game.HiRiQ;
import solver.HiRiQSolver;

public class Tester {
    public static void main(String[] args) {
        HiRiQ W1 = new HiRiQ((byte) 0);
        HiRiQSolver S1 = new HiRiQSolver(W1);
        W1.print();
        S1.setPuzzle(W1);
        System.out.println(S1.getSolution());

        HiRiQ W2 = new HiRiQ((byte) 1);
        HiRiQSolver S2 = new HiRiQSolver(W2);
        W2.print();
        S2.setPuzzle(W2);
        System.out.println(S2.getSolution());

        HiRiQ W3 = new HiRiQ((byte) 2);
        HiRiQSolver S3 = new HiRiQSolver(W3);
        W3.print();
        S3.setPuzzle(W3);
        System.out.println(S3.getSolution());
        
        HiRiQ W4 = new HiRiQ((byte) 3);
        HiRiQSolver S4 = new HiRiQSolver(W4);
        W4.print();
        S4.setPuzzle(W4);
        System.out.println(S4.getSolution());

        HiRiQ W5 = new HiRiQ((byte) 4);
        HiRiQSolver S5 = new HiRiQSolver(W5);
        W5.print();
        S5.setPuzzle(W5);
        System.out.println(S5.getSolution());
    }
}