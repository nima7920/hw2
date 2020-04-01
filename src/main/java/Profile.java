import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Profile implements ReStartable {
    private static Profile profile;
    private long score = 0, removedLines = 0;
    private ArrayList<Long> Scores;
    private File gameScores;

    private Profile() {
//        ScoreBoard.getInstance().setScores(new String[] {"1", "2", "0", "0", "2", "2", "2", "2", "2","0"});
        score=0;
        removedLines=0;
        Scores = new ArrayList<>(10);
        loadScores(); //

    }

    public static Profile getInstance() {
        if (profile == null) profile = new Profile();
        return profile;
    }

    public void saveScores() { // save the scores
        Scores.add(score);
        updateScoresFile();
        updateScoresTable();
    }

    private void loadScores() {
        gameScores = new File(".//profiles//Scores.txt");
        if (gameScores.exists()) {
            loadScoresFile();
        } else {
            createScoresFile();
        }
        updateScoresTable();

    }

    public void updateScoresTable() {
        Collections.sort(Scores);
        Collections.reverse(Scores);
        ScoreBoard.getInstance().setScores(Scores);
    }

    private void createScoresFile() {
        try {
            gameScores.createNewFile();
            FileWriter writer = new FileWriter(gameScores);
            JSONArray arr = new JSONArray();
            for (int i = 0; i < 10; i++) {
                arr.add(0l);
                Scores.add(0l);
            }
            writer.write(arr.toJSONString());
            writer.flush();
        } catch (IOException e) {

        }
    }

    private void loadScoresFile() {
        try {
            FileReader reader = new FileReader(gameScores);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONArray arr = (JSONArray) obj;
            Scores = arr;

        } catch (IOException | ParseException e) {

        }
    }

    private void updateScoresFile() {
        try {
            FileWriter writer = new FileWriter(gameScores);
            JSONArray arr = (JSONArray) Scores.clone();
            writer.write(arr.toJSONString());
            writer.flush();
        } catch (IOException e) {

        }
    }

    public void addScore(int score) {
        this.score += score;
        Score.getInstance().updateScore(this.score);
    }

    public void addRemovedLine() {
        removedLines++;
        HintBoard.getInstance().setRemovedRows(removedLines);
    }

    public long getScore() {
        return score;
    }

    @Override
    public void reStart() {
        score=0;
        removedLines=0;
    }
}
