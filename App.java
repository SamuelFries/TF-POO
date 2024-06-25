import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    private JanelaPrincipal janelaPrincipal;

    public App() {
        janelaPrincipal = new JanelaPrincipal(this);
    }

    public List<Cor> gerarCombinacaoSecreta() {
        List<String> nomesCores = new ArrayList<>(Cores.getInstance().getNomesCores());
        Collections.shuffle(nomesCores);
        List<Cor> combinacao = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            combinacao.add(Cores.getInstance().getCor(nomesCores.get(i)));
        }
        return combinacao;
    }

    public void iniciar() {
        janelaPrincipal.criarTelaInicial();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.iniciar();
        });
    }
}
