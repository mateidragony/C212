public class DeepDishPizza extends Pizza{

    public DeepDishPizza(int radius, String topping){
        super(radius, topping);
    }


    @Override
    public double getPrice(){
        return getArea() * 0.25;
    }


}
