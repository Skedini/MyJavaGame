package Scripts;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

import java.util.Optional;
import java.util.Random;


public class GraGUI {
    @FXML private TabPane tabPane;
    @FXML private VBox equipmentPane;
    @FXML private TextField ustawienianazwagracza;
    @FXML private Label imieGracza;
    @FXML private Label imiePotwora;
    @FXML private ProgressBar hpBarGracza;
    @FXML private ProgressBar hpBarPotwora;
    @FXML private TextArea log;
    @FXML private Button atakBtn;
    @FXML private Button leczBtn;
    @FXML private Button nowyBtn;
    @FXML private Label gold;
    @FXML private StackPane graczimg;
    @FXML private ImageView potworimg;
    @FXML private Pane Gra;
    @FXML private Button zapiszzmianybtn;
    @FXML private Button anulujzmianybtn;
    @FXML private ImageView top;
    @FXML private ImageView middle;
    @FXML private ImageView bottom;
    @FXML private ImageView skin;
    @FXML private ImageView weapon;
    @FXML private ImageView helmet;
    @FXML private ImageView pants;
    @FXML private ImageView shield;
    @FXML private ImageView gshield;
    @FXML private ImageView gpants;
    @FXML private ImageView gtop;
    @FXML private ImageView gmiddle;
    @FXML private ImageView gbottom;
    @FXML private ImageView gskin;
    @FXML private ImageView gweapon;
    @FXML private ImageView ghelmet;

    @FXML private Button ps;
    @FXML private Button pt;
    @FXML private Button pm;
    @FXML private Button pp;
    @FXML private Button pb;
    @FXML private Button ns;
    @FXML private Button nt;
    @FXML private Button nm;
    @FXML private Button np;
    @FXML private Button nb;


    private Bohater gracz;
    private Potwor potwor;
    private Przedmiot equipedWeapon;
    private Przedmiot equipedArmour;
    private Przedmiot equipedHelmet;
    private Przedmiot equipedShield;

    private String topint = "1";
    private String middleint = "1";
    private String bottomint = "1";
    private String skinint = "1";
    private String pantsint = "1";

    //-------------UI------------------//
    Image casualCursorImg = new Image(getClass().getResource("/assets/crosshairs/casual.png").toExternalForm());
    Cursor casualCursor = new ImageCursor(casualCursorImg,0 ,0);

    Image healCursorImg = new Image( getClass().getResource("/assets/crosshairs/leczenie.png").toExternalForm());
    Cursor healCursor = new ImageCursor(healCursorImg,0 ,0);

    Image attackCursorImg = new Image(getClass().getResource("/assets/crosshairs/atak.png").toExternalForm());
    Cursor attackCursor = new ImageCursor(attackCursorImg,0 ,0);


