/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from atp
 */
public class atp_0
implements hR {
    private static final atp_0 cTO = new atp_0();
    private static final ahm_1 cTP = new ahm_1();

    public static atp_0 aGx() {
        return cTO;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.fighterCondition");
    }

    public void a(mk_1 mk_12) {
        lJ[] lJArray;
        for (lJ lJ2 : lJArray = aly_1.aAQ().a(cTP)) {
            ahm_1 ahm_12 = (ahm_1)lJ2;
            ArrayList<xj_0> arrayList = new ArrayList<xj_0>();
            for (Ht ht : ahm_12.eC()) {
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
            bf_1.df().a(new aiz_2(ahm_12.aUf(), ahm_12.ayW(), ahm_12.getType(), ahm_12.ayX(), arrayList));
        }
        mk_12.b(this);
    }
}

