public class Square extends Rectangle{

    public Square(){
        super(1.0,1.0);
    }

    public Square(double side){
        super(side,side);
        if(side < 0)
            throw new IllegalArgumentException("Negative Dimension: "+side);
    }

    public Square(double side, String color, boolean filled){
        super(side, side, color, filled);
        if(side < 0)
            throw new IllegalArgumentException("Negative Dimension: "+side);
    }


    public double getSide() {
        return length;
    }

    public void setSide(double side) {
        length = side;
        width = side;
    }

    @Override
    public void setLength(double length){
        this.length = length;
        this.width = length;
    }

    @Override
    public void setWidth(double width){
        this.length = width;
        this.width = width;
    }

    @Override
    public String toString(){
        return "Square["+super.toString()+"]";
    }
}
