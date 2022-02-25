package deque;

import net.sf.saxon.functions.Minimax;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator cmp;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        cmp = c;
    }

    public T max() {
        if (size() == 0) {
            return null;
        } else {
            T result = get(0);
            for (int i = 0; i < size(); i++) {
                if (cmp.compare(result, get(i)) < 0) {
                    result = get(i);
                }
            }
            return result;
        }
    }

    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        } else {
            T result = get(0);
            for (int i = 0; i < size(); i++) {
                if (c.compare(result, get(i)) < 0) {
                    result = get(i);
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        Comparator<Integer> d = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
        Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };
        MaxArrayDeque<Integer> s = new MaxArrayDeque<Integer>(d);
        s.removeFirst();
        System.out.println(s.size());
        s.addFirst(1);
        s.addFirst(3);
        s.addFirst(5);
        s.printDeque();
        System.out.println(s.max(c));
    }
}
