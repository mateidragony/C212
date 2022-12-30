import java.util.ArrayList;

public class PetDayCare {

    ArrayList<Animal> pets;

    public PetDayCare(ArrayList<Animal> pets) {
        this.pets = pets;
    }

    private String addToSoundTable(String soundTable, String newSound) {
        return soundTable + String.format("%-16s", newSound);
    }

    public String[][] createSimplifiedSoundArray() {
        String[][] soundGrid = new String[pets.size()][pets.size()];
        for (int i = 0; i < pets.size(); i++) {
            for (int j = 0; j < pets.size(); j++) {
                Animal petDoingTheGreeting = pets.get(i);
                Animal petBeingGreeted = pets.get(j);
                if (!petDoingTheGreeting.equals(petBeingGreeted)) { // we could use i != j too, but this shows how
                    // objects are compared in Java. Since they're the
                    // same object in memory, this works, but remember
                    // just because two pets have the same name does
                    // NOT mean they are the same object!
                    soundGrid[i][j] = petDoingTheGreeting.greets(petBeingGreeted);
                } else {
                    soundGrid[i][j] = null; // pets lack self-awareness, and so don't talk to themselves
                }
            }
        }
        return soundGrid;
    }

    private String petGreetsPet(Animal petDoingTheGreeting, Animal petBeingGreeted) {
        // we need to find out what specific classes these pets are instances of, and cast them to that
        // Without the use of some other OO concepts we haven't seen yet, this gets complicated
        // We have to check each combination of possible Animal subtypes to get the "right" greeting
        // (if it matters to that Animal subtype: e.g. a _____ and _____ cares what it is greeting but a _____ does not)
        // Also, we have to check the most specific subtypes first, since a _____ is a Dog,
        // so we start with _____, then _____, then _____
        // Notice if we added more Animal subtypes we'd have to manually update this method!! This is not ideal!
        if (petDoingTheGreeting instanceof Chihuahua) {
            if (petBeingGreeted instanceof Chihuahua) {
                return ((Chihuahua) petDoingTheGreeting).greets((Chihuahua) petBeingGreeted);
            } else if (petBeingGreeted instanceof Dog) {
                return ((Chihuahua) petDoingTheGreeting).greets((Dog) petBeingGreeted); //
            } else if (petBeingGreeted instanceof Cat) {
                return ((Chihuahua) petDoingTheGreeting).greets((Cat) petBeingGreeted); //
            }
        }

        else if (petDoingTheGreeting instanceof Dog) { //
            if (petBeingGreeted instanceof Dog) {
                // we can lump in _____ as Dogs, since Dogs don't differentiate Dogs
                return ((Dog)(petDoingTheGreeting)).greets((Dog)petBeingGreeted); //
            } else {
                // We can lump in _____ as Animals, since Dogs don't differentiate Animals besides other Dogs
                return ((Dog)petDoingTheGreeting).greets(petBeingGreeted); //
            }
        }

        else if (petDoingTheGreeting instanceof Cat) {
            // We can lump in all Animals as Animals, since _____ don't differentiate Animals
            return petDoingTheGreeting.greets(petBeingGreeted);
        }
        throw new IllegalArgumentException("Unsupported animal interaction");
        // TODO fill in all the blanks in the comments in this method
    }

    public String[][] createSoundArray() {
        String[][] soundGrid = new String[pets.size()][pets.size()]; // TODO
        for (int i = 0; i < pets.size(); i++) {
            for (int j = 0; j < pets.size(); j++) {
                Animal petDoingTheGreeting = pets.get(i);
                Animal petBeingGreeted = pets.get(j);
                if (!petDoingTheGreeting.equals(petBeingGreeted)) { // we could use i != j too, but this shows how
                    // objects are compared in Java. Since they're the
                    // same object in memory, this works, but remember
                    // just because two pets have the same name does
                    // NOT mean they are the same object!
                    soundGrid[i][j] = petGreetsPet(petDoingTheGreeting, petBeingGreeted);
                } else {
                    soundGrid[i][j] = null; // pets lack self-awareness, and so don't talk to themselves
                }
            }
        }
        return soundGrid;
    }

    @Override
    public String toString() {
        String soundTable = "";
        String[][] soundArray = createSoundArray(); // change this to createSoundArray for Part D
        for (int i = -1; i < pets.size(); i++) {
            for (int j = -1; j < pets.size(); j++) {
                // i or j == -1 means we are adding a label to our "table"
                // both means we add the title, i.e. the top-left cell
                try {
                    if (i == -1) {
                        soundTable = addToSoundTable(soundTable, pets.get(j).getName());
                    } else {
                        if (j == -1) {
                            soundTable = addToSoundTable(soundTable, pets.get(i).getName());
                        } else if (soundArray[i][j] != null) {
                            soundTable = addToSoundTable(soundTable, soundArray[i][j]);
                        } else {
                            soundTable = addToSoundTable(soundTable, "---"); // the diagonals
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    soundTable = addToSoundTable(soundTable, "Meet and Greet!"); // the title
                }
            }
            soundTable += "\n";
        }
        return soundTable;
    }

}



