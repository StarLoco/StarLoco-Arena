/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from vK
 */
public class vk_1
implements aho_0 {
    public static final String atA = "tournamentsOfTheDay";
    public static final String atB = "tournamentsList";
    public static final String atC = "tournamentDefinitionsAvailable";
    public static final String atD = "proLeagueDefinitions";
    public static final String[] ce = new String[]{"tournamentsOfTheDay", "tournamentsList", "tournamentDefinitionsAvailable", "proLeagueDefinitions"};
    private static final Logger a = Logger.getLogger(vk_1.class);
    public static final long so = 0L;
    private cp_2 atE = new cp_2();
    private lb_0 atF = new lb_0();
    private static long lc = 0L;
    private static long Ho = 0L;
    private static short Gm = (short)-1;
    public static final vk_1 atG = new vk_1();

    private vk_1() {
        azs_0.aLV().g("tournamentsManager", this);
    }

    public static vk_1 BZ() {
        return atG;
    }

    public cp_2 Ca() {
        return this.atE;
    }

    public vg aQ(long l2) {
        return (vg)this.atE.t(l2);
    }

    public lb_0 Cb() {
        return this.atF;
    }

    public static long fx() {
        return lc;
    }

    public static long qX() {
        return Ho;
    }

    public static short qY() {
        return Gm;
    }

    public void a(wy_1 wy_12) {
        for (int j = 0; j < wy_12.getSize(); ++j) {
            vg vg2 = new vg(wy_12.iy(j), wy_12.iz(j), wy_12.iA(j), wy_12.iB(j), wy_12.iC(j), wy_12.iD(j), wy_12.iE(j), wy_12.iF(j), wy_12.iG(j), wy_12.iH(j));
            this.atE.a(vg2.fx(), vg2);
            if (vg2.Bv() == -128 || vg2.Bu() == 0) continue;
            boolean bl2 = false;
            for (aan_1 aan_12 : iz_1.Vg().Vh()) {
                if (!(aan_12 instanceof td_0) || ((td_0)aan_12).fx() != vg2.fx()) continue;
                bl2 = true;
                break;
            }
            if (!bl2) {
                td_0 td_02 = new td_0(vg2.BC());
                td_02.ad(vg2.fx());
                iz_1.Vg().b(td_02);
                continue;
            }
            a.error((Object)("on essaye d'ajouter une info de tournoi qui est d\u00e9j\u00e0 pr\u00e9sente" + new Exception()));
        }
        azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
    }

    public static void ad(long l2) {
        lc = l2;
    }

    public static void aj(long l2) {
        Ho = l2;
    }

    public static void C(short s) {
        Gm = s;
    }

    public void Cc() {
        this.atE.clear();
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(atA)) {
            return this.Cd().toArray();
        }
        if (string.equals(atB)) {
            return this.atE.getValues();
        }
        if (string.equals(atC)) {
            int[] nArray = LS.Yf().Yg().pL();
            ArrayList<Integer> arrayList = new ArrayList<Integer>(nArray.length);
            for (int j = 0; j < nArray.length; ++j) {
                arrayList.add(nArray[j]);
            }
            return arrayList;
        }
        if (string.equals(atD)) {
            return this.atF.getValues();
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    public ArrayList Cd() {
        rd_1 rd_12 = rd_1.aF(System.currentTimeMillis());
        sv_1 sv_12 = (sv_1)de_2.Mc().a(rd_12, new rd_1(rd_12).a(59 - rd_12.getSeconds(), 59 - rd_12.getMinutes(), 23 - rd_12.getHours(), 0, 0, 0)).get(0);
        ArrayList<qr_0> arrayList = new ArrayList<qr_0>();
        for (int j = 0; j < sv_12.size(); ++j) {
            iz_0 iz_02 = (iz_0)sv_12.get(j);
            if (iz_02.getType() != 4) continue;
            arrayList.add((qr_0)iz_02);
        }
        return arrayList;
    }
}

