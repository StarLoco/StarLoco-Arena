/*
 * Decompiled with CFR 0.152.
 */
import java.util.Map;

/*
 * Renamed from kr
 */
public class kr_0
extends qs_0
implements ec_0 {
    public static final int Ec = -1;
    public static final int Ed = -1;
    private int Ee = -1;
    private mp_0[] vh;
    private Map Ef;
    private int Eg = -1;
    protected boolean Eh = true;
    private ano_0 Ei = new ano_0();
    private String Ej = "execution_Time";
    protected JX nr;

    public kr_0(int n2, int n3, int n4) {
        super(n2, n3, n4);
    }

    public kr_0() {
        super(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public void a(mp_0 mp_02) {
        if (this.vh == null) {
            this.vh = new mp_0[1];
        } else {
            mp_0[] mp_0Array = new mp_0[this.vh.length + 1];
            System.arraycopy(this.vh, 0, mp_0Array, 0, this.vh.length);
            this.vh = mp_0Array;
        }
        this.vh[this.vh.length - 1] = mp_02;
    }

    public int oR() {
        return this.Ee;
    }

    public void bG(int n2) {
        this.Ee = n2;
    }

    public void b(Map map) {
        this.Ef = map;
    }

    public void a(mp_0[] mp_0Array) {
        this.vh = mp_0Array;
    }

    public long oS() {
        if (this.Ee != 0 && this.Ee != -1) {
            try {
                this.nr = Ky.WG().a(this.Ee, this.vh, this.Ef, (ec_0)this, false);
                if (this.nr != null) {
                    jJ jJ2 = (jJ)this.Ei.get(this.Ej);
                    if (jJ2 != null && jJ2.np() == aos_1.elU) {
                        this.Eh = false;
                        return ((Double)jJ2.getValue()).longValue();
                    }
                    this.Eg = this.nr.getId();
                    this.Eh = true;
                    return -1L;
                }
            }
            catch (AssertionError assertionError) {
                a.error((Object)"ERREUR CRITIQUE DANS UN SCRIPT", (Throwable)((Object)assertionError));
            }
        }
        this.Eg = -1;
        this.Nn();
        return -1L;
    }

    public void b(JX jX) {
        if (this.Eg != jX.getId() && this.Eg != -1) {
            a.error((Object)("on tente de finir une action de script(" + this.Eg + ") demand\u00e9 par la fin d'un autre script(" + jX.getId() + ")"));
        }
        jX.c(this);
        jJ jJ2 = (jJ)this.Ei.get(this.Ej);
        if (jJ2 != null && jJ2.np() == aos_1.elU) {
            this.Eh = false;
            return;
        }
        this.Eg = -1;
        if (this.Eh) {
            this.Nn();
            this.Eh = false;
        }
        this.nr = null;
    }

    public void a(JX jX, aeF aeF2, String string) {
        assert (this.Eg == jX.getId() || this.Eg == -1);
        jX.c(this);
        this.Eg = -1;
        this.Nn();
    }

    public void c(JX jX) {
        this.Ei.put(this.Ej, jX.eU(this.Ej));
    }

    public int oT() {
        return this.Eg;
    }

    protected void ax() {
    }
}

