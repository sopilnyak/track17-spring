package track.container;

import track.container.beans.Car;
import track.container.config.Bean;
import track.container.config.ConfigReader;

import java.util.List;

/**
 *
 */
public class Main {

    public static void main(String[] args) {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

        // При чтении нужно обработать исключение
        try {
            // ConfigReader reader = new JsonConfigReader();
            // List<Bean> beans = reader.parseBeans("config.json");
            Container container = new Container("config.json");

            Car car = (Car) container.getByClass("track.container.beans.Car");
            car = (Car) container.getById("carBean");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
