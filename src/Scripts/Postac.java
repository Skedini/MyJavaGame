package Scripts;

import javafx.scene.control.TextArea;

public class Postac {
    String imie;
    int zycie;
    int atak;
    int maxHP;
    int baseHP = maxHP;
    int baseAtk = atak;

    public Postac(String imie, int zycie, int atak, int maxHP) {
        this.imie = imie;
        this.zycie = zycie;
        this.atak = atak;
        this.maxHP = maxHP;

        this.baseHP = maxHP;
        this.baseAtk = atak;
    }



    public void zaatakuj(Postac cel, TextArea log) {
        log.appendText(imie + " atakuje " + cel.imie + " za " + atak + " obrażeń!\n");
        cel.zycie -= atak;
        if (cel.zycie < 0) cel.zycie = 0;
    }

    public boolean czyZyje() {
        return zycie > 0;
    }

    public void updateStats(Przedmiot armour, Przedmiot weapon, Przedmiot helmet, Przedmiot shield){
        int armourdef = armour != null ? armour.getstats() : 0;
        int weaponatk = weapon != null ? weapon.getstats() : 0;
        int helmetdef = helmet != null ? helmet.getstats() : 0;
        int shielddef = shield != null ? shield.getstats() : 0;

        maxHP = baseHP + armourdef + helmetdef + shielddef;
        atak = baseAtk + weaponatk;

        if(zycie > maxHP){
            zycie = maxHP;
        }
        if (weapon == null) {
            atak = baseAtk;
        }
    }

}
