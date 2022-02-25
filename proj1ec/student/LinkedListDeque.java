package student;

public class LinkedListDeque<T> {
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

    public void addFirst(T item) {
        ListNode t = new ListNode(item, sentinel, sentinel.next);
        sentinel.next.prev = t;
        sentinel.next = t;
        size += 1;
    }

    public void addLast(T item) {
        ListNode t = new ListNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = t;
        sentinel.prev = t;
        size += 1;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        String result = "";
        ListNode t = sentinel.next;
        while (t.item != null) {
            result += t.item.toString() + " ";
            t = t.next;
        }
        System.out.println(result.substring(0, Math.max(0, result.length()-1)));
    }

    public T removeFirst() {
        ListNode result = sentinel.next;
        if (result.item != null) {
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
        }
        return result.item;
    }

    public T removeLast() {
        ListNode result = sentinel.prev;
        if (result.item != null) {
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
        }
        return result.item;
    }

    public T get(int index) {
        ListNode result = sentinel.next;
        for (int i = 0; i < index && result.item != null; i++) {
            result = result.next;
        }
        return result.item;
    }
}
