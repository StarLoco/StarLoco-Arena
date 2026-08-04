/*
 * Decompiled with CFR 0.152.
 */
import java.util.Stack;

/*
 * Renamed from rw
 */
public class rw_2 {
    private StringBuilder ahl = new StringBuilder();
    private boolean ahm = false;
    private di_0 ahn = di_0.mr;
    private Stack aho = new Stack();

    public rw_2 D(Object object) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(object.toString());
        return this;
    }

    public rw_2 bJ(String string) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(string);
        return this;
    }

    public rw_2 y(byte by) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(by);
        return this;
    }

    public rw_2 Y(short s) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(s);
        return this;
    }

    public rw_2 db(int n2) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(n2);
        return this;
    }

    public rw_2 aE(long l2) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(l2);
        return this;
    }

    public rw_2 O(float f) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(f);
        return this;
    }

    public rw_2 i(double d) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(d);
        return this;
    }

    public rw_2 au(boolean bl2) {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append(bl2);
        return this;
    }

    private void bK(String string) {
        this.aho.push(string);
        this.ahl.append("<").append(string);
        this.ahm = true;
        this.ahn = di_0.ms;
    }

    private void bL(String string) {
        this.aho.pop();
        this.ahl.append("</").append(string).append(">");
        this.ahn = di_0.mr;
    }

    private void a(String string, di_0 di_02) {
        this.aho.push(string);
        this.ahl.append("<").append(string);
        this.ahm = true;
        this.ahn = di_02;
    }

    public rw_2 wE() {
        if (this.ahm) {
            this.wT();
        }
        this.bK("b");
        return this;
    }

    public rw_2 wF() {
        if (this.ahm) {
            this.wT();
        }
        this.bL("b");
        return this;
    }

    public rw_2 wG() {
        if (this.ahm) {
            this.wT();
        }
        this.bK("c");
        return this;
    }

    public rw_2 wH() {
        if (this.ahm) {
            this.wT();
        }
        this.bL("c");
        return this;
    }

    public rw_2 wI() {
        if (this.ahm) {
            this.wT();
        }
        this.bK("i");
        return this;
    }

    public rw_2 wJ() {
        if (this.ahm) {
            this.wT();
        }
        this.bL("i");
        return this;
    }

    public rw_2 wK() {
        if (this.ahm) {
            this.wT();
        }
        this.bK("u");
        return this;
    }

    public rw_2 wL() {
        if (this.ahm) {
            this.wT();
        }
        this.bL("u");
        return this;
    }

    public rw_2 wM() {
        if (this.ahm) {
            this.wT();
        }
        this.ahl.append("\n");
        return this;
    }

    public rw_2 wN() {
        if (this.ahm) {
            this.wT();
        }
        this.bK("text");
        return this;
    }

    public rw_2 wO() {
        if (this.ahm) {
            this.wT();
        }
        this.bL("text");
        return this;
    }

    public rw_2 wP() {
        if (this.ahm) {
            this.wT();
        }
        this.a("image", di_0.mt);
        return this;
    }

    public rw_2 wQ() {
        if (this.ahm) {
            this.wT();
        }
        this.bL("image");
        return this;
    }

    public rw_2 a(String string, int n2, int n3, String string2) {
        this.wP();
        this.j("pixmap", string);
        if (n2 > 0) {
            this.j("width", String.valueOf(n2));
        }
        if (n3 > 0) {
            this.j("height", String.valueOf(n3));
        }
        if (string2 != null) {
            this.j("align", string2);
        }
        this.wQ();
        return this;
    }

    public rw_2 bM(String string) {
        this.i("color", string);
        return this;
    }

    public rw_2 bN(String string) {
        this.i("name", string);
        return this;
    }

    public rw_2 dc(int n2) {
        this.i("size", String.valueOf(n2));
        return this;
    }

    public rw_2 bO(String string) {
        this.i("id", string);
        return this;
    }

    public rw_2 dd(int n2) {
        this.i("width", String.valueOf(n2));
        return this;
    }

    public rw_2 de(int n2) {
        this.i("height", String.valueOf(n2));
        return this;
    }

    public rw_2 bP(String string) {
        this.i("pixmap", string);
        return this;
    }

    public String toString() {
        return this.ahl.toString();
    }

    public String wR() {
        if (this.aho.size() != 0) {
            this.wS();
        }
        return this.ahl.toString();
    }

    public static boolean bQ(String string) {
        return string.contains("color=");
    }

    private void i(String string, String string2) {
        if (this.ahn != di_0.ms) {
            this.wN();
        }
        this.ahl.append(" ").append(string).append("=\"").append(string2).append("\"");
    }

    private void j(String string, String string2) {
        if (this.ahn != di_0.mt) {
            this.wN();
        }
        this.ahl.append(" ").append(string).append("=\"").append(string2).append("\"");
    }

    private void wS() {
        if (this.ahm) {
            this.wT();
        }
        while (this.aho.size() != 0) {
            this.ahl.append("</").append((String)this.aho.pop()).append(">");
        }
    }

    private void wT() {
        if (this.ahm) {
            this.ahl.append(">");
            this.ahm = false;
        }
    }

    public boolean wU() {
        return this.ahm;
    }

    public static void main(String[] stringArray) {
        rw_2 rw_22 = new rw_2();
        rw_22.bJ("Du chien !").wE().bM("FF00FF").bJ("en Carton").a("plop", -1, -1, null);
        rw_22.wI().bJ("en italique");
        System.out.println(rw_22.wR());
    }
}

