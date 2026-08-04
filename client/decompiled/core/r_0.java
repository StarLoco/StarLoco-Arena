/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from r
 */
public class r_0 {
    private afP D;
    private aab_2 E = null;
    private String F = null;
    private final ArrayList G;

    public r_0(String string) {
        this.F = string;
        this.G = new ArrayList();
    }

    public String x() {
        return this.F;
    }

    public aab_2 y() {
        return this.E;
    }

    public void a(aab_2 aab_22) {
        this.E = aab_22;
    }

    public afP z() {
        return this.D;
    }

    public void a(afP afP2) {
        this.D = afP2;
    }

    public int A() {
        return this.D.A();
    }

    public int getLevel() {
        return this.D.getLevel();
    }

    public boolean B() {
        return this.D.B();
    }

    public void C() {
        this.b(this.D.avB());
    }

    public void D() {
        Wq.ajf().c(this);
        add_1.aOG().f(this);
        add_1.aOG().w(this.F, false);
    }

    public void b(int n2) {
        this.D();
        for (ja_1 ja_12 : this.G) {
            ja_12.b(n2);
        }
    }

    public void a(ja_1 ja_12) {
        this.G.add(ja_12);
    }

    public void b(ja_1 ja_12) {
        this.G.remove(ja_12);
    }
}

