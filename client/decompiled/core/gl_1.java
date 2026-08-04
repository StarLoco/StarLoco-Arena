/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from GL
 */
public final class gl_1
extends akE {
    public final String bco;

    public gl_1(lc_0 lc_02, String string) {
        super(lc_02);
        this.bco = string;
    }

    public String toString() {
        return this.bco == null ? "break;" : "break " + this.bco + ';';
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

