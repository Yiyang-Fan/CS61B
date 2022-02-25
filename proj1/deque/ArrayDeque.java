package deque;

import org.checkerframework.checker.units.qual.A;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T>{

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

    @Override
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

    @Override
    public void addLast(T item) {
        if (size == capacity) {
            addResize();
        }
        items[endIndex] = item;
        size += 1;
        endIndex = (endIndex + 1) % capacity;
        // itemPrint();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size * 3 < capacity) {
            removeResize();
        }
        startIndex += 1;
        startIndex %= capacity;
        size -= 1;
        // itemPrint();
        return items[startIndex];
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
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

    @Override
    public T get(int ind) {
        return items[(startIndex + 1 + ind) % capacity];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator{
        int index;

        public ArrayDequeIterator() {
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T returnItem = items[(startIndex + index + 1) % capacity];
            index += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayDeque)) {
            return false;
        } else if (size != ((ArrayDeque<?>) o).size()) {
            return false;
        } else {
            for (int i = 0; i < size; i++) {
                if (!get(i).equals(((ArrayDeque<?>) o).get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    private void allPrint() {
        for (int i = 0; i < capacity; i++) {
            System.out.printf("item: %d, index: %d \n",
                    items[i], i);
        }
        System.out.println("SSSSSSSSS");
        System.out.printf("startIndex: %d,  endIndex: %d \n", startIndex, endIndex);
    }

    private void itemPrint() {
        for (int i = 0; i < size; i++) {
            System.out.printf("%d ", this.get(i));
        }
        // System.out.printf("Size: %d , Capacity: %d \n", size, capacity);
        System.out.println();
    }

    @Override
    public void printDeque() {
        itemPrint();
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

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

}
