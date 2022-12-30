public class Chihuahua extends Dog{


    public Chihuahua(String name) {
        super(name);
    }

    @Override
    public String greets(Animal animal){
        return "Arf";
    }

    @Override
    public String greets(Dog dog){
        return "Arf Arf!";
    }

    public String greets(Chihuahua chihuahua){
        return "Como estas?";
    }


}
