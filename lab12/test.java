import edu.princeton.cs.algs4.Queue;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
    public static void main(String[] args) {

    }
    @Test
    public void testMergeStudents() {
        Queue<String> students = new Queue<>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        students.enqueue("Bitch");
        students.enqueue("MotherFucker");
        students.enqueue("Shame");
        Queue<String> mst = MergeSort.mergeSort(students);
        Queue<String> target = new Queue<>();
        target.enqueue("Alice");
        target.enqueue("Bitch");
        target.enqueue("Ethan");
        target.enqueue("MotherFucker");
        target.enqueue("Shame");
        target.enqueue("Vanessa");
        System.out.println(mst);
        while (!mst.isEmpty()) {
            assertEquals(mst.dequeue(), target.dequeue());
        }
        assertTrue(target.isEmpty());
    }
    @Test
    public void testQuickStudents() {
        Queue<String> students = new Queue<>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        students.enqueue("Bitch");
        students.enqueue("MotherFucker");
        students.enqueue("Shame");
        Queue<String> mst = QuickSort.quickSort(students);
        Queue<String> target = new Queue<>();
        target.enqueue("Alice");
        target.enqueue("Bitch");
        target.enqueue("Ethan");
        target.enqueue("MotherFucker");
        target.enqueue("Shame");
        target.enqueue("Vanessa");
        while (!mst.isEmpty()) {
            assertEquals(mst.dequeue(), target.dequeue());
        }
        assertTrue(target.isEmpty());
    }
}
