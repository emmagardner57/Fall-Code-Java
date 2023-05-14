package deque;
import org.junit.Test;
import static org.junit.Assert.*;
public class ArrayDequeTest {
    @Test
    public void tester(){
        ArrayDeque<Integer> deck = new ArrayDeque<>();
        int m = 3;
        for (int i = 0; i < 3; i ++) {
            deck.addFirst(m);
            m += 4;
        }
        m = 3;
        for(int x = 0; x<3; x++){
            int deckLast = deck.removeLast();
            assertEquals(deckLast, m);
            m += 4;

        }

    }
@Test
    public void random() {
    ArrayDeque<Integer> deck = new ArrayDeque<>();
    deck.addLast(4);
    deck.addFirst(5);
    for (int i = 0; i < 20; i++) {
        deck.addLast(i);
        deck.addFirst(i);
    }
    deck.printDeque();
    for (int i = 19; i >= 0; i--) {
        int back = deck.removeLast();
        int front = deck.removeFirst();
        assertEquals(back, i);
        assertEquals(back, front);
    }
}
    }
