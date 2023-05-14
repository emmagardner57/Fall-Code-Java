package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> nr = new AListNoResizing<Integer>();
        BuggyAList<Integer> bug = new BuggyAList<Integer>();
        int m = 3;
        for (int i = 0; i<3; i++){
            nr.addLast(m);
            bug.addLast(m);
            m += 4;
        }
        for (int x = 0; x<3; x++){
            int nrLast = nr.removeLast();
            int bugLast = bug.removeLast();
            assertEquals(nrLast, bugLast);
        }
    }
@Test
        public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int bSize = B.size();
                assertEquals(size, bSize);
            } else if (operationNumber == 2 && L.size() > 0 && B.size() > 0) {
                // getLast
                int randVal = StdRandom.uniform(0, 100);
                int l = L.getLast();
                int b = B.getLast();
                assertEquals(l, b);
            } else if (operationNumber == 3 && L.size() > 0 && B.size()>0) {
                // removeLast
                int randVal = StdRandom.uniform(0, 100);
                int lr = L.removeLast();
                int br = B.removeLast();
                assertEquals(lr, br);
            }
        }
    }



}
