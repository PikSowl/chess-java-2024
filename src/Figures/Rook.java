package Figures;

public class Rook extends Figure{
    public Rook(char name, char team) {
        super(name, team);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        return row == row1 || col == col1;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        return this.canMove(row, col, row1, col1);
    }
}
