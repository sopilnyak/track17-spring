package track.lessons.lesson1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Задание 1: Реализовать два метода
 *
 * Формат файла: текстовый, на каждой его строке есть (или/или)
 * - целое число (int)
 * - текстовая строка
 * - пустая строка (пробелы)
 *
 *
 * Пример файла - words.txt в корне проекта
 *
 * ******************************************************************************************
 *  Пожалуйста, не меняйте сигнатуры методов! (название, аргументы, возвращаемое значение)
 *
 *  Можно дописывать новый код - вспомогательные методы, конструкторы, поля
 *
 * ******************************************************************************************
 *
 */
public class CountWords {

    /**
     * Метод на вход принимает объект File, изначально сумма = 0
     * Нужно пройти по всем строкам файла, и если в строке стоит целое число,
     * то надо добавить это число к сумме
     * @param file - файл с данными
     * @return - целое число - сумма всех чисел из файла
     */
    public long countNumbers(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        long acc = 0;
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            try {
                int number = Integer.parseInt(line);
                acc += number;
            } catch (NumberFormatException e) {
            }
        }
        return acc;
    }


    /**
     * Метод на вход принимает объект File, изначально результат= ""
     * Нужно пройти по всем строкам файла, и если в строка не пустая и не число
     * то надо присоединить ее к результату через пробел
     * @param file - файл с данными
     * @return - результирующая строка
     */
    public String concatWords(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        StringBuilder acc = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            try {
                Integer.parseInt(line);
            } catch (NumberFormatException e) {
                acc.append(line);
                acc.append(" ");
            }
        }

        /* Remove last space */
        if (acc.length() > 0) {
            acc.setLength(acc.length() - 1);
        }
        return acc.toString();
    }

}
