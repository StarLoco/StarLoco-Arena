/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from VM
 */
public class vm_0
extends lp_1 {
    private static final String aKC = "FighterCardUseZoneEffect";
    private static final String aKD = "FighterCardUseRange";
    private static final String aKE = "FighterCardUseRangeWithConstraint";
    private jb_2 bTf;

    public vm_0() {
        super(aKD, mx_0.Kv, aKC, mx_0.Ku, aKE, mx_0.Kw);
    }

    public void a(jb_2 jb_22, ee_2 ee_22) {
        this.bTf = jb_22;
        this.d(ee_22);
        this.bTf = null;
    }

    protected YT l(ry ry2) {
        switch (this.bsq.a((gn_0)this.bN, this.bTf, ry2)) {
            case aUd: {
                return YT.cbK;
            }
        }
        return YT.cbN;
    }
}

