/*
 * Decompiled with CFR 0.152.
 */
public class awS
implements alx_0 {
    public static final int diu = 300;
    protected static final awS div = new awS();
    private int aG;
    private int aH;
    private int diw = 300;
    private adg_2 dix;
    private boolean diy = false;
    private long nD = this.hashCode();
    private int diz = 0;

    private awS() {
        aam_1.aMF().start();
        acu_1.ara().start();
    }

    public static awS aJG() {
        return div;
    }

    public void as(int n2, int n3) {
        this.aG = n2;
        this.aH = n3;
    }

    public int getX() {
        return this.aG;
    }

    public int getY() {
        return this.aH;
    }

    public long getId() {
        return this.nD;
    }

    public void c(long l2) {
        this.nD = l2;
    }

    public int aJH() {
        return this.diw;
    }

    public void mC(int n2) {
        this.diw = n2;
    }

    public void d(adg_2 adg_22, int n2) {
        if (this.dix == adg_22 && this.diz == n2) {
            this.diy = true;
        } else {
            this.dix = adg_22;
            this.diz = n2;
            this.diy = false;
        }
    }

    public void a(adg_2 adg_22, abd_1 abd_12) {
        if (this.dix == adg_22) {
            aam_1.aMF().b(this);
            if (this.diy) {
                this.c(adg_22, abd_1.f(abd_12));
                this.b(adg_22, abd_1.f(abd_12));
                this.diy = false;
                this.dix = null;
            } else {
                aam_1.aMF().a(this, this.diw, adg_22.hashCode(), 1);
                this.c(adg_22, abd_1.f(abd_12));
                return;
            }
        }
        this.dix = null;
        this.diz = 0;
    }

    public boolean a(pr_0 pr_02) {
        if (pr_02 instanceof axe_0) {
            this.diz = 0;
            this.dix = null;
            this.diy = false;
            return false;
        }
        return true;
    }

    public void b(adg_2 adg_22, abd_1 abd_12) {
        abd_12.a(qe_1.bFC);
        abd_12.nh(2);
        abd_12.X(true);
        adg_22.f(abd_12);
    }

    public void c(adg_2 adg_22, abd_1 abd_12) {
        abd_12.a(qe_1.bFB);
        abd_12.nh(1);
        adg_22.f(abd_12);
    }

    public boolean o(adg_2 adg_22) {
        int n2 = adg_22.getDisplayX();
        int n3 = adg_22.getDisplayY();
        return adg_22.getAppearance().aY(this.aG - n2, this.aH - n3);
    }
}

