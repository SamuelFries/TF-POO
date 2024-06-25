import javax.swing.JPanel;
import java.awt.FlowLayout;

public class UmaTentativa {
    private PinoColorido[] pinos;
    private JPanel pnTentativa;

    public UmaTentativa() {
        pinos = new PinoColorido[4];
        pnTentativa = new JPanel();

        // Cria os pinos
        for (int i = 0; i < 4; i++) {
            // Modifique para criar pinos coloridos sem passar uma string de cor
            pinos[i] = new PinoColorido(Cores.getInstance().getCor("GRAY"));
            pinos[i].setEnabled(false);
        }

        // Monta o painel
        pnTentativa.setLayout(new FlowLayout(FlowLayout.CENTER));
        for (PinoColorido p : pinos) {
            pnTentativa.add(p);
        }
    }

    public void habilita() {
        for (PinoColorido p : pinos) {
            p.setEnabled(true);
        }
    }

    public void desabilita() {
        for (PinoColorido p : pinos) {
            p.setEnabled(false);
        }
    }

    public PinoColorido getPino(int i) {
        return pinos[i];
    }

    public JPanel getPainel() {
        return pnTentativa;
    }
}
