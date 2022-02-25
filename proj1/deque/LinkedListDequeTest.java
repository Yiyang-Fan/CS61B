package deque;

import afu.org.checkerframework.checker.igj.qual.I;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
        lld1.addFirst(20);

        lld1.printDeque();
		// should not be empty
		// assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
        lld1.removeLast();
        lld1.printDeque();
		// should be empty
		assertTrue("lld1 should not be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld1.printDeque();
        lld2.addFirst(3.14159);
        lld2.printDeque();
        lld3.addFirst(true);
        lld3.printDeque();

        String s = lld1.removeFirst();
        System.out.println(s);
        lld1.printDeque();
        double d = lld2.removeFirst();
        System.out.println(d);
        lld2.printDeque();
        boolean b = lld3.removeFirst();
        System.out.println(b);
        lld3.printDeque();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    public void randomTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld11 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld13 = new LinkedListDeque<>();
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        ArrayDeque<Integer> lld22 = new ArrayDeque<>();
        ArrayDeque<Integer> lld23 = new ArrayDeque<>();
        int N = 10000;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 8);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(100);
                lld1.addFirst(randVal);
                lld2.addFirst(randVal);
                lld11.addFirst(randVal + 1);
                lld22.addFirst(randVal + 1);
                lld13.addFirst(randVal);
                lld23.addFirst(randVal);

            } else if (operationNumber == 1) {
                int randVal = StdRandom.uniform(100);
                lld1.addLast(randVal);
                lld2.addLast(randVal);
                lld11.addLast(randVal);
                lld22.addLast(randVal);
                lld13.addLast(randVal);
                lld23.addLast(randVal);

            } else if (operationNumber == 2) {
                assertEquals("Size Should Equal", lld1.size(), lld2.size());
            } else if (operationNumber == 3) {
                if (lld1.size() > 0) {
                    lld13.removeFirst();
                    lld23.removeFirst();
                    assertEquals("Remove First Should Equal: ", lld1.removeFirst(), lld2.removeFirst());
                }
            } else if (operationNumber == 4) {
                if (lld1.size() > 0) {
                    int t1 = lld1.removeLast();
                    int t2 = lld2.removeLast();
                    lld13.removeLast();
                    lld23.removeLast();
                    assertEquals("Remove Last Should Equal", t1, t2);
                }
            } else if (operationNumber == 5) {
                if (lld1.size() > 0) {
                    int ind = StdRandom.uniform(lld1.size());
                    assertEquals("Get Should Equal", lld1.get(ind), lld2.get(ind));
                }
            } else if (operationNumber == 6) {
                lld1.printDeque();
                Iterator<Integer> l1 = lld1.iterator();
                while (l1.hasNext()) {
                    System.out.printf("%d ", l1.next());
                }
                System.out.printf("\n");

                lld2.printDeque();
                Iterator<Integer> l2 = lld2.iterator();
                while (l2.hasNext()) {
                    System.out.printf("%d ", l2.next());
                }
                System.out.printf("\n");
            } else if (operationNumber == 7) {
                assertTrue("Should Equals", lld1.equals(lld13));
                assertTrue("Should Equals", lld2.equals(lld23));
                if (lld1.size() > 0) {
                    assertFalse("Should not Equals", lld1.equals(lld11));
                }
                if (lld2.size() > 0) {
                    assertFalse("Should not Equals", lld2.equals(lld22));
                }
            }
        }
    }
}
