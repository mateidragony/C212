package AStar;

import java.util.*;

public class AStar {

    public static final int WALL = 10;

    //G(n) is cost from start to node
    //H(n) is cost from node to goal
    private Node start, goalChosen;
    private Node[] goals;
    private final double p = 1.0/1000;


    HashMap<Node, Node> cameFrom = new HashMap<>(); //Second node is which node we came from to get to first node
    HashMap<Node, Integer> costSoFar = new HashMap<>();

    PriorityQueue<PriorityEntry> openNodes, closedNodes;



    public AStar(Node start, Node... goals){
        openNodes = new PriorityQueue<>(Comparator.comparingDouble((o) -> o.priority));
        closedNodes = new PriorityQueue<>(Comparator.comparingDouble((o) -> o.priority));

        openNodes.add(new PriorityEntry(start, 0));

        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        this.goals = goals;
        this.start = start;
    }


    public double h(Node n){
        double dist = Double.MAX_VALUE;

        for(Node goal: goals) {
            double dx = Math.abs(n.x - goal.x);
            double dy = Math.abs(n.y - goal.y);
            dist = Math.min(Math.sqrt(dx * dx + dy * dy) * (1.0 + p), dist);
        }

        return dist;
    }


    public List<Node> getNeighbors(Node node, int[][] graph){
        List<Node> neighbors = new ArrayList<>();

        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                int x = node.x + i;
                int y = node.y + j;

                if(x >= 0 && y >= 0 && x < graph[0].length && y < graph.length &&
                        (x!= node.x || y!= node.y) && graph[y][x] != WALL){
                    Node n = new Node(x,y);
                    neighbors.add(n);
                }

            }
        }

        return neighbors;
    }

    public boolean openNodesContains(Node n){
        return openNodes.stream().map(e -> e.element).anyMatch(e -> e.equals(n));
    }

    public List<Node> reconstructPath(){
        Node current = goalChosen;

        if(current == null)
            return null;

        List<Node> path = new ArrayList<>();
        while(!current.equals(start)) {
            path.add(current);
            current = cameFrom.get(current);

            if(current == null)
                return null;

        }
        path.add(start);

        Collections.reverse(path);
        return path;
    }

    public List<Node> runAlgorithm(int[][] graph){

        while(!openNodes.isEmpty()){

            Node node = openNodes.poll().element;

            for(Node goal: goals) {
                if ((node.equals(goal))) {
                    goalChosen = new Node(node.x,node.y);
                    break;
                }
            }

            for(Node next : getNeighbors(node, graph)){

                int newCost = costSoFar.get(node) + graph[next.y][next.x];

                if(!cameFrom.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    double priority = newCost + h(next);

                    if(!openNodesContains(next))
                        openNodes.add(new PriorityEntry(next, priority));
                    cameFrom.put(next, node);
                }
            }
        }

        return reconstructPath();
    }


    public static void main(String[] args) {

        AStar astar = new AStar(new Node(0,0),new Node(8,8));

        int[][] graph = {
                {1,1,1,1,1,1,1,1,1},
                {1,100,1,1,1,1,1,1,1},
                {1,100,1,1,1,1,100,1,1},
                {1,100,1,1,1,1,100,1,1},
                {1,100,1,1,1,100,100,1,1},
                {1,100,100,100,100,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,100,1},
                {1,1,1,1,1,1,1,100,1},
        };

        astar.runAlgorithm(graph);

    }




    private class PriorityEntry implements Comparable<PriorityEntry>{

        Node element;
        double priority;

        public Node getElement(){
            return element;
        }

        public PriorityEntry(Node node, double priority){
            element = node;
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityEntry o) {
            return Double.compare(priority, o.priority);
        }

        public String toString(){
            return element.toString();
        }

    }


}
