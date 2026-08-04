/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNv
 */
public class anv_2
extends hx {
    private boolean dZp;

    public anv_2(boolean bl2) {
        this.dZp = bl2;
    }

    public int a(Object object, Object object2, Object object3, Object object4) {
        if (object == null) {
            return -1;
        }
        if (!(object instanceof gn_0)) {
            return -1;
        }
        gn_0 gn_02 = (gn_0)object;
        if (this.dZp == gn_02.Qa()) {
            return 0;
        }
        return -2;
    }
}

