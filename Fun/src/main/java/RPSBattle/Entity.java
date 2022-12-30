package RPSBattle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static RPSBattle.RPSViewer.ENTITY_SIZE;

public class Entity {

    public static final int MAX_MOVEMENT = 5;
    private static Image rock, paper, scissor;

    private int x, y, type;

    public Entity(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }


    public void animate(List<Entity> entities, Rectangle bounds){

        Entity target = getClosestOfType(entities, (type + 2) % 3);
        Entity enemy = getClosestOfType(entities, (type + 1) % 3);

        Vector2D movement = getMovementVector(target, enemy);
        //Vector2D movement = getMovementVectorOneLine(entities);

        x += movement.x;
        y += movement.y;

        x = Math.max(0, Math.min(x, bounds.width - ENTITY_SIZE));
        y = Math.max(0, Math.min(y, bounds.height - ENTITY_SIZE));

        type = entities.stream().filter(e -> e.type == (type + 1) % 3)
                .noneMatch(e -> e.intersects(this)) ? type : (type + 1) % 3;
    }


    public Vector2D getMovementVector(Entity target, Entity enemy){

        //If there is nothing, do nothing
        if(enemy == null && target == null) {
            return new Vector2D(0, 0);
        }

        //If there are no enemies just go after target
        if(enemy == null){
            Vector2D targetVector = new Vector2D(target.x - this.x, target.y - this.y);
            return targetVector.scale(1 / targetVector.getLength()).scale(MAX_MOVEMENT);
        }
        //If there are no targets just run away
        if(target == null){
            Vector2D enemyVector = new Vector2D(this.y - enemy.x, this.y - enemy.y);
            return enemyVector.scale(1 / enemyVector.getLength()).scale(MAX_MOVEMENT);
        }

        //if we end up right on the target do nothing (This will probably never happen but just in case)
        if((target.x == x && target.y == y) || (enemy.y == y && enemy.x == x)) {
            return new Vector2D(0, 0);
        }

        // get the vectors
        Vector2D targetVector = new Vector2D(target.x - this.x, target.y - this.y);
        Vector2D enemyVector = new Vector2D(this.y - enemy.x, this.y - enemy.y);
        // unitize to only get direction
        targetVector = targetVector.scale(1 / targetVector.getLength());
        enemyVector = enemyVector.scale(1 / enemyVector.getLength());
        // inverse square
        targetVector = targetVector.scale(1 / (  distTo(target) * distTo(target)));
        enemyVector = enemyVector.scale(1 / ( distTo(enemy) * distTo(enemy) ));

        Vector2D movement = targetVector.add(enemyVector);
        movement = movement.scale(1 / movement.getLength());

        movement = movement.scale(MAX_MOVEMENT);

        return movement;
    }


    public Vector2D getMovementVectorOneLine(List<Entity> entities){

        return entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null) == null && entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null) == null ? new Vector2D(0,0) : entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null) == null ? new Vector2D(this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x, this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y).scale(1 / new Vector2D(this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x, this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y).getLength()).scale(MAX_MOVEMENT) : entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null) == null ? new Vector2D(Objects.requireNonNull(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null)).x - this.x, entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y - this.y).scale(1 / new Vector2D(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x - this.x, Objects.requireNonNull(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null)).y - this.y).getLength()).scale(MAX_MOVEMENT) : (entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x == x && entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y == y) || (entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y == y && entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x == x) ? new Vector2D(0,0) : new Vector2D(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x - this.x, entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y - this.y).scale(1/new Vector2D(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x - this.x, entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y - this.y).getLength()).scale(1 / Math.pow(distTo(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null)), 2)).add(new Vector2D(this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x, this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y).scale(1/new Vector2D(this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x, this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y).getLength()).scale(1 / Math.pow(distTo(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null)), 2))).scale(MAX_MOVEMENT / new Vector2D(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x - this.x, entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y - this.y).scale(1/new Vector2D(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x - this.x, entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y - this.y).getLength()).scale(1 / Math.pow(distTo(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null)), 2)).add(new Vector2D(this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x, this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y).scale(1/new Vector2D(this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).x, this.y - entities.stream().filter(e -> e.type == (type + 1) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null).y).getLength()).scale(1 / Math.pow(distTo(entities.stream().filter(e -> e.type == (type + 2) % 3).min(Comparator.comparingDouble(this::distTo)).orElse(null)), 2))).getLength());
    }




    public boolean intersects(Entity e){
        return getHitBox().intersects(e.getHitBox());
    }

    public Rectangle getHitBox(){
        return new Rectangle(x, y, ENTITY_SIZE, ENTITY_SIZE);
    }

    public void draw(Graphics2D g, ImageObserver io){

        if(type == RockPaperScissors.ROCK) {
            g.drawImage(rock, x, y, ENTITY_SIZE, ENTITY_SIZE, io);
        } else if(type == RockPaperScissors.SCISSOR) {
            g.drawImage(scissor, x, y, ENTITY_SIZE, ENTITY_SIZE, io);
        }else if(type == RockPaperScissors.PAPER)
            g.drawImage(paper, x, y, ENTITY_SIZE, ENTITY_SIZE, io);

    }

    public double distTo(Entity target){
        return Math.sqrt(Math.pow(this.x - target.x,2) + Math.pow(this.y - target.y,2));
    }


    public Entity getClosestOfType(List<Entity> entities, int wantedType){

        return entities.stream().filter(e -> e.type == wantedType)
                .min(Comparator.comparingDouble(this::distTo))
                .orElse(null);
    }


    public static void initImages(){
        try {
            rock = ImageIO.read(new File("Fun/src/main/java/RPSBattle/rock.png"));
            paper = ImageIO.read(new File("Fun/src/main/java/RPSBattle/paper.png"));
            scissor = ImageIO.read(new File("Fun/src/main/java/RPSBattle/scissor.png"));
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }


    public int getType(){return type;}


}
