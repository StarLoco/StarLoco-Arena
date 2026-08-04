/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tp
 */
public abstract class tp_1
extends aiu_0
implements mg_2,
axu_0 {
    protected static final short amG = Short.MIN_VALUE;
    private boolean amH = false;
    private static final String amI = "AnimTrans_";
    private static final String amJ = "AnimStatique_";
    private static final String amK = "Func_";
    private static final String amL = "End";
    private ym_0 rI;
    protected apn_0 amM;
    private int amN;
    private int amO;
    protected short amP = Short.MIN_VALUE;
    private boolean amQ;

    protected void a(ym_0 ym_02) {
        this.rI = ym_02;
    }

    public long getId() {
        return this.amM.getId();
    }

    public int zm() {
        return this.amN;
    }

    public void dE(int n2) {
        this.amN = n2;
    }

    public int zn() {
        return this.amO;
    }

    public void aL(int n2) {
        this.amO = n2;
    }

    public void setColor(int n2) {
        vP vP2 = new vP(n2);
        this.aaV[0] = vP2.Cr();
        this.aaV[1] = vP2.Cq();
        this.aaV[2] = vP2.Cp();
        this.dLN = this.aaV[3] = vP2.getAlpha();
        this.dLO = this.aaV[3];
    }

    public byte zo() {
        return (byte)this.ge();
    }

    public void B(byte by) {
        this.bJ(by);
    }

    public void ay(boolean bl2) {
        int n2 = bl2 ? 2 : -2;
        this.bR(0.4375f + (float)n2 * 0.0625f);
    }

    public void j() {
        this.tJ.release();
        this.amN = 0;
        this.amO = 0;
        this.amM = null;
        this.rI = null;
        this.amP = Short.MIN_VALUE;
        this.amH = false;
        this.aTD();
    }

    public void b() {
        this.amQ = true;
    }

    public void release() {
        if (this.rI != null) {
            try {
                this.rI.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"Exception lev\u00e9e du retour d'un objet dans son pool : ", (Throwable)exception);
            }
        } else {
            a.error((Object)"L'objet ne peut retourner dans un pool, il n'a pas \u00e9t\u00e9 initialis\u00e9");
        }
    }

    public apn_0 zp() {
        return this.amM;
    }

    public void a(apn_0 apn_02) {
        this.amM = apn_02;
    }

    protected void a(byte by, boolean bl2, atx atx2, qc_0 qc_02) {
        String string;
        boolean bl3;
        boolean bl4 = this.amP != by;
        boolean bl5 = bl3 = this.ak != qc_02;
        if (!(by != -1 && (bl4 || bl3 || atx2 == atx.cTY) || (string = this.AU()) == null || string.equals("AnimStatique"))) {
            return;
        }
        this.b(qc_02);
        string = !bl4 && bl3 || atx2 == atx.cTZ ? amJ : (this.amP == Short.MIN_VALUE ? amJ : (bl2 ? amI + this.amP + "_" : amI));
        this.aY(string + by);
        this.dW(amJ + by);
        this.aTt();
    }

    public boolean zq() {
        return this.amH;
    }

    public void az(boolean bl2) {
        this.amH = bl2;
    }

    public void a(ahh_1 ahh_12) {
        this.amM.a(this);
    }

    public boolean zr() {
        return this.amQ;
    }

    public void aA(boolean bl2) {
        this.amQ = bl2;
    }

    public abstract mp_0[] kD();
}

