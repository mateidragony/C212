



public class TestCircle {
    public static void main(String[] args) {

        Circle c1 = new Circle();
        System.out.println("The circle has radius of " + c1.getRadius()
                + " and area of " + c1.getArea());

        Circle c2 = new Circle(2.0);
        System.out.println("The circle has radius of " + c1.getRadius()
                + " and area of " + c1.getArea());


        //System.out.println(c1.radius);
        //Ruh-roh radius is private, so I can't access it in a different class!!
    }
}