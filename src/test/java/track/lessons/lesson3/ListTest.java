package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class ListTest {

    private <ListType extends List> void randomAddGetRemove(ListType list) throws Exception {
        ArrayList<Integer> arrayList = new ArrayList<>(0);
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            int choice = random.nextInt();

            boolean isExceptionExpected = false;
            boolean isExceptionThrown = false;

            if (choice % 3 == 0) {
                arrayList.add(i);
                list.add(i);
                i++;
                Assert.assertEquals(arrayList.size(), list.size());
            }
            if (choice % 3 == 1) {
                try {
                    arrayList.get(choice % 30);
                } catch (IndexOutOfBoundsException e) {
                    isExceptionExpected = true;
                } finally {
                    try {
                        list.get(choice % 30);
                    } catch (NoSuchElementException e) {
                        isExceptionThrown = true;
                    } finally {
                        Assert.assertEquals(isExceptionExpected, isExceptionThrown);
                    }
                }
            }
            if (choice % 3 == 2) {
                try {
                    arrayList.remove(choice % 30);
                } catch (IndexOutOfBoundsException e) {
                    isExceptionExpected = true;
                } finally {
                    try {
                        list.remove(choice % 30);
                    } catch (NoSuchElementException e) {
                        isExceptionThrown = true;
                    } finally {
                        Assert.assertEquals(isExceptionExpected, isExceptionThrown);
                    }
                }
            }
        }
    }

    @Test
    public void randomAddGetRemoveTest() throws Exception {
        MyArrayList arrayList = new MyArrayList();
        randomAddGetRemove(arrayList);

        MyLinkedList linkedList = new MyLinkedList();
        randomAddGetRemove(linkedList);
    }
}
