package bstmap;

import org.junit.jupiter.api.Test;

public class BSTTest {
    @Test
    public void test1(){
        BSTMap x = new BSTMap();
        x.put(2, 5);
        x.put(4, 6);
        x.put(1, 3);
        System.out.println("containsKey:  " + x.containsKey(2));
        System.out.println("size:  " + x.size());
        x.printInOrder();
        x.clear();
        System.out.println("containsKey:  " + x.containsKey(2));
        System.out.println("size:  " + x.size());


    }

}
