public class Cat extends Animal{

    public Cat(String name){
        super(name);
    }

    @Override
    public String greets(Animal animal){
        return "Meow";
    }


}
