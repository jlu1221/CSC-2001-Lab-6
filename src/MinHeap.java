import java.util.NoSuchElementException;

/**
 * A min-heap of ints stored in an array, as presented in lecture.
 *
 * The heap is a complete binary tree kept in heap[1] .. heap[size];
 * heap[0] is never used. For the node at index i:
 *     left child  = 2 * i
 *     right child = 2 * i + 1
 *     parent      = i / 2 
 *
 * Heap-order property: every parent is <= its children, so the
 * minimum is always at heap[1]. Duplicate values are allowed.
 *
 * Do not rename the class, the public methods, or change their
 * parameters/return types. You may add private helper methods.
 */
public class MinHeap {

    /** Number of values a new heap can hold before it has to grow. */
    public static final int DEFAULT_CAPACITY = 10;

    private int[] heap;   // heap[0] is unused; the root is heap[1]
    private int size;     // values live in heap[1] ... heap[size]

    /** Creates an empty heap with room for DEFAULT_CAPACITY values. */
    // Purpose: Creates an empty min heap
    // input: none
    // output: new empty minheap
    // example: new MinHeap().isEmpty() return true
    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty heap with room for capacity values before it grows.
     * (The array needs capacity + 1 slots, since index 0 is unused.)
     * throws IllegalArgumentException if capacity is less than 1
     */
    // purpose: creates an empty min heap with a specified initial capacity
    // input: capacity, the number of values the heap can initially hold
    // output: a new empty minheap
    // example: new minheap(3) creates room for 3 values

    public MinHeap(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }

