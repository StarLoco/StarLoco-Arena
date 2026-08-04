/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from ahy
 */
public class ahy_1
implements aho_0 {
    public static final int cvH = 12;
    public static final String cvI = "challengesList";
    public static final String[] ce = new String[]{"challengesList"};
    public static ahy_1 cvJ = new ahy_1();
    private cp_2 cvK = new cp_2();
    private long[] cvL;

    public static ahy_1 axg() {
        return cvJ;
    }

    public ahy_1() {
        azs_0.aLV().g("challengeManager", this);
    }

    public void a(GE gE) {
        int n2 = gE.Qs();
        this.cvK.a(n2, new afz_0(n2, aon_0.aYc().a(30, n2, new Object[0]), aon_0.aYc().a(31, n2, new Object[0]), gE.Qu(), gE.QA(), gE.QB(), gE.QC(), gE.QD(), gE.QE()));
    }

    public afz_0 dC(long l2) {
        return (afz_0)this.cvK.t(l2);
    }

    public int dD(long l2) {
        return ((afz_0)this.cvK.t(l2)).QC();
    }

    public void k(long[] lArray) {
        this.cvL = lArray;
        azs_0.aLV().g("selectedChallenge", this.cvK.t(lArray[0]));
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(cvI) && this.cvL != null) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (int j = 0; j < this.cvL.length; ++j) {
                arrayList.add(this.cvK.t(this.cvL[j]));
            }
            return arrayList.toArray();
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
}

