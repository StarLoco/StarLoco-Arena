/*
 * Decompiled with CFR 0.152.
 */
public final class aBi
extends akE
implements aR,
alW {
    private final String sF;
    public final short HC;
    public final atu_0 HD;
    public final jk_2[] HE;

    public aBi(lc_0 lc_02, String string, short s, atu_0 atu_02, jk_2[] jk_2Array) {
        super(lc_02);
        this.sF = string;
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

    public void a(el_1 el_12) {
        this.a((aim_2)el_12);
    }

    public el_1 bV() {
        return (el_1)this.Dw();
    }

    public boolean isStatic() {
        return (this.HC & 8) != 0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(pp_0.R(this.HC)).append(' ').append(this.HD).append(' ');
        stringBuffer.append(this.HE[0]);
        for (int j = 1; j < this.HE.length; ++j) {
            stringBuffer.append(", ").append(this.HE[j]);
        }
        return stringBuffer.toString();
    }

    public void a(ea_2 ea_22) {
        ea_22.a(this);
    }

    public void a(awv_0 awv_02) {
        awv_02.a(this);
    }

    public String jv() {
        return this.sF;
    }

    public boolean jw() {
        return this.sF != null && this.sF.indexOf("@deprecated") != -1;
    }
}

