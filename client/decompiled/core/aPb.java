/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public final class aPb
implements cn_1 {
    public static int end = 0;
    private static final abk_2 ene = new ade_1(new auP(), 10000);
    public static final aPb enf = aPb.aYI();
    public static final aPb eng;
    private static final int enh = 4;
    private static final Logger a;
    private float[] eni = new float[4];
    private float[] enj = new float[4];
    private float[] enk = new float[4];
    private float[] enl = new float[4];
    private float enm;

    public static aPb aYI() {
        try {
            return (aPb)ene.adr();
        }
        catch (Exception exception) {
            throw new RuntimeException("Erreur lors d'un checkOut sur un Material : ", exception);
        }
    }

    private aPb() {
    }

    public final void d(aPb aPb2) {
        System.arraycopy(aPb2.eni, 0, this.eni, 0, aPb2.eni.length);
        System.arraycopy(aPb2.enj, 0, this.enj, 0, aPb2.enj.length);
        System.arraycopy(aPb2.enk, 0, this.enk, 0, aPb2.enk.length);
        System.arraycopy(aPb2.enl, 0, this.enl, 0, aPb2.enl.length);
        this.enm = aPb2.enm;
    }

    public final float[] aYJ() {
        return this.eni;
    }

    public final void F(float[] fArray) {
        System.arraycopy(fArray, 0, this.eni, 0, this.eni.length);
    }

    public final void F(float f, float f2, float f3, float f4) {
        this.eni[0] = f;
        this.eni[1] = f2;
        this.eni[2] = f3;
        this.eni[3] = f4;
    }

    public final float[] aYK() {
        return this.enj;
    }

    public final void G(float[] fArray) {
        System.arraycopy(fArray, 0, this.enj, 0, this.enj.length);
    }

    public final void G(float f, float f2, float f3, float f4) {
        this.enj[0] = f;
        this.enj[1] = f2;
        this.enj[2] = f3;
        this.enj[3] = f4;
    }

    public final float[] aYL() {
        return this.enk;
    }

    public final void H(float[] fArray) {
        System.arraycopy(fArray, 0, this.enk, 0, this.enk.length);
    }

    public final void H(float f, float f2, float f3, float f4) {
        this.enk[0] = f;
        this.enk[1] = f2;
        this.enk[2] = f3;
        this.enk[3] = f4;
    }

    public final float[] aYM() {
        return this.enl;
    }

    public final void I(float[] fArray) {
        System.arraycopy(fArray, 0, this.enl, 0, this.enl.length);
    }

    public final void I(float f, float f2, float f3, float f4) {
        this.enl[0] = f;
        this.enl[1] = f2;
        this.enl[2] = f3;
        this.enl[3] = f4;
    }

    public final float aYN() {
        return this.enm;
    }

    public final void bY(float f) {
        this.enm = f;
    }

    public final boolean e(aPb aPb2) {
        if (aPb2 == this) {
            return true;
        }
        if (!this.g(this.enj, aPb2.enj)) {
            return false;
        }
        if (!this.g(this.enk, aPb2.enk)) {
            return false;
        }
        if (!this.g(this.eni, aPb2.eni)) {
            return false;
        }
        if (!this.g(this.enl, aPb2.enl)) {
            return false;
        }
        return this.enm == aPb2.enm;
    }

    private boolean g(float[] fArray, float[] fArray2) {
        if (fArray == fArray2) {
            return true;
        }
        return fArray[0] == fArray2[0] && fArray[1] == fArray2[1] && fArray[2] == fArray2[2] && fArray[3] == fArray2[3];
    }

    public void release() {
        try {
            ene.af(this);
        }
        catch (Exception exception) {
            a.error((Object)("Exception dans le release de " + this.getClass().toString() + "(normalement impossible)"));
        }
    }

    public void b() {
    }

    public void j() {
    }

    /* synthetic */ aPb(auP auP2) {
        this();
    }

    static {
        enf.F(0.0f, 0.0f, 0.0f, 1.0f);
        enf.G(1.0f, 1.0f, 1.0f, 1.0f);
        enf.H(1.0f, 1.0f, 1.0f, 1.0f);
        enf.I(0.0f, 0.0f, 0.0f, 1.0f);
        enf.bY(128.0f);
        eng = aPb.aYI();
        eng.F(0.0f, 0.0f, 0.0f, 1.0f);
        eng.G(0.0f, 0.0f, 0.0f, 1.0f);
        eng.H(0.0f, 0.0f, 0.0f, 1.0f);
        eng.I(0.0f, 0.0f, 0.0f, 1.0f);
        eng.bY(128.0f);
        a = Logger.getLogger(aPb.class);
    }
}

