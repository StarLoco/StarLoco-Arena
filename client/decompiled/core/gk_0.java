/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from gk
 */
public abstract class gk_0
extends azV
implements aao_0,
alW {
    private final String sF;
    public final String name;
    public final atu_0 sG;
    public final atu_0[] sH;

    public gk_0(lc_0 lc_02, String string, short s, String string2, atu_0 atu_02, atu_0[] atu_0Array) {
        super(lc_02, s);
        this.sF = string;
        this.name = string2;
        this.sG = atu_02;
        if (atu_02 != null) {
            atu_02.a(new us_1(this));
        }
        this.sH = atu_0Array;
        for (int j = 0; j < atu_0Array.length; ++j) {
            atu_0Array[j].a(new us_1(this));
        }
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public String jv() {
        return this.sF;
    }

    public boolean jw() {
        return this.sF != null && this.sF.indexOf("@deprecated") != -1;
    }
}

