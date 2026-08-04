/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aog
 */
public class aog_0
implements zc_2 {
    public static final int OK = 0;
    public static final int cKG = 1;
    private gn_0 bzf;

    protected aog_0(gn_0 gn_02) {
        this.bzf = gn_02;
    }

    public int a(mi_2 mi_22, fv fv2) {
        return 0;
    }

    public int a(mi_2 mi_22, fv fv2, short s) {
        return 0;
    }

    public int a(mi_2 mi_22, fv fv2, int n2) {
        throw new UnsupportedOperationException("Pas de position en Int pour cet inventaire, Utiliser une position en short");
    }

    public int a(mi_2 mi_22, fv fv2, fv fv3) {
        if (fv2.iQ() != fv3.iQ()) {
            return 1;
        }
        return 0;
    }

    public int b(mi_2 mi_22, fv fv2) {
        return 0;
    }

    public boolean a(fv fv2, kc_2 kc_22, ea_0 ea_02) {
        return true;
    }

    public boolean a(mi_2 mi_22, kc_2 kc_22, ea_0 ea_02) {
        return true;
    }
}

