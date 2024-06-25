import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Som {
    
    private static final String CAMINHO_PADRAO_SOM = "boop.wav";

    public static void tocarSom() {
        try {
            File arquivoDeSom = new File(CAMINHO_PADRAO_SOM); 
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoDeSom);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
}
