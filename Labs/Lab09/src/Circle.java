

public class Circle {

    protected double radius;


    public Circle(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("A circle must have a non-negative radius");
        }
        this.radius = radius;
    }


    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public double getArea() {
        return radius * radius * Math.PI;
    }
}