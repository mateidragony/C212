package RPSBattle;

import java.text.DecimalFormat;

public class Vector2D {

    private static final DecimalFormat df = new DecimalFormat("#.###");
    protected double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public Vector2D add(Vector2D v){
        return new Vector2D(x + v.x, y + v.y);
    }

    public Vector2D scale(double scale){
        return new Vector2D(scale * x, scale * y);
    }

    public double getLength(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }


    @Override public String toString(){
        return "<"+df.format(x)+", "+df.format(y)+">";
    }
}
