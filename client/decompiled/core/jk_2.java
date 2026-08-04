/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from jK
 */
public final class jk_2
extends aj_1 {
    public final String name;
    public final int BN;
    public final fd_2 BO;
    public fb_2 BP = null;

    public jk_2(lc_0 lc_02, String string, int n2, fd_2 fd_22) {
        super(lc_02);
        this.name = string;
        this.BN = n2;
        this.BO = fd_22;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.name);
        for (int j = 0; j < this.BN; ++j) {
            stringBuffer.append("[]");
        }
        if (this.BO != null) {
            stringBuffer.append(" = ").append(this.BO);
        }
        return stringBuffer.toString();
    }
}

