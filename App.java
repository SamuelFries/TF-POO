import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class App {
    private int nroTentativa;
    private JFrame frame;
    private Tentativas tentativas;
    private List<Cor> combinacaoSecreta;

    public App(){
        nroTentativa = 0;
        combinacaoSecreta = gerarCombinacaoSecreta();
    }

    private List<Cor> gerarCombinacaoSecreta() {
        List<String> nomesCores = new ArrayList<>(Cores.getInstance().getNomesCores());
        Collections.shuffle(nomesCores);
        List<Cor> combinacao = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            combinacao.add(Cores.getInstance().getCor(nomesCores.get(i)));
        }
        return combinacao;
    }

    private void verificarTentativa() {
        UmaTentativa tentativa = tentativas.getTentativaComDica(nroTentativa).getTentativa();
        List<Cor> tentativaCores = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tentativaCores.add(tentativa.getPino(i).getCor());
        }

        List<PinoPB> feedback = fornecerFeedback(tentativaCores);
        Dica dica = tentativas.getTentativaComDica(nroTentativa).getDica();
        for (int i = 0; i < 4; i++) {
            dica.getPino(i).setCor(feedback.get(i).getCor());
        }

        if (tentativaCores.equals(combinacaoSecreta)) {
            JOptionPane.showMessageDialog(frame, "Você ganhou!", "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    private List<PinoPB> fornecerFeedback(List<Cor> tentativaCores) {
        List<PinoPB> feedback = new ArrayList<>();
        boolean[] combinacaoUsada = new boolean[4];
        boolean[] tentativaUsada = new boolean[4];

        // Verifica pinos pretos (cor e posição corretas)
        for (int i = 0; i < 4; i++) {
            if (tentativaCores.get(i).equals(combinacaoSecreta.get(i))) {
                feedback.add(PinoPB.criaPinoPreto());
                combinacaoUsada[i] = true;
                tentativaUsada[i] = true;
            }
        }

        // Verifica pinos cinza (cor correta, posição errada)
        for (int i = 0; i < 4; i++) {
            if (!tentativaUsada[i]) {
                for (int j = 0; j < 4; j++) {
                    if (!combinacaoUsada[j] && tentativaCores.get(i).equals(combinacaoSecreta.get(j))) {
                        feedback.add(PinoPB.criaPinoCinza());
                        combinacaoUsada[j] = true;
                        break;
                    }
                }
            }
        }

        // Completa com pinos vazios
        while (feedback.size() < 4) {
            feedback.add(PinoPB.criaPinoVazio());
        }

        return feedback;
    }

    public void verifica(){
        if (nroTentativa >= Tentativas.NROTEN-1){
            JOptionPane.showMessageDialog(frame, "Você perdeu", "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        verificarTentativa();

        tentativas.getTentativaComDica(nroTentativa).getTentativa().desabilita();
        nroTentativa++;
        tentativas.getTentativaComDica(nroTentativa).getTentativa().habilita();
    }

    public void criaJanelaPrincipal() {
        frame = new JFrame("Mini Senha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lbConfig = new JLabel("Controles");
        JButton btVerificar = new JButton("Verificar");
        btVerificar.addActionListener(e -> verifica());
        JButton btSair = new JButton("Sair");
        btSair.addActionListener(e -> System.exit(0));
        JPanel pnCtrl = new JPanel();
        pnCtrl.setLayout(new BoxLayout(pnCtrl, BoxLayout.PAGE_AXIS));
        pnCtrl.add(lbConfig);
        pnCtrl.add(btVerificar);
        pnCtrl.add(btSair);

        tentativas = new Tentativas();

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(pnCtrl, BorderLayout.WEST);
        contentPane.add(tentativas.getPainel(), BorderLayout.CENTER);

        // Libera a primeira linha de tentativa
        tentativas.getTentativaComDica(nroTentativa).getTentativa().habilita();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                App app = new App();
                app.criaJanelaPrincipal();
            }
        });
    }
}
