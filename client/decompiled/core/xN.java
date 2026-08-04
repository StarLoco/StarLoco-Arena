/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class xN
extends aOE
implements alW {
    private final String sF;
    public final short HC;
    public final atu_0 HD;
    public final String name;
    public final anb_1[] azz;
    public final atu_0[] azA;
    public final List azB;
    asn azC = null;
    public Map avW = null;

    public xN(lc_0 lc_02, String string, short s, atu_0 atu_02, String string2, anb_1[] anb_1Array, atu_0[] atu_0Array, List list) {
        super(lc_02, (s & 8) != 0);
        int n2;
        this.sF = string;
        this.HC = s;
        this.HD = atu_02;
        this.HD.a(this);
        this.name = string2;
        this.azz = anb_1Array;
        for (n2 = 0; n2 < anb_1Array.length; ++n2) {
            anb_1Array[n2].HD.a(this);
        }
        this.azA = atu_0Array;
        for (n2 = 0; n2 < atu_0Array.length; ++n2) {
            atu_0Array[n2].a(this);
        }
        this.azB = list;
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                TK tK = (TK)iterator.next();
                if (("<init>".equals(string2) || "<clinit>".equals(string2)) && tK.Dw() != null) continue;
                tK.a(this);
            }
        }
    }

    public aim_2 Dw() {
        return this.bV();
    }

    public String jv() {
        return this.sF;
    }

    public boolean jw() {
        return this.sF != null && this.sF.indexOf("@deprecated") != -1;
    }
}

