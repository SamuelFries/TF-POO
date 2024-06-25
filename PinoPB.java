import java.awt.Color;
import java.awt.event.ActionEvent;

public class PinoPB extends Pino {
    private PinoPB(Cor cor){
        super(cor);
    }

    public static PinoPB criaPinoPreto(){
        return new PinoPB(new Cor("BLACK", Color.BLACK));
    }

    public static PinoPB criaPinoBranco(){
        return new PinoPB(new Cor("WHITE", Color.WHITE));
    }

    public static PinoPB criaPinoCinza(){
        return new PinoPB(new Cor("GRAY", Color.GRAY));
    }

    public static PinoPB criaPinoVazio(){
        return new PinoPB(new Cor("EMPTY", Color.WHITE));
    }

    public void setCor(Cor cor){
        if (cor.getNomeCor().equals("BLACK") ||
            cor.getNomeCor().equals("WHITE") ||
            cor.getNomeCor().equals("GRAY") ||
            cor.getNomeCor().equals("EMPTY")) {
            super.setCor(cor);    
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void acaoDoBotao(ActionEvent e) {
        
    }
}
