/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from fB
 */
public class fb_2 {
    public final boolean rB;
    public final asn rC;
    public xl_2 rD;

    public fb_2(boolean bl2, asn asn2) {
        this.rB = bl2;
        this.rC = asn2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.rB) {
            stringBuffer.append("final ");
        }
        stringBuffer.append(this.rC).append(" ");
        return stringBuffer.toString();
    }

    public void a(xl_2 xl_22) {
        this.rD = xl_22;
    }

    public short jl() {
        if (this.rD == null) {
            return -1;
        }
        return this.rD.jl();
    }
}

