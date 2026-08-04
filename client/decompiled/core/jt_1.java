/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from Jt
 */
public class jt_1
extends aj_1 {
    public final List blm;
    public final boolean bln;
    public final List blo;

    public jt_1(lc_0 lc_02, List list, boolean bl2, List list2) {
        super(lc_02);
        this.blm = list;
        this.bln = bl2;
        this.blo = list2;
    }

    public String toString() {
        return this.blm.size() + (this.bln ? " case label(s) plus DEFAULT" : " case label(s)");
    }
}

