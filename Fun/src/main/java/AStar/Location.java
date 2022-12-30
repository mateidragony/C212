package AStar;

public class Location {

    private int row, col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Location getAdjacentLocation(int direction){
        return this;
    }

    public int getDirectionToward(Location l){
        return 1;
    }

    public boolean isValidLocation(){
        return true;
    }
}
