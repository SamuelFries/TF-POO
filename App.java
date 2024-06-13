import java.awt.Color;
import java.awt.Container;
//import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.GridLayout; 
import java.awt.Dimension;

public class App {
    public void criaJanelaPrincipal() {
        JFrame frame = new JFrame("MINI SENHA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(4, 2, 20, 20));
        //contentPane.setLayout(new FlowLayout());

        Pino[] pinos = new Pino[6];
        pinos[0] = PinoPB.criaPinoBranco();
        pinos[1] = PinoPB.criaPinoPreto();
        pinos[2] = PinoColorido.criaPinoColorido("BLUE");
        pinos[3] = PinoColorido.criaPinoColorido("RED");
        pinos[4] = PinoColorido.criaPinoColorido("YELLOW");
        pinos[5] = PinoColorido.criaPinoColorido("GREEN");
        for(Pino p:pinos){
            contentPane.add(p);
        }

        frame.setSize(400, 900); 
        frame.setMinimumSize(new Dimension(400, 300));

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