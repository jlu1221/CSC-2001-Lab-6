import org.junit.jupiter.api.Test;

import java.time.chrono.MinguoEra;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MinHeap.
 *
 * These starter tests (with these exact names) should all pass by the
 * time you're done. They are NOT enough on their own - add your own
 * tests (see the TODO list at the bottom and the lab handout).
 */
public class MinHeapTest {

    // ---- starter tests ----

    @Test
    void newHeapIsEmpty() {
        MinHeap h = new MinHeap();
        assertTrue(h.isEmpty());
        assertEquals(0, h.size());
        assertArrayEquals(new int[0], h.toArray());
    }

    @Test
    void peekOnEmptyHeapThrows() {
        assertThrows(NoSuchElementException.class, () -> new MinHeap().peek());
    }

    @Test
    void insertOneValue() {
        MinHeap h = new MinHeap(1);
        h.insert(42);
        assertFalse(h.isEmpty());
        assertEquals(1, h.size());
        assertEquals(42, h.peek());
        assertArrayEquals(new int[]{42}, h.toArray());
    }

    @Test
    void removeMin() {
        MinHeap h = new MinHeap();

        assertThrows(NoSuchElementException.class, () -> h.removeMin());
    }

    @Test
    void minHeapIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new MinHeap(0));
        assertThrows(IllegalArgumentException.class, () -> new MinHeap(-1));
    }

    @Test
    void peek() {
        MinHeap h = new MinHeap();
        h.insert(8);
        h.insert(3);
        h.insert(5);

        assertEquals(3, h.peek());
        assertEquals(3, h.size());
        assertEquals(3, h.removeMin());
    }

    @Test
    void minimumPercolates() {
        MinHeap h = new MinHeap();
        h.insert(8);
        h.insert(6);
        h.insert(5);
        h.insert(1);

        assertEquals(1, h.peek());
        assertArrayEquals(new int[]{1, 5, 6, 8}, h.toArray());
    }

    @Test
    void removeMintest() {
        MinHeap h = new MinHeap(new int[]{3, 5, 8, 10, 7, 12, 9});

        assertEquals(3, h.removeMin());
        assertArrayEquals(new int[]{5, 7, 8, 10, 9, 12}, h.toArray());
    }

    @Test
    void copyAndNegative() {
        MinHeap h = new MinHeap();
        h.insert(4);
        h.insert(-2);
        h.insert(4);
        h.insert(-8);

        assertEquals(-8, h.removeMin());
        assertEquals(-2, h.removeMin());
        assertEquals(4, h.removeMin());
        assertEquals(4, h.removeMin());
        assertTrue(h.isEmpty());
    }

    @Test
    void heapGrow() {
        MinHeap h = new MinHeap(1);
        h.insert(3);
        h.insert(5);
        h.insert(8);

        assertEquals(3, h.size());
        assertArrayEquals(new int[]{3, 5, 8}, h.toArray());
    }

    @Test
    void toArrayTest() {
        MinHeap h = new MinHeap();
        h.insert(2);
        h.insert(8);

        int[] copy = h.toArray();
        copy[0] = 10;

        assertEquals(2, h.peek());
        assertArrayEquals(new int[]{2, 8}, h.toArray());
    }

    @Test
    void sortedOrderTest() {
        MinHeap h = new MinHeap();
        int[] values = {8, 4, 9, 7, 0};

        for (int value : values) {
            h.insert(value);
        }

        int[] expected = {0, 4, 7, 8, 9};

        for (int value : expected) {
            assertEquals(value, h.removeMin());
        }

        assertTrue(h.isEmpty());
        assertThrows(NoSuchElementException.class, () -> h.peek());
    }

    @Test
    void minHeapOneValue() {
        int[] values = {1, 4, 9, 0, 3, 6, 1};

        MinHeap array = new MinHeap(values);

        MinHeap insertOne = new MinHeap();
        for (int value : values) {
            insertOne.insert(value);
        }

        assertFalse(Arrays.equals(
                array.toArray(),
                insertOne.toArray()
        ));
    }

    @Test
    void emptyAndSingleValue() {
        MinHeap empty = new MinHeap(new int[]{});
        assertTrue(empty.isEmpty());

        MinHeap one_value = new MinHeap(new int[]{12});
        assertEquals(12, one_value.peek());
    }

    @Test
    void nullArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new MinHeap((int[]) null)
        );
    }

    @Test
    void arrayHeapGrow() {
        MinHeap h = new MinHeap(new int[]{5});

        h.insert(2);
        h.insert(7);

        assertEquals(2, h.removeMin());
        assertEquals(5, h.removeMin());
        assertEquals(7, h.removeMin());
    }

    @Test
    void heapCopyNegative() {
        int[] values = {4, -1, 0, 2, -5};
        int[] array = {4, -1, 0, 2, -5};

        assertArrayEquals(new int[]{-5, -1, 0, 2, 4}, MinHeap.heapSort(values));

        assertArrayEquals(array, values);
        assertArrayEquals(new int[]{}, MinHeap.heapSort(new int[]{}));
    }
}