import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.applet.*;
import java.io.File;
import java.util.concurrent.TimeUnit;


public class Sound {
   private static Sound sound;

    private Sound() {

    }

    public static Sound getInstance() {
        if (sound == null) sound = new Sound();
        return sound;
    }

    public void playReachedEnd() {

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(".\\audio\\Line Removed.wav")));
            clip.start();
//            TimeUnit.SECONDS.sleep(1);
//            clip.close();
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public void playGameOver() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(".\\audio\\Game Over.wav")));
            clip.start();
//            TimeUnit.SECONDS.sleep(1);
//            clip.close();
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
