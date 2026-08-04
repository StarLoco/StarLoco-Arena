/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ne
 */
public class ne_2
implements zc_2 {
    public static final int OK = 0;
    public static final int bzd = 1;
    public static final int bze = 2;
    private gn_0 bzf;

    protected ne_2(gn_0 gn_02) {
        this.bzf = gn_02;
    }

    public int a(mi_2 mi_22, jb_2 jb_22) {
        return 1;
    }

    public int a(mi_2 mi_22, jb_2 jb_22, short s) {
        if (jb_22.Vk().aiJ() != s) {
            return 2;
        }
        return 0;
    }

    public int a(mi_2 mi_22, jb_2 jb_22, int n2) {
        throw new UnsupportedOperationException("Pas de position en Int pour cet inventaire, Utiliser une position en short");
    }

    public int a(mi_2 mi_22, jb_2 jb_22, jb_2 jb_23) {
        if (jb_22.Vk() != jb_23.Vk()) {
            return 2;
        }
        return 0;
    }

    public int b(mi_2 mi_22, jb_2 jb_22) {
        return 0;
    }

    public boolean a(jb_2 jb_22, kc_2 kc_22, ea_0 ea_02) {
        return true;
    }

    public boolean a(mi_2 mi_22, kc_2 kc_22, ea_0 ea_02) {
        return true;
    }
}

