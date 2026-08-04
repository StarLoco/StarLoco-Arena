/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class aJd
implements aho_0 {
    public static final byte aMi = 6;
    public static final String aMj = "exchangeId";
    public static final String aMk = "localCardExchange";
    public static final String dRc = "cardMasterCardExchange";
    public static final String aMm = "localCardCount";
    public static final String bEv = "localCardsPrice";
    public static final String dRd = "cardMasterCardsPrice";
    public static final String dRe = "selectedCard";
    public static final String bEx = "canBuyCards";
    public static final String[] ce = new String[]{"localCardExchange", "cardMasterCardExchange", "localCardCount", "localCardsPrice", "cardMasterCardsPrice", "canBuyCards", "selectedCard"};
    private static final int[] bEy = new int[1];
    private lb_0 dRf = new lb_0();
    private lb_0 bAx = new lb_0();
    private nn_0 dRg = null;
    private int btt;

    public String[] getFields() {
        return ce;
    }

    public aJd(lb_0 lb_02, int n2) {
        this.bAx = lb_02;
        this.btt = n2;
    }

    public Object getFieldValue(String string) {
        if (string.equals(aMk)) {
            int[] nArray = this.dRf.pL();
            ArrayList<wy_2> arrayList = new ArrayList<wy_2>();
            for (int j = 0; j < nArray.length; ++j) {
                wy_2 wy_22 = new wy_2(nArray[j]);
                wy_22.q((Short)this.dRf.get(nArray[j]));
                arrayList.add(wy_22);
            }
            return arrayList.toArray();
        }
        if (string.equals(dRc)) {
            ArrayList arrayList = new ArrayList(this.bAx.size());
            this.bAx.a(new mv(this, arrayList));
            return arrayList.toArray();
        }
        if (string.equals(aMm)) {
            return this.dRf.size();
        }
        if (string.equals(bEv)) {
            return this.acz();
        }
        if (string.equals(dRd)) {
            return this.aVo();
        }
        if (string.equals(bEx)) {
            return this.aVo() > 0 && this.acz() - this.aVo() >= 0;
        }
        if (string.equals(dRe)) {
            return this.dRg;
        }
        return null;
    }

    public void k(int n2, short s) {
        short s2 = 0;
        if (this.dRf.bY(n2)) {
            s2 = (Short)this.dRf.get(n2);
        }
        this.dRf.c(n2, (short)(s2 + s));
    }

    public void oP(int n2) {
        if (this.dRf.bY(n2)) {
            short s = (Short)this.dRf.get(n2);
            if (s == 1) {
                this.dRf.remove(n2);
            } else {
                this.dRf.c(n2, (short)(s - 1));
            }
        }
    }

    private int acz() {
        aJd.bEy[0] = 0;
        int[] nArray = this.dRf.pL();
        for (int j = 0; j < nArray.length; ++j) {
            bEy[0] = bEy[0] + ((xj)la_0.XJ().pj(nArray[j])).getValue() * (Short)this.dRf.get(nArray[j]);
        }
        return bEy[0];
    }

    private int aVo() {
        if (this.dRg != null) {
            return vt_2.it(((xj)this.dRg.NR()).getValue());
        }
        return 0;
    }

    public void b(nn_0 nn_02) {
        this.dRg = nn_02;
        azs_0.aLV().a((aho_0)this, dRe);
    }

    public int aaW() {
        return this.btt;
    }

    public nn_0 aVp() {
        return this.dRg;
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

    public boolean acB() {
        return this.dRf == null || this.dRf.size() < 6;
    }
}

