package track.lessons.lesson3;

import java.util.NoSuchElementException;
import java.lang.System;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    public MyArrayList() {
        final int defaultCapacity = 10;

        buffer = new int[defaultCapacity];
        bufferSize = defaultCapacity;
        size = 0;
    }

    public MyArrayList(int capacity) {
        buffer = new int[capacity];
        bufferSize = capacity;
        size = 0;
    }

    @Override
    void add(int item) {
        if (size == bufferSize) {
            int[] newBuffer = new int[(bufferSize + 1) * 2];
            System.arraycopy(buffer, 0, newBuffer, 0, size);
            buffer = newBuffer;
            bufferSize = (bufferSize + 1) * 2;
        }
        buffer[size] = item;
        super.add(item);
    }

    @Override
    int remove(int idx) throws NoSuchElementException {

        super.remove(idx);

        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }

        int[] newBuffer = new int[bufferSize];
        size--;
        System.arraycopy(buffer, 0, newBuffer, 0, idx);
        System.arraycopy(buffer, idx + 1, newBuffer, idx, size - idx);

        int removed = buffer[idx];
        buffer = newBuffer;

        return removed;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        super.get(idx);
        return buffer[idx];
    }

    private int[] buffer;
    private int bufferSize;
}
