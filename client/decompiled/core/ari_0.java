/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from arI
 */
public abstract class ari_0 {
    private static final Logger a = Logger.getLogger(ari_0.class);
    protected final aba_2 cuD;
    private final agf_0 cQu = new agf_0();
    private boolean cQv = true;
    private float cQw = 1.0f;
    private int cQx;
    private int oK;

    protected ari_0(aba_2 aba_22) {
        this.cuD = aba_22;
        this.aEN();
    }

    public final float aEK() {
        return (float)((double)this.cQw * this.oZ());
    }

    public final int getScreenWidth() {
        return this.cQx;
    }

    public final int getScreenHeight() {
        return this.oK;
    }

    public final boolean y(int n2, int n3, int n4, int n5) {
        return n5 >= this.cQu.bAB && n3 <= this.cQu.bAC && n2 >= this.cQu.bAD && n4 <= this.cQu.bAE;
    }

    public final void bT(int n2, int n3) {
        this.cQx = n2;
        this.oK = n3;
        float f = (float)n2 / 1024.0f;
        float f2 = (float)n3 / 768.0f;
        this.cQw = Math.max(1.0f, Math.min(f, f2));
        this.aEN();
    }

    protected final void aEL() {
        int n2 = this.getScreenY();
        int n3 = this.getScreenX();
        double d = Math.abs(1.0 / (2.0 * this.oZ()));
        int n4 = (int)Math.ceil((double)this.getScreenWidth() * d);
        int n5 = (int)Math.ceil((double)this.getScreenHeight() * d);
        this.cQu.bAB = n3 - n4;
        this.cQu.bAC = n3 + n4;
        this.cQu.bAD = n2 - n5;
        this.cQu.bAE = n2 + n5;
        this.cQv = false;
    }

    public final boolean aEM() {
        return this.cQv;
    }

    protected final void aEN() {
        this.cQv = true;
    }

    public final agf_0 aEO() {
        return this.cQu;
    }

    public final int getScreenX() {
        return (int)Math.round(this.oX());
    }

    public final int getScreenY() {
        return (int)Math.round(this.oY());
    }

    public abstract double oV();

    public abstract double oW();

    public abstract double oX();

    public abstract double oY();

    public abstract double oZ();

    public abstract void bI(int var1);
}

