package track.lessons.lesson1;

import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
public class CountWordsTest {

    static File file;

    @Ignore
    @BeforeClass
    public static void init() {
        file = new File("words.txt");
    }


    @Ignore
    @Test
    public void countNumbers() throws Exception {
        CountWords countWords = new CountWords();
        Assert.assertEquals(42, countWords.countNumbers(file));
    }

    @Ignore
    @Test
    public void concatWords() throws Exception {
        CountWords countWords = new CountWords();
        Assert.assertEquals("hello world !", countWords.concatWords(file));
    }

}