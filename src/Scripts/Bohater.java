package Scripts;

import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Bohater extends Postac {
    int lReady = 0;
    private int zloto;
    public int leczenie = 50;
    public ArrayList<Przedmiot> Ekwipunek = new  ArrayList<>();
    public Bohater(String imie, int zloto) {
        super(imie, 100, 20, 100);
        this.zloto  = zloto;
    }
    public void leczenie(TextArea log) {
        if (lReady <= 0) {
            zycie += leczenie;
            if (zycie > 100) zycie = maxHP;
            log.appendText(imie + " uleczył się o " + leczenie + " punktów życia!\n");
        }
    }


    @Override
    public void zaatakuj(Postac cel, TextArea log) {
        super.zaatakuj(cel, log);
        lReady--;
    }

    public void dodajLoot(int loot){
        zloto += loot;
    }

    public void goldUpdate(Label label){
        label.setText("Złoto: " + zloto);
    }

    public void resetLeczenia() {
        lReady = 0;
    }

}
