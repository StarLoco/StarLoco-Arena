/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZK
 */
public class zk_1
extends ayp {
    private String ayx;

    public aij_2 Ce() {
        return aij_2.cxE;
    }

    public zk_1(String string) {
        this.ayx = string.replace('\"', ' ');
        this.ayx = this.ayx.trim().intern();
    }

    public String getValue() {
        return this.ayx;
    }

    public Enum eN() {
        return cr_1.jU;
    }
}

