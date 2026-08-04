/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from wg
 */
public class wg_2
implements atG {
    protected static final Logger a = Logger.getLogger(wg_2.class);
    private static wg_2 auk = new wg_2();

    public static wg_2 CC() {
        return auk;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 26304: {
                gz_0 gz_02 = (gz_0)pr_02;
                pw_1.acG().cr(gz_02.Y());
                apN.aDK().a(ft_1.jr());
                apN.aDK().b(do_2.Mm());
                apN.aDK().b(B.V());
                apN.aDK().b(this);
                add_1.aOG().aPe();
                add_1.aOG().a(aon_0.aYc().getString("error.fight.creation.canceledByOpponent"), 1090L, 102, 1);
                return false;
            }
            case 26310: {
                nx_1 nx_12 = (nx_1)pr_02;
                apN.aDK().a(ft_1.jr());
                apN.aDK().b(do_2.Mm());
                apN.aDK().b(B.V());
                apN.aDK().b(this);
                zN.M(nx_12.aaE());
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
        if (!bl2) {
            apN.aDK().b(hu_2.li());
            apN.aDK().b(agn_0.awo());
            apN.aDK().b(hm_1.Tz());
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            pm_0.ur().done();
            azs_0.aLV().kb("fight.id");
            azs_0.aLV().kb("fight.budget");
        }
    }
}

