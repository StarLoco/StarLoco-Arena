/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from aEU
 */
public final class aeu_1 {
    public static final int dED = 0;
    public static final int dEE = 1;
    public static final int dEF = 2;
    public static final int dEG = 4;
    public static final int dEH = 8;
    private GL go;
    private int dEI = 0;

    public final void r(GL gL) {
        this.go = gL;
    }

    public final void nO(int n2) {
        if (n2 == this.dEI) {
            return;
        }
        if (n2 == 0) {
            if ((this.dEI & 1) != 0) {
                this.go.glDisableClientState(32884);
            }
            if ((this.dEI & 2) != 0) {
                this.go.glDisableClientState(32885);
            }
            if ((this.dEI & 4) != 0) {
                this.go.glDisableClientState(32886);
            }
            if ((this.dEI & 8) != 0) {
                this.go.glDisableClientState(32888);
            }
            this.dEI = n2;
            return;
        }
        this.dEI = n2;
        if ((this.dEI & 1) != 0) {
            this.go.glEnableClientState(32884);
        }
        if ((this.dEI & 2) != 0) {
            this.go.glEnableClientState(32885);
        }
        if ((this.dEI & 4) != 0) {
            this.go.glEnableClientState(32886);
        }
        if ((this.dEI & 8) != 0) {
            this.go.glEnableClientState(32888);
        }
    }
}

