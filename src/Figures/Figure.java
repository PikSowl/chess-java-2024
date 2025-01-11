package Figures;

public abstract class Figure {
    private char name;
    private char team;

    public Figure(char name, char team){
        this.name = name;
        this.team = team;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {this.name = name;}

    public char getTeam() {
        return team;
    }

    public void setTeam(char team) {
        this.team = team;
    }

    public boolean canMove(int row, int col, int row1, int col1){
        return  (row>= 0 && row < 8) && (col>=0 && col<8) &&
                (row1>= 0 && row1 < 8) && (col1>=0 && col1<8) &&
                ((col!=col1) || (row!=row1));
    }

    public boolean canAttack(int row, int col, int row1, int col1){
        return this.canMove(row, col, row1, col1);
    }
}
