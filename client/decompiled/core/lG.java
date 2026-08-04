/*
 * Decompiled with CFR 0.152.
 */
public final class lG
extends akE {
    public final short HC;
    public final atu_0 HD;
    public final jk_2[] HE;

    public lG(lc_0 lc_02, short s, atu_0 atu_02, jk_2[] jk_2Array) {
        super(lc_02);
        this.HC = s;
        this.HD = atu_02;
        this.HD.a(this);
        this.HE = jk_2Array;
        for (int j = 0; j < jk_2Array.length; ++j) {
            jk_2 jk_22 = jk_2Array[j];
            if (jk_22.BO == null) continue;
            jf_1.b(jk_22.BO, this);
        }
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.HC != 0) {
            stringBuffer.append(pp_0.R(this.HC)).append(' ');
        }
        stringBuffer.append(this.HD).append(' ').append(this.HE[0].toString());
        for (int j = 1; j < this.HE.length; ++j) {
            stringBuffer.append(", ").append(this.HE[j].toString());
        }
        return stringBuffer.append(';').toString();
    }
}

