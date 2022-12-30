public class Circle extends Object{


    public static abstract class Auto{

        public abstract void makeNoise(int a);

    }

    public static final class Car extends Auto{

        public void makeNoise(int a){
            System.out.println("CAR!");
        }

    }


    public static void main(String[] args) {

       Car a1 = new Car();
       a1.makeNoise(2);

    }

}
