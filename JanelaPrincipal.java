import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JanelaPrincipal {
    private JFrame frame;
    private Tentativas tentativas;
    private List<Cor> combinacaoSecreta;
    private int nroTentativa;
    private boolean modoTeste;


    public JanelaPrincipal(App app) {
        this.modoTeste = false;
        this.nroTentativa = 0;
        this.combinacaoSecreta = app.gerarCombinacaoSecreta();
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

    public void verifica() {
        if (nroTentativa >= Tentativas.NROTEN - 1) {
            JOptionPane.showMessageDialog(frame, "Você perdeu", "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        verificarTentativa();

        tentativas.getTentativaComDica(nroTentativa).getTentativa().desabilita();
        nroTentativa++;
        tentativas.getTentativaComDica(nroTentativa).getTentativa().habilita();
    }

    public void criarTelaInicial() {
        JFrame telaInicial = new JFrame("Tela Inicial");
        telaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaInicial.setSize(300, 200);

        JPanel painelInicial = new JPanel(new GridLayout(2, 1));

        JButton btnTeste = new JButton("Teste");
        btnTeste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                telaInicial.dispose(); // Fecha a tela inicial
                modoTeste = true;
                criarJanelaPrincipal(); // Inicia a janela principal no modo teste
            }
        });

        JButton btnJogar = new JButton("Jogar");
        btnJogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                telaInicial.dispose(); // Fecha a tela inicial
                modoTeste = false;
                criarJanelaPrincipal(); // Inicia a janela principal no modo normal
            }
        });

        painelInicial.add(btnTeste);
        painelInicial.add(btnJogar);

        telaInicial.add(painelInicial);
        telaInicial.setLocationRelativeTo(null); // Centraliza a tela inicial
        telaInicial.setVisible(true);
    }

    public void criarJanelaPrincipal() {
        frame = new JFrame("Mini Senha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btVerificar = new JButton("Verificar");
        btVerificar.addActionListener(e -> verifica());
        JButton btVoltar = new JButton("Voltar");
        btVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Fecha a janela principal
                criarTelaInicial(); // Volta para a tela inicial
            }
        });
        JPanel pnCtrl = new JPanel();
        pnCtrl.setLayout(new BorderLayout());

        tentativas = new Tentativas();

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(pnCtrl, BorderLayout.WEST);
        contentPane.add(tentativas.getPainel(), BorderLayout.CENTER);

        if (modoTeste) {
            contentPane.add(criarPainelCombinacaoSecreta(), BorderLayout.NORTH);
        }

        
        pnCtrl.add(btVoltar, BorderLayout.NORTH);

        
        JPanel painelVerificar = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelVerificar.add(btVerificar, gbc);

        contentPane.add(painelVerificar, BorderLayout.SOUTH);

        // Libera a primeira linha de tentativa
        tentativas.getTentativaComDica(nroTentativa).getTentativa().habilita();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel criarPainelCombinacaoSecreta() {
        JPanel painelCombinacaoSecreta = new JPanel();
        painelCombinacaoSecreta.setLayout(new FlowLayout(FlowLayout.CENTER));

        for (Cor cor : combinacaoSecreta) {
            JLabel labelCor = new JLabel();
            labelCor.setOpaque(true);
            labelCor.setBackground(cor.getCor());
            labelCor.setPreferredSize(new Dimension(50, 50));
            painelCombinacaoSecreta.add(labelCor);
        }

        return painelCombinacaoSecreta;
    }
}
