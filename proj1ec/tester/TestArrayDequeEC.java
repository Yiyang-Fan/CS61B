package tester;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.ArrayDeque;
import student.LinkedListDeque;

public class TestArrayDequeEC {
    @Test
    public void randomTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        ArrayDequeSolution<Integer> lld2 = new ArrayDequeSolution<>();
        int N = 10000;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(100);
                lld1.addFirst(randVal);
                lld2.addFirst(randVal);
            } else if (operationNumber == 1) {
                int randVal = StdRandom.uniform(100);
                lld1.addLast(randVal);
                lld2.addLast(randVal);
            } else if (operationNumber == 2) {
                assertEquals("Size Should Equal", lld1.size(), lld2.size());
            } else if (operationNumber == 3) {
                if (lld1.size() > 0) {
                    assertEquals("Remove First Should Equal: ", lld1.removeFirst(), lld2.removeFirst());
                }
            } else if (operationNumber == 4) {
                if (lld1.size() > 0) {
                    int t1 = lld1.removeLast();
                    int t2 = lld2.removeLast();
                    assertEquals("Remove Last Should Equal", t1, t2);
                }
            } else if (operationNumber == 5) {
                if (lld1.size() > 0) {
                    int ind = StdRandom.uniform(lld1.size());
                    assertEquals("Get Should Equal", lld1.get(ind), lld2.get(ind));
                }
            }
        }
    }

}
