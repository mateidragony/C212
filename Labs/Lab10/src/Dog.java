public class Dog extends Animal{

    public Dog(String name) {
        super(name);
    }

    @Override
    public String greets(Animal animal) {
        return "Woof";
    }

    public String greets(Dog dog){
        return "Howl!";
    }

}
