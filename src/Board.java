import Figures.*;

import java.util.ArrayList;

public class Board {
    private boolean isStarsTurn;
    private boolean isCheckedStar = false;
    private boolean isCheckedDark = false;

    public void setStarsTurn(boolean isStarsTurn) {
        this.isStarsTurn = isStarsTurn;
    }

    public boolean getStarsTurn() {
        return isStarsTurn;
    }

    public void passTurn(){
        isStarsTurn = !isStarsTurn;
    }


    private Figure fields[][] = new Figure[8][8];
    private ArrayList<String> takeStar = new ArrayList(16);
    private ArrayList<String> takeDark = new ArrayList(16);
    public ArrayList<String> getTakeDark() {
        return takeDark;
    }
    public ArrayList<String> getTakeStar() {
        return takeStar;
    }

    public void init(){
        this.fields[0] = new Figure[]{
                new Rook('R', '*'), new Horse('H', '*'), new Bishop('B', '*'), new Queen('Q', '*'),
                new King('K', '*'), new Bishop('B', '*'), new Horse('H', '*'), new Rook('R','*')
        };
        this.fields[1] = new Figure[]{
                new Pawn('P', '*'), new Pawn('P', '*'), new Pawn('P', '*'), new Pawn('P', '*'),
                new Pawn('P', '*'), new Pawn('P', '*'), new Pawn('P', '*'), new Pawn('P', '*')
        };

        this.fields[6] = new Figure[] {
                new Pawn('P', '#'), new Pawn('P', '#'), new Pawn('P', '#'), new Pawn('P', '#'),
                new Pawn('P', '#'), new Pawn('P', '#'), new Pawn('P', '#'), new Pawn('P', '#'),
        };

        this.fields[7] = new Figure[]{
                new Rook('R', '#'), new Horse('H', '#'), new Bishop('B', '#'), new Queen('Q', '#'),
                new King('K', '#'), new Bishop('B', '#'), new Horse('H', '#'), new Rook('R','#')
        };
    }


    public String getCell(int row, int col){
        Figure figure = this.fields[row][col];
        if (figure == null){
            return "    ";
        }
        return  " "+figure.getTeam()+figure.getName()+" ";
    }
    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1 ; row --){
            System.out.print(row + 1);
            for (int col=0; col<8; col++){
                System.out.print("|"+getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col=0; col< 8; col++){
            System.out.print("    "+ (char)(65+col));
        }
    }

    public boolean isKingAttacked(int row, int col, char team){
        for (int row2 = 0; row2 < 8; row2++)
            for (int col2 = 0; col2 < 8; col2++) {
                Figure attacker = fields[row2][col2];
                if (attacker != null)
                    if (attacker.getTeam() != team && attacker.canAttack(row2, col2, row, col))
                        if (clearPath(row2, col2, row, col)){
                            System.out.println(attacker.getTeam() + "" + attacker.getName() + " " + (char)(col2 + 'A') + (row2+1) + " Шах");
                            return true;
                        }
            }
        return false;
    }

    public boolean isCheck(char team){
        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++)
                if (fields[row][col] != null)
                    if (fields[row][col].getName() == 'K' && fields[row][col].getTeam() == team){
                        boolean ka = isKingAttacked(row, col, team);
                        if(team == '*') isCheckedStar = ka;
                        else isCheckedDark = ka;
                        return ka;
                    }
        return false;
    }

    public boolean isMate(){
        return (isStarsTurn && isCheckedStar) || (!isStarsTurn && isCheckedDark);
    }

    private boolean clearPath(int row, int col, int row1, int col1){
        //Diagonal
        if (Math.abs(row - row1) == Math.abs(col - col1) && Math.abs(row - row1) > 1) {
            if (row > row1 && col > col1) {
                for (int i = 1; row > row1 + i; i++)
                    if (fields[row1 + i][col1 + i] != null) return false;
            } else if (row > row1 && col < col1) {
                for (int i = 1; row > row1 + i; i++)
                    if (fields[row1 + i][col1 - i] != null) return false;
            } else if (row < row1 && col > col1){
                for (int i = 1; row < row1 - i; i++)
                    if (fields[row1 - i][col1 + i] != null) return false;
            } else if (row < row1 && col < col1) {
                for (int i = 1; row < row1 - i; i++)
                    if (fields[row1 - i][col1 - i] != null) return false;
            }
        }

        //Vertical
        else if ((Math.abs(row - row1) > 1 && Math.abs(col - col1) == 0) || (Math.abs(col - col1) > 1 && Math.abs(row - row1) == 0)) {
            if (row > row1) {
                for (int i = 1; row > row1 + i; i++)
                    if (fields[row1 + i][col1] != null) return false;
            } else if (row < row1) {
                for (int i = 1; row < row1 - i; i++)
                    if (fields[row1 - i][col1] != null) return false;
            } else if (col > col1) {
                for (int i = 1; col > col1 + i; i++)
                    if (fields[row1][col1 + i] != null) return false;
            } else if (col < col1) {
                for (int i = 1; col < col1 - i; i++)
                    if (fields[row1][col1 - i] != null) return false;
            }
        }
        return true;
    }

    public boolean move_figure(int row, int col, int row1, int col1){
        Figure figure = this.fields[row][col];

        if (figure == null) return false;
        else if ((figure.getTeam() == '*') != this.isStarsTurn) return false;
        else if(!clearPath(row, col, row1, col1)) return false;


        if (figure.canMove(row, col, row1, col1) && this.fields[row1][col1] == null){
            this.fields[row1][col1] = figure;
            this.fields[row][col] = null;
            return true;
        }else if (figure.canAttack(row, col, row1, col1) && this.fields[row1][col1] != null
                && this.fields[row1][col1].getTeam() != this.fields[row][col].getTeam()){
            if(this.isStarsTurn) this.takeDark.add("#" + this.fields[row1][col1].getName());
            else this.takeStar.add("*" + this.fields[row1][col1].getName());

            this.fields[row1][col1] = figure;
            this.fields[row][col] = null;
            return true;
        }
        return false;
    }
}
