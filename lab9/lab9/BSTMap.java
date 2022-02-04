package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {
    @Override
    public Iterator iterator() {
        return null;
    }

    private K key;
    private V value;
    private int size;
    private BSTMap left, right;

    public BSTMap() {
        size = 0;
        key = null;
        value = null;
        left = null;
        right = null;
    }

    public BSTMap(K k, V v, BSTMap l, BSTMap r) {
        size = 0;
        key = k;
        value = v;
        left = l;
        right = r;
    }
    @Override
    public void clear() {
        key = null;
        value = null;
        left = null;
        right = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (this.key == null) {
            return false;
        }
        if (this.key.compareTo(key) == 0) {
            return true;
        } else if (this.key.compareTo(key) > 0) {
            if (this.left != null) {
                return this.left.containsKey(key);
            }
        } else if (this.key.compareTo(key) < 0) {
            if (this.right != null) {
                return this.right.containsKey(key);
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (!this.containsKey(key)) {
            return null;
        }
        if (this.key.compareTo(key) == 0) {
            return this.value;
        } else if (this.key.compareTo(key) > 0) {
            return (V) this.left.get(key);
        } else {
            return (V) this.right.get(key);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        this.size += 1;
        if (this.key == null) {
            this.key = key;
            this.value = value;
        } else if (this.key.compareTo(key) < 0) {
            if (this.right == null) {
                this.right = new BSTMap(key, value, null, null);
            } else {
                this.right.put(key, value);
            }
        } else if (this.key.compareTo(key) > 0) {
            if (this.left == null) {
                this.left = new BSTMap(key, value, null, null);
            } else {
                this.left.put(key, value);
            }
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> s = new TreeSet<>();
        BFS(this, s);
        return s;
    }

    private void BFS(BSTMap<K, V> m, Set<K> s) {
        if (m != null && m.key != null) {
            s.add(m.key);
            BFS(m.left, s);
            BFS(m.right, s);
        }
    }

    @Override
    public V remove(K key) {
        if (this.containsKey(key)) {
            this.size -= 1;
            if (this.key == null) {
                return null;
            } else if (this.key.compareTo(key) < 0) {
                if (this.right != null) {
                    return (V) this.right.remove(key);
                }
            } else if (this.key.compareTo(key) > 0) {
                if (this.left != null) {
                    return (V) this.left.remove(key);
                }
            } else {
                V v = this.value;
                if (this.left == null) {
                    if (this.right == null) {
                        this.key = null;
                        this.value = null;
                        this.left = null;
                        this.right = null;
                    } else {
                        this.key = (K) this.right.key;
                        this.value = (V) this.right.value;
                        this.left = this.right.left;
                        this.right = this.right.right;
                    }

                } else if (this.right == null) {
                    if (this.left == null) {
                        this.key = null;
                        this.value = null;
                        this.left = null;
                        this.right = null;
                    } else {
                        this.key = (K) this.left.key;
                        this.value = (V) this.left.value;
                        this.right = this.left.right;
                        this.left = this.left.left;
                    }
                } else {
                    this.right = swapSmallest(this.right, this);
                }
                return v;
            }
        }
        return null;
    }

    private BSTMap swapSmallest(BSTMap t1, BSTMap t2) {
        if (t1.left == null) {
            t2.key = t1.key;
            t2.value = t1.value;
            return t1.right;
        } else {
            t1.left = swapSmallest(t1.left, t2);
            return t1;
        }
    }
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

}
