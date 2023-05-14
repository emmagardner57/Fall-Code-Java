package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int top;
    private int bottom;
    private int[] status;
    private int open = 0;
    private int length;
    private WeightedQuickUnionUF board;
    private WeightedQuickUnionUF bottomlessBoard;
    public Percolation(int N) {
        length = N;
        top = (length * length) + 1;
        bottom = top + 1;
        board = new WeightedQuickUnionUF(bottom + 1);
        bottomlessBoard = new WeightedQuickUnionUF(bottom);
        status = new int[length * length];
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            int curr = xyTo1D(row, col);
            status[curr] = 1;
            if (row == 0) {
                board.union(top, curr);
                bottomlessBoard.union(top, curr);
            }
            if (row == (length - 1)) {
                board.union(bottom, curr);
            }
            checkConnect(row, col);
            open++;
        }
    }
    
    private void checkConnect(int row, int col) {
        int curr = xyTo1D(row, col);
        int area = length * length;
        if ((curr + 1) < (length * (row + 1)) && isOpen(row, col + 1)) {
            board.union(curr, curr + 1);
            bottomlessBoard.union(curr, curr + 1);
        }
        if ((curr - 1) >= (length * row) && isOpen(row, col - 1)) {
            board.union(curr, curr - 1);
            bottomlessBoard.union(curr, curr - 1);
        }
        if ((row + 1) < length && isOpen(row + 1, col)) {
            board.union(curr, curr + length);
            bottomlessBoard.union(curr, curr + length);
        }
        if (row >= 1 && isOpen(row - 1, col)) {
            board.union(curr, curr - length);
            bottomlessBoard.union(curr, curr - length);
        }
    }

    public boolean isOpen(int row, int col) {
        int curr = xyTo1D(row, col);
        if (status[curr] == 1) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        int curr = xyTo1D(row, col);
        if (isOpen(row, col) && bottomlessBoard.connected(top, curr)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return open;
    }

    public boolean percolates() {
        if (board.connected(top, bottom)) {
            return true;
        }
        return false;
    }

    private int xyTo1D(int row, int col) {
        int val = row * length;
        val += col;
        return val;
    }

}
