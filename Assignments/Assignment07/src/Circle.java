public class Circle extends Shape{

    protected double radius;

    public Circle(){
        super();
        radius = 1.0;
    }

    public Circle(double radius){
        super();
        this.radius = radius;

        if(radius < 0)
            throw new IllegalArgumentException("Negative Dimension: "+radius);
    }

    public Circle(double radius, String color, boolean filled){
        super(color, filled);
        this.radius = radius;

        if(radius < 0)
            throw new IllegalArgumentException("Negative Dimension: "+radius);
    }


    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea(){
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter(){
        return Math.PI * 4 * radius;
    }

    @Override
    public String toString(){
        return "Circle["+super.toString()+",radius="+radius+"]";
    }
}
