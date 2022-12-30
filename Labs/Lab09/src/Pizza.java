

public class Pizza extends Circle{


    private String topping;

    public Pizza(int radius, String topping){
        super(radius);

        this.topping = topping;

    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public double getPrice(){
        return getArea() * 0.15;
    }

}