    @FXML
    public void initialize() {
        tabPane.setCursor(casualCursor);
        graczimg.setCursor(healCursor);
        potworimg.setCursor(attackCursor);
        //-----------Gra--------------------//
        Image bohater = new Image(getClass().getResource("/assets/bohatergraint.png").toExternalForm());
        String imie = AquirePlayerName();
        gracz = new Bohater(imie, 0);
        potwor = Potwor.losowyPotwor();
        imieGracza.setText(imie);
        aktualizujimgpotwora();
        refreshskin();
        equipmentRefresh();


        log.appendText("Witaj w grze, " + gracz.imie + "!\n");
        log.appendText("Twój przeciwnik to: " + potwor.klasa + " " + potwor.imie + "\n");
        aktualizujHPBohatera();
        aktualizujHPPotwora();
        imiePotwora.setText(potwor.klasa + " " +potwor.imie);

        ustawienianazwagracza.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
            }
        });

        ustawienianazwagracza.setText(gracz.imie);

        zapiszzmianybtn.setOnAction(event -> {
            gracz.imie = ustawienianazwagracza.getText();
            imieGracza.setText(gracz.imie);
        });

        anulujzmianybtn.setOnAction(event -> {
           ustawienianazwagracza.setText(gracz.imie);
        });



        ustawienianazwagracza.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 20) {
                ustawienianazwagracza.setText(oldText);
            }
        });


        graczimg.setOnMouseClicked(e -> {
            leczBtn.fire();
        });

        potworimg.setOnMouseClicked(e -> {
            atakBtn.fire();
        });

        atakBtn.setOnAction(e -> {
            graczimg.setCursor(casualCursor);
            potworimg.setCursor(casualCursor);
            if (!gracz.czyZyje() || !potwor.czyZyje()) return;
            gracz.zaatakuj(potwor, log);
            atakanim(potworimg, gracz.atak, potwor.atak);

            if (potwor.czyZyje()) {
                    potwor.zaatakuj(gracz, log);
            } else {
                log.appendText("🎉 Pokonałeś potwora!\n");
                int loot = 1 + new Random().nextInt(99);
                log.appendText("Zyslałeś " + loot + " złota!\n");
                gracz.dodajLoot(loot);
                gracz.goldUpdate(gold);
            }
        });

        leczBtn.setOnAction(e -> {
            graczimg.setCursor(casualCursor);
            potworimg.setCursor(casualCursor);
            atakBtn.setDisable(true);
            if (!gracz.czyZyje() || !potwor.czyZyje()) return;
            if (gracz.lReady > 0){
                log.appendText("Nie możesz uleczyć sie w tej rundzie!\nPozostała liczba rund do uleczenia: " + gracz.lReady + "\n");
                leczBtn.setDisable(false);
                atakBtn.setDisable(false);
                potworimg.setCursor(attackCursor);
                graczimg.setCursor(healCursor);
                return;
            }
            leczanim(gracz.leczenie);
            gracz.leczenie(log);
            aktualizujHPBohatera();
            if (potwor.czyZyje()) {
                gracz.lReady = 2;
                potwor.zaatakuj(gracz, log);
            }
        });

        nowyBtn.setOnAction(e -> {
            if (!gracz.czyZyje()) {
                log.appendText("Nie żyjesz. Zacznij grę od nowa.\n");
                return;
            }
            if (potwor.czyZyje()){
                log.appendText("Najpierw pokonaj obecnego potwora! \n");
                return;
            }
            atakBtn.setDisable(true);
            leczBtn.setDisable(true);
            potwor = Potwor.losowyPotwor();
            gracz.resetLeczenia();
            log.appendText("|================| \n😈 Nowy potwór: " + potwor.klasa + "\n");
            nowystageanim();
            aktualizujimgpotwora();
        });

        //---------------------ekwipunek----------------------//

        ns.setOnAction(e -> {
            if (skinint.equals("8")) {
                skinint = "1";
                refreshskin();
            }
            else {
                int num = Integer.parseInt(skinint);
                num++;
                skinint = Integer.toString(num);
                refreshskin();
            }

        });

        nt.setOnAction(e -> {
            if (topint.equals("84")) {
                topint = "1";
                refreshskin();
            }
            else {
                int num = Integer.parseInt(topint);
                num++;
                topint = Integer.toString(num);
                refreshskin();
            }

        });

        nm.setOnAction(e -> {
            isArmourEquipped();
            if (equipedArmour == null){
            if (middleint.equals("108")) {
                middleint = "1";
                refreshskin();
            }
            else {
                int num = Integer.parseInt(middleint);
                num++;
                middleint = Integer.toString(num);
                refreshskin();
            }
            }

        });

        np.setOnAction(e -> {
            if (pantsint.equals("8")) {
                pantsint = "1";
                refreshskin();
            }
            else {
                int num = Integer.parseInt(pantsint);
                num++;
                pantsint = Integer.toString(num);
                refreshskin();
            }

        });

        nb.setOnAction(e -> {
            if (bottomint.equals("12")) {
                bottomint = "1";
                refreshskin();
            }
            else {
                int num = Integer.parseInt(bottomint);
                num++;
                bottomint = Integer.toString(num);
                refreshskin();
            }

        });

        ps.setOnAction(e -> {
            if (skinint.equals("1")) {
                skinint = "8";
                refreshskin();
            }
            else {
                int num = Integer.parseInt(skinint);
                num--;
                skinint = Integer.toString(num);
                refreshskin();
            }

        });

        pt.setOnAction(e -> {
            if (topint.equals("1")) {
                topint = "84";
                refreshskin();
            }
            else {
                int num = Integer.parseInt(topint);
                num--;
                topint = Integer.toString(num);
                refreshskin();
            }
        });

        pm.setOnAction(e -> {
            isArmourEquipped();
            if (equipedArmour == null) {
                if (middleint.equals("1")) {
                    middleint = "108";
                    refreshskin();
                }
                else {
                    int num = Integer.parseInt(middleint);
                    num--;
                    middleint = Integer.toString(num);
                    refreshskin();
                }
            }
        });

        pp.setOnAction(e -> {
            if (pantsint.equals("1")) {
                pantsint = "8";
                refreshskin();
            }
            else {
                int num = Integer.parseInt(pantsint);
                num--;
                pantsint = Integer.toString(num);
                refreshskin();
            }

        });

        pb.setOnAction(e -> {
            if (bottomint.equals("1")) {
                bottomint = "12";
                refreshskin();
            }
            else{
            int num = Integer.parseInt(bottomint);
            num--;
            bottomint = Integer.toString(num);
            refreshskin();}
        });
    }


