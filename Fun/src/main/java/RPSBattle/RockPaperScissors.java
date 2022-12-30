package RPSBattle;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static RPSBattle.RPSViewer.NUM_FIGHTERS;

public class RockPaperScissors {

    final static int ROCK = 0, PAPER = 1, SCISSOR = 2;
    final Random randy = new Random();

    final int[] wins;

    List<Entity> entities;
    final Rectangle bounds;

    public RockPaperScissors(Rectangle bounds){

        this.bounds = bounds;

        wins = new int[3];

        reset();

        Entity.initImages();

    }


    public void reset(){
        //Make the list with Rocks, Papers, and Scissors
        entities = Stream.concat(Stream.generate(() -> getEntity(ROCK)).limit(NUM_FIGHTERS),
                        Stream.concat(Stream.generate(() -> getEntity(PAPER)).limit(NUM_FIGHTERS),
                                Stream.generate(() -> getEntity(SCISSOR)).limit(NUM_FIGHTERS)))
                .collect(Collectors.toList());

        Collections.shuffle(entities);
    }

    public Entity getEntity(int type){
        return new Entity(randy.nextInt(bounds.width),randy.nextInt(bounds.height), type);
    }


    public void animate(){
        entities.forEach(e -> e.animate(entities, bounds));

        if(entities.stream().map(Entity::getType).collect(Collectors.toSet()).size() == 1){
            int winner = entities.get(0).getType();
            wins[winner]++;
            System.out.println("Type: "+winner+" wins");
            reset();
        }

    }

    public void draw(Graphics2D g, ImageObserver io){
        entities.forEach(e -> e.draw(g, io));
    }
}
