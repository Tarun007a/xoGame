
import java.util.*;

public class AiXo {
    static Scanner sc = new Scanner(System.in);
    static char human = 'X';
    static char ai = 'O';
    static int humanWin = 0;
    static int aiWin = 0;
    static char[][] arr = new char[3][3];
    public static void resetGrid(){
        for(int i = 0; i < 3; i++) Arrays.fill(arr[i],' ');
    }
    public static void printGrid(){
        for(int i = 0; i < 3; i++){
            boolean flag = true;
            int idx = 0;
            for(int j = 0; j < 5; j++){
                if(flag){
                    System.out.print(arr[i][idx++]);
                }
                else System.out.print("|");
                flag = !flag;
            }
            System.out.println();
            if(i != 2){
                System.out.println("-+-+-");
            }
        }
    }
    public static void humanTurn(){
        System.out.println("This is your turn");
        System.out.print("Enter row number : ");
        int row = sc.nextInt();
        System.out.print("Enter column number : ");
        int column = sc.nextInt();
        if(row > 2 || column > 2 || arr[row][column] != ' '){
            System.out.println("Invalid move");
            humanTurn();
        }
        else{
            arr[row][column] = human;
        }
    }
    public static void aiTurn(){
        int[] moves = bestMove();
        arr[moves[0]][moves[1]] = ai;
    }

    public static int[] bestMove() {
        int[] ans = new int[2];
        int bestScore = Integer.MIN_VALUE;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(arr[i][j] == ' '){
                    arr[i][j] = ai;
                    int res = miniMax(false);
                    arr[i][j] = ' ';
                    if(res > bestScore){
                        bestScore = res;
                        ans[0]=i;
                        ans[1]=j;
                    }
                }
            }
        }
        return ans;
    }
    public static int miniMax(boolean isMaximizing){
        int res = checkWinner();
        if(res != 2)return res;
        if(isMaximizing){
            int bestScore = Integer.MIN_VALUE;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(arr[i][j] == ' ') {
                        arr[i][j] = ai;
                        int score = miniMax(false);
                        arr[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
        else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (arr[i][j] == ' ') {
                        arr[i][j] = human;
                        int score = miniMax(true);
                        arr[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public static boolean checkHelper(char ch){
        if(arr[0][0] == ch && arr[1][1] == ch && arr[2][2] == ch) return true;
        if(arr[0][2] == ch && arr[1][1] == ch && arr[2][0] == ch) return true;
        for(int i = 0; i < 3; i++){
            int numr = 0 ,numc = 0;
            for(int j = 0; j < 3; j++){
                if(arr[i][j] == ch)numr++;
                if(arr[j][i] == ch)numc++;
            }
            if(numr == 3 || numc == 3)return true;
        }
        return false;
    }
    public static boolean isFull(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                if (arr[i][j] == ' ') return false;
            }
        }
        return true;
    }
    public static int checkWinner() {
        if(checkHelper(ai)) return 1;
        if(checkHelper(human)) return -1;
        if(isFull())return 0;
        return 2;//nothing continue game
    }
    public static void play() {
        resetGrid();
        printGrid();
        for (int i = 1; i <= 5; i++) {
            humanTurn();
            printGrid();
            int check = checkWinner();
            if (check == -1){
                System.out.println("YOU WON!");
                humanWin++;
                return;
            } else if (check == 0) {//the last turn will always go to human so tie only after human's turn.
                System.out.println("TIE!");
                return;
            }
            aiTurn();
            printGrid();
            check = checkWinner();
            if (check == 1) {
                System.out.println("YOU LOST!");
                aiWin++;
                return;
            }
        }
    }
    public static void main(String[] args){
        int ans;
        do{
            play();
            System.out.println("X have won : " + humanWin);
            System.out.println("Y have won : "  + aiWin);
            System.out.print("If you want do play again enter 1 or for exit enter 2 : ");
            ans = sc.nextInt();
            resetGrid();
        }while (ans != 2);
    }
}
