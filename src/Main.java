import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    static int row = 0, col = 0, row1 = 0, col1 = 0;

    static boolean turnCheck(){
        System.out.print("Введите ход: ");
        String inputLine = in.nextLine();
        System.out.print("\n \n \n");
        if (Objects.equals(inputLine, "A9 A9")) return false;
        if (inputLine.length() < 5){
            System.out.println("Невернный ввод");
            return true;
        }

        char[] coords = inputLine.toCharArray();
        col = coords[0] - 'A';
        row = coords[1] - '1';
        col1 = coords[3] - 'A';
        row1 = coords[4] - '1';

        if (row > 7 || col > 7 || row1 > 7 || col1 > 7 || row < 0 || col < 0 || row1 < 0 || col1 < 0){
            row = 0; col = 0; row1 = 0; col1 = 0;
            System.out.println("Невернный ввод");
        }
        return true;
    }

    public static void main(String[] args) {

        Board board = new Board();
        board.init();
        board.setStarsTurn(true);

        System.out.println();

        boolean isGame = true;


        while (isGame){
            board.print_board();
            System.out.println();

            System.out.println("Управление: ");
            System.out.println("Введите в строку клетку начала и клетку конца хода фигуры, пример B2 B4");
            System.out.println("Чтобы сдаться введите A9 A9");
            System.out.println("Взятые звезды: " + board.getTakeStar().toString() );
            System.out.println("Взятые тени: " + board.getTakeDark().toString() + "\n");

            if (board.getStarsTurn()) System.out.println("Ход звезд");
            else System.out.println("Ход теней");

            if(!turnCheck()){
                if (board.getStarsTurn()) System.out.println("Звезды сдались");
                else System.out.println("Тени сдались");
                return;
            }

            while (!board.move_figure(row, col, row1, col1)){
                System.out.println("Ошибка! Повторите ход фигуры!");
                if(!turnCheck()){
                    if (board.getStarsTurn()) System.out.println("Звезды сдались");
                    else System.out.println("Тени сдались");
                    return;
                }
            }


            if(board.isCheck('*') && isGame) System.out.println("ШАХ ЗВЕЗДАМ!");
            if(board.isCheck('#') && isGame) System.out.println("ШАХ ТЕНЯМ!");

            if(board.isMate()) {
                if(board.getStarsTurn()) System.out.println("МАТ ЗВЕЗДАМ!");
                else System.out.println("МАТ ТЕНЯМ");
                System.out.println("Игра окончена");
                isGame = false;
            }

            board.passTurn();
        }
    }
}