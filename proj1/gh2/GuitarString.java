package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    private Deque<Double> buffer;

    public GuitarString(double frequency) {
        buffer = new ArrayDeque<>();
        double temp = SR / frequency;
        temp = Math.round(temp);
        int capacity = (int) temp;
        for (int i = 0; i < capacity; i++) {
            buffer.addFirst(0.0);
        }
    }


    public void pluck() {
        int x = buffer.size();
        for (int i = 0; i < x; i++) {
            double r = Math.random() - 0.5;
            buffer.removeFirst();
            buffer.addLast(r);
        }
    }

    public void tic() {
        double first = buffer.removeFirst();
        double newFirst = buffer.get(0);
        double newDouble = DECAY * 0.5 * (first + newFirst);
        buffer.addLast(newDouble);
    }

    public double sample() {
        return buffer.get(0);
    }
}
