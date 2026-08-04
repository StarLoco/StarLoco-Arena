/*
 * Decompiled with CFR 0.152.
 */
public final class Nl
extends jy_2 {
    public final jy_2 bzn;
    public final atu_0 HD;
    public final jy_2[] avU;
    protected asn asH = null;

    public Nl(lc_0 lc_02, jy_2 jy_22, atu_0 atu_02, jy_2[] jy_2Array) {
        super(lc_02);
        this.bzn = jy_22;
        this.HD = atu_02;
        this.avU = jy_2Array;
    }

    public Nl(lc_0 lc_02, jy_2 jy_22, asn asn2, jy_2[] jy_2Array) {
        super(lc_02);
        this.bzn = jy_22;
        this.HD = null;
        this.avU = jy_2Array;
        this.asH = asn2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.bzn != null) {
            stringBuffer.append(this.bzn.toString()).append('.');
        }
        stringBuffer.append("new ");
        if (this.HD != null) {
            stringBuffer.append(this.HD.toString());
        } else if (this.asH != null) {
            stringBuffer.append(this.asH.toString());
        } else {
            stringBuffer.append("???");
        }
        stringBuffer.append('(');
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
        ax.c(this);
    }

    public void a(EO eO) {
        eO.c(this);
    }
}

