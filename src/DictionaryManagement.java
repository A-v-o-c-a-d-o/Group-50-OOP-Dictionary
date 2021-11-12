import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import edu.princeton.cs.algs4.In;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();
    Scanner sc = new Scanner(System.in);

    public void insertFromCommandline() {
        System.out.print("Number of word to insert: ");
        int n = sc.nextInt();   sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Target word: ");
            String wordTarget = sc.nextLine().toLowerCase().trim();
            System.out.print("Explain word: ");
            String wordExplain = sc.nextLine().toLowerCase().trim();
            System.out.print("Phonetics word: ");
            String wordPhonetics = sc.nextLine().toLowerCase().trim();

            dictionary.addWord(new Word(wordTarget, wordExplain, wordPhonetics));
        }
    }

    public void insertFromFile() {
        In in = new In(new File("D:\\Study\\Dictionary\\src\\dictionaries.txt"));
        while (!in.isEmpty()) {
            String wordTarget = in.readString().toLowerCase().trim();
            String wordExplain = in.readLine().toLowerCase().trim();
            String wordPhonetics = in.readLine().toLowerCase().trim();
            dictionary.addWord(new Word(wordTarget, wordExplain, wordPhonetics));
        }
    }

    public static void UpdateDatabase(Word word) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./lib/dict_hh.db");
        Statement statement = connection.createStatement();
        String sql = "UPDATE av set description = '" + word.getWord_explain() + "' where word = '" + word.getWord_target() + "'";
        statement.executeQuery(sql);
        connection.close();
        statement.close();
    }

    public static void deleteInDatabase(Word word) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./lib/dict_hh.db");
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM av where word = '" + word.getWord_target() + "'";
        statement.executeQuery(sql);
        connection.close();
        statement.close();
    }

    public static void insertToDatabase(Word word) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./lib/dict_hh.db");
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO av (word, pronounce, description) values ('" +
                    word.getWord_target() + "', '" + word.getWord_phonetics() + "', '" + word.getWord_explain() + "')";
        statement.executeQuery(sql);
        connection.close();
        statement.close();
    }

    public static ObservableList<Word> searchFromDatabase(String wordToFind) throws SQLException {
        ObservableList<Word> ans = FXCollections.observableArrayList();
        
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./lib/dict_hh.db");
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM av WHERE word like " + "'" + wordToFind + "%'" + " ORDER BY word";
        ResultSet a = statement.executeQuery(sql);
        
        while(a.next()) {
            ans.add(new Word(a.getString(2), parseHTMLContent(a.getString(3)), a.getString(5)));
        }
        connection.close();
        statement.close();

        return ans;
    }

    private static String parseHTMLContent(String toString) {
        String result = toString.replaceAll("\\<.*?\\>", "\n");
        String previousResult = "";
        while(!previousResult.equals(result)){
            previousResult = result;
            result = result.replaceAll("\n\n","\n");
        }
        return result;
    }

    public void removeFromCommandLine() {
        System.out.print("Number of word to remove: ");
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.print("Word to remove: ");
            String wordTarget = sc.nextLine().toLowerCase().trim();
            String wordExplain = sc.nextLine().toLowerCase().trim();
            String wordPhonetics = sc.nextLine().toLowerCase().trim();
            dictionary.removeWord(new Word(wordTarget, wordExplain, wordPhonetics));
        }
    }

    public void dictionaryLookup() {
        System.out.print("Enter exactly the word to find: ");
        String wordToFind = sc.next().toLowerCase().trim();
        for (Word i: dictionary)
            if (i.getWord_target().equals(wordToFind)) {
                System.out.println("Explain word: " + i.getWord_explain());
                return;
            }
        System.out.println("We can not find your word!");
    }

    public void dictionaryExportToFile() throws IOException {
        FileWriter writer = new FileWriter(new File("D:\\Study\\Dictionary\\src\\outPut.txt"));
        for (Word i: dictionary) {
            writer.write(i.getWord_target() + "\t" + i.getWord_explain() + "\n");
        }
        writer.close();
    }

    public static void speak(Word word) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin");
        voice.allocate();
        voice.speak(word.getWord_target());
    }
}