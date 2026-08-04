/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from aCQ
 */
public abstract class acq_2 {
    protected oc_2 duQ = new oc_2();
    protected ap_1 aQE;
    protected ap_1 aQF;
    protected Pq duR;
    protected String m_name = "<undefined>";
    public static final int duS = 0;
    public static final int duT = 1;
    public static final int duU = 2;
    public static final int duV = 3;
    public static final int duW = 4;

    public oc_2 aOx() {
        return this.duQ;
    }

    public boolean q(GL gL) {
        if (this.duQ != null) {
            return this.duQ.e(gL);
        }
        return true;
    }

    public abstract void no(int var1);

    public abstract int a(ep_2 var1, int var2);

    public abstract void a(GL var1, ba_0 var2);

    public final void b(GL gL, ba_0 ba_02) {
        if (this.duR != null) {
            db_2 db_22 = arX.cQT.iE();
            vo_1 vo_12 = vo_1.aik();
            vo_12.a(jq_0.bmI);
            vo_12.n(db_22);
            gL.glPushMatrix();
            this.duR.a(gL);
        }
        if (this.aQE != null) {
            this.aQE.a(gL);
        }
        this.a(gL, ba_02);
        if (this.aQF != null) {
            this.aQF.a(gL);
        }
        if (this.duR != null) {
            gL.glPopMatrix();
        }
    }

    public void a(ah_2 ah_22, ba_0 ba_02) {
    }

    public void b(ah_2 ah_22, ba_0 ba_02) {
    }

    public ap_1 aOy() {
        return this.aQE;
    }

    public void a(ap_1 ap_12) {
        this.aQE = ap_12;
    }

    public ap_1 aOz() {
        return this.aQF;
    }

    public void b(ap_1 ap_12) {
        this.aQF = ap_12;
    }

    public Pq aOA() {
        return this.duR;
    }

    public void b(Pq pq) {
        this.duR = pq;
    }

    public String getName() {
        return this.m_name;
    }

    public abstract ba_0 aOB();
}

