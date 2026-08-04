/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from adD
 */
public class add_0
implements atG {
    protected static final Logger a = Logger.getLogger(add_0.class);
    private static add_0 cne = new add_0();

    public static add_0 atb() {
        return cne;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 2401: {
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 == null) {
                    a.error((Object)"Impossible de sauvegerde les statistiques de coach si aucun coach local n'est d\u00e9fini !");
                } else {
                    sj_12.a(((uf_0)pr_02).AK());
                }
                return false;
            }
            case 2411: {
                HJ hJ = (HJ)pr_02;
                for (int j = hJ.Tt().size() - 1; 0 <= j; --j) {
                    zK zK2 = bs_0.IF().at(hJ.Tt().cg(j));
                    if (zK2 == sw_1.bMq) continue;
                    zK2.M(hJ.Tu().cg(j));
                    zK2.bj(hJ.Tv().cg(j));
                    zK2.hM(hJ.Tw().bu(j));
                    zK2.hN(hJ.Tx().bu(j));
                    zK2.hO(hJ.Ty().bu(j));
                }
                azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset1vs1List");
                azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset2vs2List");
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