//----------funkcje------------//




    private void aktualizujHPBohatera() {
        hpBarGracza.setProgress(((double) gracz.zycie / gracz.maxHP));

        if (!gracz.czyZyje()) {
            log.appendText("💀 Zginąłeś! Koniec gry.\n");
            atakBtn.setDisable(true);
            leczBtn.setDisable(true);
            potworimg.setCursor(casualCursor);
            graczimg.setCursor(casualCursor);
            Smiercgraczaanim();
        }
    }

    private void aktualizujHPPotwora(){
        hpBarPotwora.setProgress(((double) potwor.zycie / potwor.maxHP));
    }

    private void aktualizujimgpotwora(){
        switch (potwor.klasa) {
            case "Szkielet" -> {
                Image img = new Image(getClass().getResource("/assets/szkieletgraint.png").toExternalForm());
                potworimg.setImage(img);
            }
            case "Smok" -> {
                Image img = new Image(getClass().getResource("/assets/smokgraint.png").toExternalForm());
                potworimg.setImage(img);
            }
            case "Goblin" -> {
                Image img = new Image(getClass().getResource("/assets/goblingraint.png").toExternalForm());
                potworimg.setImage(img);
            }
            case "Wilkołak" -> {
                Image img = new Image(getClass().getResource("/assets/wilkołakgraint.png").toExternalForm());
                potworimg.setImage(img);
            }
            case "Olbrzym" -> {
                Image img = new Image(getClass().getResource("/assets/olbrzymgraint.png").toExternalForm());
                potworimg.setImage(img);
            }
            case "Czarnoksiężnik" -> {
                Image img = new Image(getClass().getResource("/assets/czarnoksiężnikgraint.png").toExternalForm());
                potworimg.setImage(img);
            }
            case "Ork" -> {
                Image img = new Image(getClass().getResource("/assets/orkgraint.png").toExternalForm());
                potworimg.setImage(img);
            }
            default -> potworimg.setImage(null);
        }
    }

    private void atakanim(ImageView cel, int ilośćzadana, int ilośćprzyjeta){
        graczimg.toFront();
        Label minusik = new Label("-" + ilośćzadana + "HP");
        minusik.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-font-weight: bold;");
        Gra.getChildren().add(minusik);
        minusik.setOpacity(0);

        javafx.geometry.Point2D point = cel.localToScene(0, 0);
        javafx.geometry.Point2D parentPoint = Gra.sceneToLocal(point);


        minusik.setLayoutX(parentPoint.getX() + 30);
        minusik.setLayoutY(parentPoint.getY() - 10);

        TranslateTransition leczanim = new TranslateTransition(Duration.seconds(0.5), minusik);
        leczanim.setByY(-50);

        FadeTransition leczfade = new FadeTransition(Duration.seconds(0.5), minusik);
        leczfade.setFromValue(1.0);
        leczfade.setToValue(0.0);

        ParallelTransition anim = new ParallelTransition(leczanim, leczfade);
        anim.setOnFinished(e -> {Gra.getChildren().remove(minusik);});

        RotateTransition bronanim = new RotateTransition(Duration.seconds(0.1), gweapon);
        bronanim.setByAngle(20);
        bronanim.setCycleCount(2);
        bronanim.setAutoReverse(true);



        ColorAdjust red = new ColorAdjust();
        red.setBrightness(-0.5);

        PauseTransition pause = new PauseTransition(Duration.millis(200));
        pause.setOnFinished(e -> {cel.setEffect(null);});

        atakBtn.setDisable(true);
        leczBtn.setDisable(true);
        TranslateTransition bohaterprzodanim = new TranslateTransition(Duration.seconds(0.5), graczimg);
        bohaterprzodanim.setByX(300);
        bohaterprzodanim.setCycleCount(1);
        bohaterprzodanim.setAutoReverse(false);

        atakBtn.setDisable(true);
        TranslateTransition bohatertylanim = new TranslateTransition(Duration.seconds(0.5), graczimg);
        bohatertylanim.setByX(-300);
        bohatertylanim.setCycleCount(1);
        bohatertylanim.setAutoReverse(false);

        bohaterprzodanim.play();
        bohaterprzodanim.setOnFinished(e -> {
            bronanim.play();
            cel.setEffect(red);
            pause.play();
            minusik.setOpacity(1);
            aktualizujHPPotwora();
            anim.play();
            bohatertylanim.play();

        });


        if (!potwor.czyZyje()) {
            bohatertylanim.setOnFinished(e -> {
                losujprzedmiot();
                FadeTransition fadein = new FadeTransition(Duration.seconds(1), potworimg);
                fadein.setFromValue(1);
                fadein.setToValue(0);
                aktualizujHPPotwora();
                fadein.setCycleCount(1);
                fadein.play();
                atakBtn.setDisable(false);
                fadein.setOnFinished(e2 ->{
                    potworimg.setOpacity(0);
                    nowyBtn.fire();
                });

            });
            return;
        }

        bohatertylanim.setOnFinished(e -> {
            PotworATK(graczimg, ilośćprzyjeta);
        });

    }

    private void leczanim(int ileHP){
        atakBtn.setDisable(true);
        Label plusik = new Label("+" + ileHP + "HP");
        Gra.getChildren().add(plusik);
        plusik.setStyle("-fx-text-fill: green; -fx-font-size: 24px; -fx-font-weight: bold;");

        plusik.setLayoutX(graczimg.getLayoutX()+30);
        plusik.setLayoutY(graczimg.getLayoutY()-10);

        TranslateTransition leczanim = new TranslateTransition(Duration.seconds(0.5), plusik);
        leczanim.setByY(-50);

        FadeTransition leczfade = new FadeTransition(Duration.seconds(0.5), plusik);
        leczfade.setFromValue(1.0);
        leczfade.setToValue(0.0);

        ParallelTransition anim = new ParallelTransition(leczanim, leczfade);
        anim.setOnFinished(e -> {Gra.getChildren().remove(plusik);});
        anim.play();

        anim.setOnFinished(e -> {
            PotworATK(graczimg, potwor.atak);
        });
    }

    private void nowystageanim(){
        FadeTransition fade1 = new FadeTransition(Duration.seconds(1), graczimg);
        fade1.setFromValue(1);
        fade1.setToValue(0);

        FadeTransition fadeng = new FadeTransition(Duration.seconds(1), imieGracza);
        fadeng.setFromValue(1);
        fadeng.setToValue(0);

        FadeTransition fadeHPBg = new FadeTransition(Duration.seconds(1), hpBarGracza);
        fadeHPBg.setFromValue(1);
        fadeHPBg.setToValue(0);

        FadeTransition fadenp = new FadeTransition(Duration.seconds(1), imiePotwora);
        fadenp.setFromValue(1);
        fadenp.setToValue(0);

        FadeTransition fadeHPBp = new FadeTransition(Duration.seconds(1), hpBarPotwora);
        fadeHPBp.setFromValue(1);
        fadeHPBp.setToValue(0);

        FadeTransition fadeinng = new FadeTransition(Duration.seconds(1), imieGracza);
        fadeinng.setFromValue(0);
        fadeinng.setToValue(1);

        FadeTransition fadeinHPBg = new FadeTransition(Duration.seconds(1), hpBarGracza);
        fadeinHPBg.setFromValue(0);
        fadeinHPBg.setToValue(1);

        TranslateTransition graczucieczka = new TranslateTransition(Duration.seconds(1), graczimg);
        graczucieczka.setByX(300);
        graczucieczka.setCycleCount(1);
        graczucieczka.setAutoReverse(false);

        TranslateTransition graczwejscie = new TranslateTransition(Duration.seconds(1), graczimg);
        graczwejscie.setByX(10);
        graczwejscie.setCycleCount(1);
        graczwejscie.setAutoReverse(false);

        FadeTransition FadeInGracz = new  FadeTransition(Duration.seconds(1), graczimg);
        FadeInGracz.setFromValue(0);
        FadeInGracz.setToValue(1);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));

        ParallelTransition ucieczka = new ParallelTransition(fade1, graczucieczka, fadeng, fadeHPBg, fadenp, fadeHPBp);

        ParallelTransition wejscie = new ParallelTransition(graczwejscie, FadeInGracz, fadeinng, fadeinHPBg);
        ucieczka.play();
        ucieczka.setOnFinished(e -> {
            graczimg.setTranslateX(0);
            wejscie.play();
            pause.play();
        });

        pause.setOnFinished(e -> {
            imiePotwora.setText(potwor.klasa + " " +potwor.imie);
            aktualizujHPPotwora();
            FadeTransition fade = new FadeTransition(Duration.seconds(1), potworimg);
            fade.setFromValue(0);
            fade.setToValue(1);

            FadeTransition fadeinnp = new FadeTransition(Duration.seconds(1), imiePotwora);
            fadeinnp.setFromValue(0);
            fadeinnp.setToValue(1);

            FadeTransition fadeinHPBp = new FadeTransition(Duration.seconds(1), hpBarPotwora);
            fadeinHPBp.setFromValue(0);
            fadeinHPBp.setToValue(1);

            TranslateTransition anim = new TranslateTransition(Duration.seconds(1), potworimg);
            potworimg.setLayoutX(potworimg.getLayoutX()+50);
            anim.setByX(-50);
            anim.setCycleCount(1);
            anim.setAutoReverse(false);

            ParallelTransition finalanim = new  ParallelTransition(fade, anim, fadeinnp, fadeinHPBp);
            finalanim.play();
            finalanim.setOnFinished(e1 -> {
                atakBtn.setDisable(false);
                leczBtn.setDisable(false);
                graczimg.setCursor(healCursor);
                potworimg.setCursor(attackCursor);
                potworimg.setOpacity(1);
            });
        });

    }

    private void PotworATK(StackPane cel,int ilośćzadana) {
        potworimg.toFront();
        Label minusik = new Label("-" + ilośćzadana + "HP");
        minusik.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-font-weight: bold;");
        Gra.getChildren().add(minusik);
        minusik.setOpacity(0);
        minusik.setLayoutX(cel.getLayoutX()+30);
        minusik.setLayoutY(cel.getLayoutY()-10);

        TranslateTransition leczanim = new TranslateTransition(Duration.seconds(0.5), minusik);
        leczanim.setByY(-50);

        FadeTransition leczfade = new FadeTransition(Duration.seconds(0.5), minusik);
        leczfade.setFromValue(1.0);
        leczfade.setToValue(0.0);

        ParallelTransition anim = new ParallelTransition(leczanim, leczfade);
        anim.setOnFinished(e -> {Gra.getChildren().remove(minusik);});



        ColorAdjust red = new ColorAdjust();
        red.setBrightness(-0.5);

        PauseTransition pause = new PauseTransition(Duration.millis(200));
        pause.setOnFinished(e -> {cel.setEffect(null);});

        TranslateTransition potworprzodanim = new TranslateTransition(Duration.seconds(0.5), potworimg);
        potworprzodanim.setByX(-300);
        potworprzodanim.setCycleCount(1);
        potworprzodanim.setAutoReverse(false);

        TranslateTransition potwortylanim = new TranslateTransition(Duration.seconds(0.5), potworimg);
        potwortylanim.setByX(300);
        potwortylanim.setCycleCount(1);
        potwortylanim.setAutoReverse(false);

        potworprzodanim.play();
        potworprzodanim.setOnFinished(e -> {
            cel.setEffect(red);
            pause.play();
            aktualizujHPBohatera();
            minusik.setOpacity(1);
            anim.play();
            potwortylanim.play();
        });


        potwortylanim.setOnFinished(e1 -> {
            atakBtn.setDisable(false);
            leczBtn.setDisable(false);
            graczimg.setCursor(healCursor);
            potworimg.setCursor(attackCursor);
        });
    }

    private void Smiercgraczaanim(){
        FadeTransition fadein = new FadeTransition(Duration.seconds(1), graczimg);
        fadein.setFromValue(1);
        fadein.setToValue(0);
        aktualizujHPPotwora();
        fadein.setCycleCount(1);
        fadein.play();
    }

    private String AquirePlayerName(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Podaj nazwę gracza");
        dialog.setHeaderText("Wpisz nazwę swojego bohatera:");
        dialog.setContentText("Nazwa:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("Bezimienny");
    }

    private void refreshskin(){
        Image tp = new Image(getClass().getResource("/assets/hair/hair (" + topint + ").png").toExternalForm());
        top.setImage(tp);
        gtop.setImage(tp);

        if (equipedArmour == null){
            Image mid = new Image(getClass().getResource("/assets/shirt/shirt (" + middleint + ").png").toExternalForm());
            middle.setImage(mid);
            gmiddle.setImage(mid);
        }

        Image pnts = new Image(getClass().getResource("/assets/pants/pants (" + pantsint + ").png").toExternalForm());
        pants.setImage(pnts);
        gpants.setImage(pnts);

        Image bttm = new Image(getClass().getResource("/assets/shoes/shoes (" + bottomint + ").png").toExternalForm());
        bottom.setImage(bttm);
        gbottom.setImage(bttm);

        Image skn = new Image(getClass().getResource("/assets/skins/sprite (" + skinint + ").png").toExternalForm());
        skin.setImage(skn);
        gskin.setImage(skn);
    }

    private void losujprzedmiot(){
        Random rand = new Random();
        String texture;
        int def = 0;
        int atk = 0;
        int los = rand.nextInt(1,6);
        int los2 = rand.nextInt(1,101);

        if (los2 < 50){
            def = rand.nextInt(1,11);
            atk = rand.nextInt(1,11);
        }
        else if (los2 > 50 && los2 < 80 ){
            def = rand.nextInt(10,31);
            atk = rand.nextInt(10,31);
        }
        else if (los2 > 80 && los2 < 95 ){
            def = rand.nextInt(30,41);
            atk = rand.nextInt(30,41);
        }
        else if (los2 > 95 && los2 < 100 ){
            def = rand.nextInt(40,81);
            atk = rand.nextInt(40,81);
        }


        if(los == 3){
            int id = rand.nextInt(1,5);
            switch (id) {
                case 1 -> {
                    def = 0;
                    texture = "/assets/weapon/weapon (" + rand.nextInt(1,81) + ").png";
                    gracz.Ekwipunek.add(new Przedmiot(texture, id, atk, atk));
                    log.appendText("Nowy przedmiot w ekwipunku! \n");
                }

                case 2 -> {
                    atk =0;
                    texture = "/assets/armour/armour (" + rand.nextInt(1,13) + ").png";
                    gracz.Ekwipunek.add(new Przedmiot(texture, id, def, atk));
                    log.appendText("Nowy przedmiot w ekwipunku! \n");
                }

                case 3 -> {
                    atk =0;
                    texture = "/assets/helmet/helmet (" + rand.nextInt(1,29) + ").png";
                    gracz.Ekwipunek.add(new Przedmiot(texture, id, def, atk));
                    log.appendText("Nowy przedmiot w ekwipunku! \n");
                }
                case 4 ->{
                    atk =0;
                    texture = "/assets/shield/shield (" + rand.nextInt(1,61) + ").png";
                    gracz.Ekwipunek.add(new Przedmiot(texture, id, def, atk));
                    log.appendText("Nowy przedmiot w ekwipunku! \n");
                }
                default -> {
                    return;
                }
            }
        }
        equipmentRefresh();
    };

    private void equipmentRefresh(){
        if(gracz.Ekwipunek.isEmpty()){
            return;
        }

        equipmentPane.getChildren().clear();
        for(Przedmiot przedmiot: gracz.Ekwipunek){
            addItem(przedmiot);
        }
        gweapon.setImage(null);
        weapon.setImage(null);
        Image mid = new Image(getClass().getResource("/assets/shirt/shirt (" + middleint + ").png").toExternalForm());
        middle.setImage(mid);
        gmiddle.setImage(mid);
        ghelmet.setImage(null);
        helmet.setImage(null);
        gshield.setImage(null);
        shield.setImage(null);

        if(equipedWeapon != null){
            Image weaponimg = new Image(getClass().getResource(equipedWeapon.gettexture()).toExternalForm());
            gweapon.setImage(weaponimg);
            weapon.setImage(weaponimg);
        }

        if (equipedArmour != null){
            Image armourimg = new Image(getClass().getResource(equipedArmour.gettexture()).toExternalForm());
            gmiddle.setImage(armourimg);
            middle.setImage(armourimg);
        }

        if (equipedHelmet != null){
            Image helmetimg = new Image(getClass().getResource(equipedHelmet.gettexture()).toExternalForm());
            ghelmet.setImage(helmetimg);
            helmet.setImage(helmetimg);
        }

        if (equipedShield != null){
            Image shieldimg = new Image(getClass().getResource(equipedShield.gettexture()).toExternalForm());
            gshield.setImage(shieldimg);
            shield.setImage(shieldimg);
        }
    }

    private void addItem(Przedmiot przedmiot){
        Pane pane = new Pane();
        pane.setUserData(przedmiot);
        pane.setPrefSize(400, 50);
        pane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(0),new BorderWidths(1))));
        Image img = new Image(getClass().getResource(przedmiot.gettexture()).toExternalForm());
        ImageView image = new ImageView(img);
        image.setPreserveRatio(true);
        image.setFitHeight(40);
        image.setFitWidth(40);
        image.setLayoutX(15);
        image.setLayoutY(5);

        Label label = new Label();
        if (przedmiot.getId() == 1) {
            label.setText("Atk: " + Integer.toString(przedmiot.getstats()));
        }
        else {
            label.setText("Def: " + Integer.toString(przedmiot.getstats()));
        }
        label.setLayoutX(66);
        label.setLayoutY(9);
        label.setFont(new Font(22));

        Button equipbtn = new Button();
        equipbtn.setPrefWidth(65);
        equipbtn.setLayoutX(260);
        equipbtn.setLayoutY(13);

        if (przedmiot.isequipped()) {
            equipbtn.setText("Unequip");
        }
        else{
            equipbtn.setText("Equip");
        }

        equipbtn.setOnAction(e -> {
            boolean isequipped = przedmiot.isequipped();
            if(!checkIfElseEquipped(przedmiot)){
                if (!isequipped) {
                    przedmiot.setisequipped(true);
                    switch (przedmiot.getId()) {
                        case 1 -> {
                            equipedWeapon = przedmiot;
                        }
                        case 2 -> {
                            equipedArmour = przedmiot;
                        }
                        case 3 -> {
                            equipedHelmet = przedmiot;
                        }
                        case 4 -> {
                            equipedShield = przedmiot;
                        }
                    }
                }
            }
            if (isequipped) {
                switch (przedmiot.getId()) {
                    case 1 -> {
                        equipedWeapon = null;
                    }
                    case 2 -> {
                        equipedArmour = null;
                    }
                    case 3 -> {
                        equipedHelmet = null;
                    }
                    case 4 -> {
                        equipedShield = null;
                    }
                }
                przedmiot.setisequipped(false);
            }
            equipmentRefresh();
            isArmourEquipped();
            gracz.updateStats(equipedArmour, equipedWeapon, equipedHelmet, equipedShield);
            aktualizujHPBohatera();
        });

        Button discard = new Button("Discard");
        discard.setLayoutX(330);
        discard.setLayoutY(13);
        discard.setOnAction(e -> {
            if(przedmiot.isequipped()){
                switch (przedmiot.getId()) {
                    case 1 -> {
                        equipedWeapon = null;
                    }
                    case 2 -> {
                        equipedArmour = null;
                    }
                    case 3 -> {
                        equipedHelmet = null;
                    }
                    case 4 -> {
                        equipedShield = null;
                    }
                }
            };

            przedmiot.setisequipped(false);
            equipmentPane.getChildren().remove(pane);
            gracz.Ekwipunek.remove(przedmiot);

            isArmourEquipped();
            equipmentRefresh();
            refreshskin();
            gracz.updateStats(equipedArmour, equipedWeapon, equipedHelmet, equipedShield);
        });

        pane.getChildren().addAll(image, label, equipbtn, discard);
        equipmentPane.getChildren().add(pane);

    }

    private boolean checkIfElseEquipped(Przedmiot przedmiot1){
        boolean check = false;
        for(Przedmiot przedmiot:gracz.Ekwipunek){
            if(przedmiot.getId() == przedmiot1.getId() && przedmiot.isequipped()){
                check = true;
            }
        }
        return check;
    }

    private void isArmourEquipped(){
        if(equipedArmour != null){
            pm.setDisable(true);
            nm.setDisable(true);
        }
        else{
            pm.setDisable(false);
            nm.setDisable(false);
        }
    }
}