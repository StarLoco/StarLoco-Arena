/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Vj
 */
public class vj_2
implements hR {
    private static final vj_2 bSk = new vj_2();
    private static final aeI bSl = new aeI();

    public static vj_2 aii() {
        return bSk;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.sphereBoard");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(bSl)) {
            aeI aeI2 = (aeI)lJ2;
            ArrayList<xj_0> arrayList = new ArrayList<xj_0>();
            for (Ht ht : aeI2.eC()) {
                agf_2 agf_22;
                aih_1 aih_12;
                aai_1 aai_12 = mh_2.YJ().cq(ht.M());
                if (aai_12 == null || !(aih_12 = (aih_1)aai_12).oN(ht.Tb().length)) continue;
                try {
                    agf_22 = zg_1.a(ht.SV(), ht.Tg(), ht.SW());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    agf_22 = null;
                }
                if (agf_22 == null) continue;
                long l2 = 0L;
                if (ht.isCritical()) {
                    l2 |= 1L;
                }
                xj_0 xj_02 = new xj_0(ht.ST(), ht.M(), ht.SU(), agf_22, ht.Tc(), ht.Td(), ht.Te(), ht.Tf(), l2, new aLc(ht.Ti()), ht.SX(), ht.Tb(), ht.Th(), ht.SY(), ht.SZ(), ht.Ta(), ht.Tj(), ht.Tk());
                arrayList.add(xj_02);
            }
            ayr_0 ayr_02 = new ayr_0(aeI2.getId(), aeI2.aus(), aeI2.aut(), aeI2.auu(), aeI2.el(), arrayList, aeI2.auv(), aeI2.cv(), aeI2.auw(), aeI2.aux(), aeI2.auy());
            akp_1.aVO().a(aeI2.NH(), ayr_02);
        }
        mk_12.b(this);
    }
}

