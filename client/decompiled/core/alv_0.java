/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aLV
 */
public class alv_0
implements atG {
    protected static final Logger a = Logger.getLogger(alv_0.class);
    private static alv_0 dXi = new alv_0();

    public static alv_0 aWM() {
        return dXi;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 17003: {
                awa_0 awa_02 = (awa_0)pr_02;
                de_2.Mc().clear();
                for (int j = 0; j < awa_02.Zz().size(); ++j) {
                    de_2.Mc().a((iz_0)awa_02.Zz().get(j));
                }
                apN.aDK().a(zb_1.GG());
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

