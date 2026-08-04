/*
 * Decompiled with CFR 0.152.
 */
public final class La
extends afN {
    public final alb_0 bpM;
    ff_2 boI;

    public La(lc_0 lc_02, alb_0 alb_02, String string, jy_2[] jy_2Array) {
        super(lc_02, string, jy_2Array);
        this.bpM = alb_02;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.bpM != null) {
            stringBuffer.append(this.bpM.toString()).append('.');
        }
        stringBuffer.append(this.methodName).append('(');
        for (int j = 0; j < this.avU.length; ++j) {
            if (j > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.avU[j].toString());
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public void a(Ax ax) {
        ax.d(this);
    }

    public void a(EO eO) {
        eO.d(this);
    }
}

