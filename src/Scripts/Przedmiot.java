package Scripts;

public class Przedmiot {
    private int id;
    private String texture;
    private int def;
    private int atk;
    private boolean isequipped = false;

    public Przedmiot(String texture, int id, int def, int atk) {
        this.texture = texture;
        this.id = id;
        this.def = def;
        this.atk = atk;
    }

    public int getstats() {
        if (id == 1) {
            //1=weapon
            return atk;
        }
        else if (id == 2 || id == 3 || id == 4) {
            //2=armour, 3=helmet, 4=Shield
            return def;
        };
        return 0;
    }

    public int getId() {
        return id;
    }

    public String gettexture() {
        return texture;
    }

    @Override
    public String toString() {
        return "Przedmiot{" +
                "id=" + id +
                ", texture='" + texture + '\'' +
                ", def=" + def +
                ", atk=" + atk +
                '}';
    }

    public boolean isequipped() {
        return isequipped;
    }

    public void setisequipped(boolean isequipped) {
        this.isequipped = isequipped;
    }
}
