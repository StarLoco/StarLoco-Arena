/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aFI
 */
public final class afi_2
extends jy_2 {
    public final jy_2 bzn;
    public final uy_1 dHC;
    public final jy_2[] avU;

    public afi_2(lc_0 lc_02, jy_2 jy_22, uy_1 uy_12, jy_2[] jy_2Array) {
        super(lc_02);
        this.bzn = jy_22;
        this.dHC = uy_12;
        this.avU = jy_2Array;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.bzn != null) {
            stringBuffer.append(this.bzn.toString()).append('.');
        }
        stringBuffer.append("new ").append(this.dHC.aqD.toString()).append("() { ... }");
        return stringBuffer.toString();
    }

    public void a(Ax ax) {
        ax.c(this);
    }

    public void a(EO eO) {
        eO.c(this);
    }
}

