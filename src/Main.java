import com.github.collinalpert.java2db.database.DBConnection;
import view.Game;

public class Main {

    public static void main(String[] args) {
        new Game().initNewGame();

        DBConnection.HOST = "localhost";
        DBConnection.PORT = 3306;
        DBConnection.DATABASE = "hangman";
        DBConnection.USERNAME = "luca";
        DBConnection.PASSWORD = "192837";
    }
}
