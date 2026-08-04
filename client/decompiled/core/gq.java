/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

public class gq
implements Comparator {
    public static gq sR = new gq();

    public int a(xb_2 xb_22, xb_2 xb_23) {
        int n2 = this.a(xb_22.aex(), xb_23.aex());
        if (n2 != 0) {
            return n2;
        }
        if (xb_22.Np() instanceof yp_2 && xb_23.Np() instanceof yp_2) {
            if (((yp_2)((Object)xb_22.Np())).getId() != ((yp_2)((Object)xb_23.Np())).getId()) {
                return Integer.signum(((yp_2)((Object)xb_22.Np())).getId() - ((yp_2)((Object)xb_23.Np())).getId());
            }
            if (xb_22.getId() != xb_23.getId()) {
                return Integer.signum(xb_22.getId() - xb_23.getId());
            }
        }
        return -1;
    }

    public int a(akv_0 akv_02, akv_0 akv_03) {
        if (akv_02 == akv_03 || akv_02.aVD() && akv_03.aVD()) {
            return 0;
        }
        if (akv_02.aVD()) {
            return 1;
        }
        if (akv_03.aVD()) {
            return -1;
        }
        return Integer.signum(akv_02.aVC() - akv_03.aVC());
    }
}

