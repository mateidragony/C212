import java.util.ArrayList;
import java.util.Arrays;

public class PetDayCareRunner {

    public static void main(String[] args) {

        ArrayList<Animal> pets = new ArrayList<>(Arrays.asList(new Cat("Lucy"),
                new Dog("Sparky"),
                new Chihuahua("Fred"),
                new Chihuahua("Tripod")));

        PetDayCare pdc = new PetDayCare(pets);

        System.out.println(pdc);
    }

}
