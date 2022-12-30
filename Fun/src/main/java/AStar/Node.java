package AStar;

import java.util.HashMap;
import java.util.Objects;

public class Node {

    protected final int x,y;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "Node("+x+", "+y+")";
    }


    public boolean equals(Object n){
        assert n instanceof Node;
        Node node = (Node)n;
        return x == node.x && y == node.y;
    }

    public int hashCode(){
        return Objects.hash(x,y);
    }

}
