import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.applet.*;
import java.io.File;
import java.util.concurrent.TimeUnit;


public class Sound {
    private File lineRemoved, gameOver;
    private static Sound sound;
    //    private Clip clip;
    private AudioInputStream lineRemovedStream, gameOverStream;

    private Sound() {
        lineRemoved = new File(".\\audio\\Line Removed.wav");
        gameOver = new File(".\\audio\\Game Over.wav");
        try {
//            clip = AudioSystem.getClip();
            lineRemovedStream = AudioSystem.getAudioInputStream(lineRemoved);
            gameOverStream = AudioSystem.getAudioInputStream(gameOver);
//            clip.open(lineRemovedStream);
        } catch (Exception e) {
            System.out.println("can't find file");
        }
    }

    public static Sound getInstance() {
        if (sound == null) sound = new Sound();
        return sound;
    }

    public void playReachedEnd() {

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(lineRemovedStream);
            clip.start();
            TimeUnit.SECONDS.sleep(1);
            clip.close();
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public void playGameOver() {
//        try {
//            clip.open(gameOverStream);
//            clip.start();
//        }catch(Exception e){
//
//        }
    }
}
