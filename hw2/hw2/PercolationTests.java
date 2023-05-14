package hw2;

import org.junit.jupiter.api.Test;

public class PercolationTests {
    @Test
    public void test1() {
        Percolation us = new Percolation(9);
        for (int i = 0; i < 9; i++) {
            us.open(i, 5);
        }
    }

    @Test
    public void test2() {
    PercolationFactory you = new PercolationFactory();
    PercolationStats us = new PercolationStats(10, 10, you);
    System.out.println(us.mean());
    System.out.println(us.stddev());
    System.out.println(us.confidenceLow());
    System.out.println(us.confidenceHigh());

    }
}
