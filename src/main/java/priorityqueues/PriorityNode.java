package priorityqueues;

/**
 * Node class to be used with PriorityQueues. This class was originally written by the CSE 373
 * staff at the University of Washington.
 *
 * @param <T> Generic type
 */
public class PriorityNode<T> {
    private final T item;
    private double priority;

    PriorityNode(T e, double p) {
        this.item = e;
        this.priority = p;
    }

    T getItem() {
        return this.item;
    }

    double getPriority() {
        return this.priority;
    }

    void setPriority(double priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "PriorityNode{" +
                "item=" + item +
                ", priority=" + priority +
                '}';
    }
}
