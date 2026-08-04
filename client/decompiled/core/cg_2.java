/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from Cg
 */
public abstract class cg_2
extends DM
implements aao_0,
alW {
    private final String sF;
    public String name;
    public atu_0[] aKH;
    public final List aKI = new ArrayList();
    asn[] aKJ = null;

    protected cg_2(lc_0 lc_02, String string, short s, String string2, atu_0[] atu_0Array) {
        super(lc_02, s);
        this.sF = string;
        this.name = string2;
        this.aKH = atu_0Array;
        for (int j = 0; j < atu_0Array.length; ++j) {
            atu_0Array[j].a(new us_1(this));
        }
    }

    public String toString() {
        return this.name;
    }

    public void e(aBi aBi2) {
        this.aKI.add(aBi2);
        aBi2.a(this);
        if (this.aOl != null) {
            this.aOl.aFn();
        }
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

