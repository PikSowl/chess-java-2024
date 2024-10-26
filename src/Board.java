import Figures.Figure;
import Figures.Pawn;

public class Board {

    private char colorGame;

    public void setColorGame(char colorGame) {
        this.colorGame = colorGame;
    }

    public  char getColorGame(){
        return colorGame;
    }


    private Figure fields[][] = new Figure[8][8];


    public void init(){
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),
                new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),
        };
        this.fields[6] = new Figure[] {
                new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),
                new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b')
        };
    }

    public String getCell(int row, int col){
        Figure figure = this.fields[row][col];
        if (figure ==null){
            return "    ";
        }
        return  " "+figure.getColor()+figure.getName()+" ";
    }
    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1 ; row --){
            System.out.print(row);
            for (int col=0; col<8; col++){
                System.out.print("|"+getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col=0; col< 8; col++){
            System.out.print("    "+col);
        }
    }

    public boolean move_figure(int row, int col, int row1, int col1){
      Figure figure = this.fields[row][col];
      if (figure != null && figure.canMove(row, col, row1, col1) && this.fields[row1][col1] == null && figure.getColor() == this.colorGame){
          this.fields[row1][col1] = figure;
          this.fields[row][col] = null;
          return true;
      }else  if (figure.canAttack(row, col, row1, col1) && this.fields[row1][col1] != null && this.fields[row1][col1].getColor() != this.fields[row][col].getColor()){
          this.fields[row1][col1] = figure;
          this.fields[row][col] = null;
          return true;
      }
        return false;
    }
}
