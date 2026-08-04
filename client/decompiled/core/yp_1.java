/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from yp
 */
public class yp_1 {
    protected static final Logger a = Logger.getLogger(yp_1.class);
    private static boolean aCf = false;
    private Cs aCg = null;
    private ml_2 aCh = new ml_2("ProxyClient");
    private eG aCi = null;
    private RE aCj = null;
    private boolean aCk = false;
    private int gP = 1000;
    private int gQ = 0;
    private ait_0 aCl = null;

    public yp_1() {
        if (!aCf) {
            acu_1.ara().start();
            aam_1.aMF().start();
            aCf = true;
        }
    }

    private void EU() {
        this.aCh.a(this.aCg);
        this.aCh.a(this.aCi);
        this.aCh.a(this.aCj);
        this.aCh.z(this.gP);
        this.aCh.A(this.gQ);
        this.aCh.initialize();
        this.aCk = true;
    }

    private boolean EV() {
        if (this.aCg == null) {
            return false;
        }
        if (this.aCj == null) {
            return false;
        }
        return this.aCi != null;
    }

    public ml_2 EW() {
        return this.aCh;
    }

    public boolean c(String string, int[] nArray) {
        if (!this.aCk && this.EV()) {
            this.EU();
            this.aCh.n(null);
        }
        if (this.aCl != null) {
            if (!this.aCl.isConnected()) {
                this.aCl = null;
            } else {
                a.error((Object)"Une connexion est toujours active");
                return false;
            }
        }
        int n2 = 3;
        int n3 = 1000;
        for (int n4 : nArray) {
            a.info((Object)("Tentative de connexion " + string + " : " + n4));
            this.aCl = this.aCh.a(string, n4, n3, n2);
            if (this.aCl != null) break;
            a.error((Object)("La connexion au proxy (" + string + " : " + n4 + ") n'a pas pu etre \u00e9tablie en " + n2 * n3 + " ms"));
        }
        return true;
    }

    public Cs EX() {
        return this.aCg;
    }

    public void b(Cs cs) {
        this.aCg = cs;
    }

    public eG EY() {
        return this.aCi;
    }

    public void b(eG eG2) {
        this.aCi = eG2;
    }

    public RE EZ() {
        return this.aCj;
    }

    public void b(RE rE) {
        this.aCj = rE;
    }

    public void z(int n2) {
        this.gP = n2;
    }

    public void A(int n2) {
        this.gQ = n2;
    }
}

