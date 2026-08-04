/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from acR
 */
public class acr_2
extends hx {
    private boolean clc;
    private byte cld;

    public acr_2(boolean bl2, byte by) {
        this.clc = bl2;
        this.cld = by;
    }

    public int a(Object object, Object object2, Object object3, Object object4) {
        if (object == null) {
            return -1;
        }
        if (!(object instanceof gn_0)) {
            return -1;
        }
        gn_0 gn_02 = (gn_0)object;
        if (this.clc == gn_02.b(avx_0.a(this.cld))) {
            return 0;
        }
        return -2;
    }
}

