package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List {

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    void add(int item) {
        Node newNode = new Node(tail, null, item);

        if (head == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        super.add(item);
    }

    @Override
    int remove(int idx) throws NoSuchElementException {

        super.remove(idx);

        Node currentNode = head;
        for (int i = 0; i < idx; i++) {
            if (currentNode.next != null) {
                currentNode = currentNode.next;
            }
        }

        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        }

        size--;
        return currentNode.val;
    }

    @Override
    int get(int idx) throws NoSuchElementException {

        super.get(idx);

        Node currentNode = head;
        for (int i = 0; i < idx; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.val;
    }

    private Node head;
    private Node tail;
}
