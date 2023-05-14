package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comp;
    public MaxArrayDeque(Comparator<T> c) {
        comp = c;
    }
    public T max() {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (int counter = 1; counter < size(); counter++) {
            if (comp.compare(max, get(counter)) < 0) {
                max = get(counter);
            }
        }
        return max;
    }
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (int counter = 1; counter < size(); counter++) {
            if (c.compare(max, get(counter)) < 0) {
                max = get(counter);
            }
        }
        return max;
    }
}
