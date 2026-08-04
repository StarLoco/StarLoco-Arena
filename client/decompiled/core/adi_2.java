/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from adi
 */
public class adi_2
implements atG {
    private static final Logger a = Logger.getLogger(adi_2.class);
    private static final adi_2 cmk = new adi_2();

    public static adi_2 ash() {
        return cmk;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            pm_0.ur().bD(true).m(aon_0.aYc().getString("loading"), 0);
            apN.aDK().vJ().b(new ys_1());
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 6006: {
                jt_2 jt_22 = (jt_2)pr_02;
                cp_2 cp_22 = jt_22.Wl();
                if (!cp_22.isEmpty()) {
                    long l2 = apN.aDK().Ln().getId();
                    cp_22.a(new pj_0(this, jt_22, l2));
                    azs_0.aLV().a((aho_0)xz_0.amc(), xz_0.oT);
                }
                return false;
            }
            case 6030: {
                ar_0 ar_02 = (ar_0)pr_02;
                pm_0.ur().done();
                ArrayList arrayList = ar_02.Hh();
                for (int j = 0; j < arrayList.size(); ++j) {
                    if (((zK)arrayList.get(j)).getType() != -4) continue;
                    xz_0.amc().b((sw_1)arrayList.get(j));
                }
                add_1.aOG().a("graveyardDialog", oh_2.bq("graveyardDialog"), (short)10000);
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
}

