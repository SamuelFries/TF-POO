import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

public class PinoColorido extends Pino {
    private static final int TAMANHO_PINO = 30;

    public PinoColorido(Cor cor) {
        super(cor);
        setPreferredSize(new Dimension(TAMANHO_PINO, TAMANHO_PINO));
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getCor().getCor());
        int diameter = Math.min(getWidth(), getHeight());
        g2d.fillOval((getWidth() - diameter) / 2, (getHeight() - diameter) / 2, diameter, diameter);
        g2d.dispose();
    }

    @Override
    public void setCor(Cor cor) {
        super.setCor(cor);
        repaint();
    }

    @Override
    public void acaoDoBotao(ActionEvent e) {
        Cor novaCor = Cores.getInstance().proximaCor(getCor().getNomeCor());
        setCor(novaCor);
        Som.tocarSom();
    }

    public static PinoColorido criaPinoColorido(Cor cor) {
        return new PinoColorido(cor);
    }
}
