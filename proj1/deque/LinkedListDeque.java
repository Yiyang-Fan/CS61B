package deque;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    public class ListNode {
        public T item;
        public ListNode prev;
        public ListNode next;
        public ListNode(T i, ListNode p, ListNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private ListNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new ListNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        ListNode t = new ListNode(item, sentinel, sentinel.next);
        sentinel.next.prev = t;
        sentinel.next = t;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        ListNode t = new ListNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = t;
        sentinel.prev = t;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        String result = "";
        ListNode t = sentinel.next;
        while (t.item != null) {
            result += t.item.toString() + " ";
            t = t.next;
        }
        System.out.println(result.substring(0, Math.max(0, result.length()-1)));
    }

    @Override
    public T removeFirst() {
        ListNode result = sentinel.next;
        if (result.item != null) {
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
        }
        return result.item;
    }

    @Override
    public T removeLast() {
        ListNode result = sentinel.prev;
        if (result.item != null) {
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
        }
        return result.item;
    }

    @Override
    public T get(int index) {
        ListNode result = sentinel.next;
        for (int i = 0; i < index && result.item != null; i++) {
            result = result.next;
        }
        return result.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private ListNode ind;
        public LinkedListDequeIterator() {
            ind = sentinel;
        }
        @Override
        public boolean hasNext() {
            return ind.next != sentinel;
        }

        @Override
        public T next() {
            ind = ind.next;
            T returnItem = ind.item;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque)) {
            return false;
        } else if (size() != ((LinkedListDeque<T>) o).size()) {
            return false;
        } else {
            ListNode head1 = sentinel.next;
            ListNode head2 = ((LinkedListDeque<T>) o).sentinel.next;
            for (int i = 0; i < size(); i++) {
                if (!head1.item.equals(head2.item)) {
                    return false;
                }
            }
            return true;
        }
    }

}
