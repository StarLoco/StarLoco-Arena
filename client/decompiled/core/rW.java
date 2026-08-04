/*
 * Decompiled with CFR 0.152.
 */
public class rW
extends hx {
    private boolean aiG;

    public rW(boolean bl2) {
        this.aiG = bl2;
    }

    public int a(Object object, Object object2, Object object3, Object object4) {
        if (object == null) {
            return -1;
        }
        if (!(object instanceof gn_0)) {
            return -1;
        }
        gn_0 gn_02 = (gn_0)object;
        gn_0 gn_03 = ((gn_0)object).PY();
        if (gn_02.Qa() && this.aiG == (gn_03.PH() == gn_02.PH())) {
            return 0;
        }
        return -2;
    }
}

