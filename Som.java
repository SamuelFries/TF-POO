import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Som {
    // Defina o caminho do arquivo de som aqui
    private static final String CAMINHO_PADRAO_SOM = "boop.wav";

    public static void tocarSom() {
        try {
            File arquivoDeSom = new File(CAMINHO_PADRAO_SOM); // Use a constante definida acima
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoDeSom);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
}