        heap = new int[capacity + 1];
        size = 0;
    }

    /**
     * Builds a heap from values bottom-up in O(n): copies them into
     * heap[1] .. heap[n] in the given order, then calls buildHeap().
     * values itself must not be changed. (Make the array exactly big
     * enough: n + 1 slots, but at least 2.)
     * throws IllegalArgumentException if values is null
     */
    // purpose: builds a min heap from an array using bottom-up
    // input: values, an array of integers
    // output: a minheap containing the values
    // example: new minheap(new int[] {1, 2, 3})

    public MinHeap(int[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Values cannot be null value");
        }

        heap = new int[Math.max(values.length + 1, 2)];
        size = values.length;

        for (int i = 0; i < values.length; i++) {
            heap[i + 1] = values[i];
        }

        buildHeap();
    }

    /**
     * Adds value to the heap: puts it in the next open spot (index size + 1),
     * then percolates it up until its parent is no larger.
     * Grows the array first if it is full.
     */
    // purpose: adds a value to this min heap while maintaining heap order
    // input: value, the integer to insert
    // output: None
    // example: new minheap(new int[] {2, 3, 4}) insert 1 = {1, 2, 3, 4}

    public void insert(int value) {
        if (size == heap.length - 1) {
            grow();
        }

        size++;
        heap[size] = value;
        percolateUp(size);
    }

    /**
     * Removes and returns the smallest value: the last leaf moves to the
     * root, then percolates down until it is no larger than its children.
     * throws NoSuchElementException if the heap is empty
     */
    // purpose: Removes and returns the smallest value in this heap.
    // input: None
    // output: The minimum integer in the heap.
    // example: removeMin() on {3, 5, 8} returns 3.

    public int removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        int minimum = heap[1];

        heap[1] = heap[size];
        size--;

        if (!isEmpty()) {
            percolateDown(1);
        }

        return minimum;
    }

    /**
     * Returns the smallest value without removing it.
     * throws NoSuchElementException if the heap is empty
     */
    // purpose: return the smallest value without removing it
    // input: none
    // output: the minimum integer in the heap
    // example: peek() on {3, 5, 6} return 3
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        return heap[1];
    }

    /**
     * Returns the number of values in the heap.
     */
    // purpose: returns how many values are in this heap
    // input: none
    // output: the number of stored values
    // example: a heap containing {3, 5} has size 2

    public int size() {
        return size;
    }

    /**
     * Reports whether the heap has no values.
     * return true if the heap is empty
     */
    // purpose: determines whether this heap contains no values
    // input: none
    // output: true if empty otherwise false
    // example: new MinHeap().isEmpty() returns true
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the heap's values in array order (heap[1] ... heap[size]) as a
     * new array of length size(). Changing the returned array must not
     * change the heap.
     * Example: after inserting 3, 5, 8, 10, 7, 12, 9 this returns
     * {3, 5, 8, 10, 7, 12, 9}.
     * (The insert order matches the heap array order in this example)
     */

    // purpose: return the heap values in array order as a new array
    // input: none
    // output: a copy of heap[1] through heap[size]
    // example: a heap {3, 5, 8} return {3, 5, 8}
    public int[] toArray() {
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = heap[i + 1];
        }

        return result;
    }

    /**
     * Moves the value at index i up while it is smaller than its parent.
     * (Stop when it reaches the root or its parent is <= it.)
     */

    // purpose: moves a value upward until the heap order is restored
    // input: the index that is move upward
    // output: none
    // example: a value smaller than its parent swaps upward

    private void percolateUp(int i) {
        while (i > 1 && heap[i] < heap[i/2]) {
            swap(i, i/2);
            i = i /2;
        }
    }

    /**
     * Moves the value at index i down, always swapping with the smaller
     * child (the LEFT child if both children are equal), until it is no
     * larger than its children or reaches a leaf.
     */
    // purpose: moves a value downward until heap order is restored
    // input: index that is move downward
    // output: none
    // example: a root larger than its child is swap downward
    private void percolateDown(int i) {
        while (2 * i <= size) {
            int leftChild = 2 * i;
            int rightChild = 2 * i + 1;
            int smallerChild = leftChild;

            if (rightChild <= size && heap[rightChild] < heap[leftChild]) {
                smallerChild = rightChild;
            }

            if (heap[i] <= heap[smallerChild]) {
                return;
            }

            swap(i, smallerChild);
            i = smallerChild;
        }
    }

    /**
     * Exchanges heap[a] and heap[b].
     * a and b are the indices of the values to swap
     */

    // purpose: exchanges two values in the heap array
    // input: indexes in the heap array
    // output: none
    // example: swap(3, 4) exchanges the root and left child
    private void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    /** Doubles the array's capacity, keeping every value at the same index. */

    // purpose: double the heap array
    // input: none
    // output: non
    // example: heap of 2 capacity to heap of 4 capacity
    private void grow() {
        int[] doubleHeap = new int[heap.length * 2];

        for (int i = 1; i <= size; i++) {
            doubleHeap[i] = heap[i];
        }

        heap = doubleHeap;
    }

    /**
     * Bottom up heap construction.
     * Restores heap order over heap[1] .. heap[size] by percolating down
     * each non-leaf node, starting at the last one (index size / 2) and
     * working back to the root (index 1).
     */

    // purpose: restores heap order using bottom-up heap construction
    // input: none
    // output: non
    // example: values {9, 4, 7, 1} are reorganized into a min heap

    private void buildHeap() {
        for (int i = size / 2; i >= 1; i--) {
            percolateDown(i);
        }
    }

    /**
     * Returns a new array holding values in ascending order: builds a
     * MinHeap from values with the MinHeap(int[]) constructor, then calls
     * removeMin() repeatedly. values itself must not be changed.
     * throws IllegalArgumentException if values is null
     */

    // purpose: returns a sorted copy of an integer array ascending order
    // input: values an array of integers
    // output: a new sorted array
    // example: heapsort({4, 1, 3}) return {1, 3, 4}
    public static int[] heapSort(int[] values) {
        MinHeap h = new MinHeap(values);
        int[] sorted = new int[values.length];

        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = h.removeMin();
        }

        return sorted;
    }
}