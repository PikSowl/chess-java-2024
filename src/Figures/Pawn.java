package Figures;

public class Pawn extends Figure {
    private boolean isFirstStep = true;
    public Pawn(char name, char team) {
        super(name, team);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if(this.isFirstStep){
            if ((((row+2==row1 || row+1==row1) && this.getTeam()=='*')
                    || ((row-2==row1 || row-1==row1) && this.getTeam()=='#')) && col==col1){
                this.isFirstStep = false;
                return true;
            }
        }else return ((row + 1 == row1 && this.getTeam() == '*')
                || (row - 1 == row1 && this.getTeam() == '^')) && col == col1;
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        return ((getTeam() == '*' && row1 - row == 1) || (getTeam() == '#' && row - row1 == 1))
                && (col - col1 == 1 || col1 - col == 1);
    }
}
