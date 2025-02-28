package Figures;

public class Horse extends Figure{
    public Horse(char name, char team) {
        super(name, team);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        return (Math.abs(row - row1) == 1 && Math.abs(col - col1) == 2) || (Math.abs(row - row1) == 2 && Math.abs(col - col1) == 1);
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        return this.canMove(row, col, row1, col1);
    }
}
