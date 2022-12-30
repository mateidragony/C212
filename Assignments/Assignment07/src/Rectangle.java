public class Rectangle extends Shape{

    protected double width;
    protected double length;

    public Rectangle(){
        super();
        width = 1.0;
        length = 1.0;
    }

    public Rectangle(double width, double length){
        super();
        this.length = length;
        this.width = width;

        if(length < 0)
            throw new IllegalArgumentException("Negative Dimension: "+length);
        if(width < 0)
            throw new IllegalArgumentException("Negative Dimension: "+width);
    }

    public Rectangle(double width, double length, String color, boolean filled){
        super(color, filled);
        this.length = length;
        this.width = width;

        if(length < 0)
            throw new IllegalArgumentException("Negative Dimension: "+length);
        if(width < 0)
            throw new IllegalArgumentException("Negative Dimension: "+width);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public double getArea(){
        return length*width;
    }

    @Override
    public double getPerimeter(){
        return 2*width + 2*length;
    }

    @Override
    public String toString(){
        return "Rectangle["+super.toString()+",width="+width+",length="+length+"]";
    }


}
