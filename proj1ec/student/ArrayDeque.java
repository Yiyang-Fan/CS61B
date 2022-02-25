package student;

import org.checkerframework.checker.units.qual.A;

public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int startIndex;
    private int endIndex;
    private int capacity;

    public ArrayDeque() {
        capacity = 8;
        items = (T[]) new Object[capacity];
        startIndex = 0;
        endIndex = 1;
        size = 0;
    }

    public void addFirst(T item) {
        if (size == capacity){
            addResize();
        }
        items[startIndex] = item;
        size += 1;
        startIndex -= 1;
        if (startIndex < 0) {
            startIndex = capacity + startIndex;
        }
        // itemPrint();
    }

    public void addLast(T item) {
        if (size == capacity) {
            addResize();
        }
        items[endIndex] = item;
        size += 1;
        endIndex = (endIndex + 1) % capacity;
        // itemPrint();
    }

    public T removeFirst() {
        if (size * 3 < capacity) {
            removeResize();
        }
        startIndex += 1;
        startIndex %= capacity;
        size -= 1;
        // itemPrint();
        return items[startIndex];
    }

    public T removeLast() {
        if (size * 3 < capacity) {
            removeResize();
        }
        endIndex -= 1;
        if (endIndex < 0) {
            endIndex = capacity - 1;
        }
        size -= 1;
        // itemPrint();
        return items[endIndex];
    }

    public T get(int ind) {
        return items[(startIndex + 1 + ind) % capacity];
    }

    public int size() {
        return size;
    }

    public void allPrint() {
        for (int i = 0; i < capacity; i++) {
            System.out.printf("item: %d, index: %d \n",
                    items[i], i);
        }
        System.out.println("SSSSSSSSS");
        System.out.printf("startIndex: %d,  endIndex: %d \n", startIndex, endIndex);
    }

    public void itemPrint() {
        for (int i = 0; i < size; i++) {
            System.out.printf("%d  ", this.get(i));
        }
        // System.out.printf("Size: %d , Capacity: %d \n", size, capacity);
        System.out.println();
    }

    private void addResize() {
        Object[] new_item = new Object[3 * capacity];
        for (int i = 0; i < size; i++) {
            new_item[1 + i] = items[(startIndex + 1 + i) % capacity];
        }
        startIndex = 0;
        endIndex = capacity + 1;
        capacity *= 3;
        items = (T[]) new_item;
    }

    private void removeResize() {
        if (capacity < 16) {
            return;
        }
        Object[] new_item = new Object[(int) (capacity / 2)];
        for(int i = 0; i < size; i++) {
            new_item[i + 1] = items[(startIndex + 1 + i) % capacity];
        }
        items = (T[]) new_item;
        capacity = (int) (capacity / 2);
        startIndex = 0;
        endIndex = size + 1;
    }

}
