package apppacman;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*** @author IrvinACG
 * @version 1.0.0
 * https://github.com/IrvinACG
 */
public class Sonido {
    public static final String PACMAN="soundPacman.wav";
    public static final String PACMAN_2="soundPacman2.wav";
    public static final String VICTORIA="soundWin.wav";
    public static final String INICIO="soundStar.wav";
    private static Clip sonido;
    private static File file;

    public Sonido() {
    }
    public static void sonar(String str) {
        try {
            sonido = AudioSystem.getClip();
            file = new File("src/sonidos/" + str);
            sonido.open(AudioSystem.getAudioInputStream(file));
            sonido.start();
        } catch (Exception e) {

        }
    }
}
