package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayHeapMaxPQ<T> implements ExtrinsicMaxPQ<T> {
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    private int size;
    private HashMap<T, Integer> auxItems;

    public ArrayHeapMaxPQ() {
        items = new ArrayList<>();
        auxItems = new HashMap<>();
        items.add(null);
        size = 0;
    }

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> tempNode = items.get(a);
        T aItem = tempNode.getItem();
        T bItem = items.get(b).getItem();
        auxItems.replace(aItem, b);
        auxItems.replace(bItem, a);
        items.set(a, items.get(b));
        items.set(b, tempNode);
    }

    @Override
    public void add(T item, double priority) {
        if (item == null || contains(item)) {
            throw new IllegalArgumentException();
        }
        size++;
        this.items.add(new PriorityNode<>(item, priority));
        auxItems.put(item, size);
        percolateUp(size);
    }

    /**
     * helper method to swap two elements in an array if one has a lower priority
     * @param index - index to be percolated up
     */
    private void percolateUp(int index) {
        int parentIndex = index / 2;
        if (index != 1 && items.get(index).getPriority() > items.get(parentIndex).getPriority()) {
            swap(index, parentIndex);
            percolateUp(parentIndex);
        }
    }

    @Override
    public boolean contains(T item) {
        return auxItems.containsKey(item);
    }

    @Override
    public T peekMax() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return this.items.get(START_INDEX).getItem();
    }

    @Override
    public T removeMax() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T maxVal = peekMax();
        auxItems.remove(maxVal);
        items.set(START_INDEX, items.get(size));
        items.remove(size);
        size--;
        percolateDown(START_INDEX);
        return maxVal;
    }

    private void percolateDown(int index) {
        int leftIndex = 2 * index;
        int rightIndex = leftIndex + 1;
        int childIndex = leftIndex;
        if (rightIndex <= size) {
            if (items.get(leftIndex).getPriority() < items.get(rightIndex).getPriority()) {
                childIndex = rightIndex;
            }
        }
        if (childIndex <= size) {
            if (items.get(index).getPriority() < items.get(childIndex).getPriority()) {
                swap(index, childIndex);
                percolateDown(childIndex);
            }
        }
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = auxItems.get(item);
        double oldPriority = items.get(index).getPriority();
        items.get(index).setPriority(priority);
        if (oldPriority > priority) {
            percolateDown(index);
        } else {
            percolateUp(index);
        }
    }

    @Override
    public int size() {
        return size;
    }
}
