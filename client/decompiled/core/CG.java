/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class CG
extends nk
implements aho_0 {
    public static final byte aMi = 5;
    public static final String aMj = "exchangeId";
    public static final String aMk = "localCardExchange";
    public static final String aMl = "remoteCardExchange";
    public static final String aMm = "localCardCount";
    public static final String aMn = "remoteCardCount";
    public static final String aMo = "localCardsValue";
    public static final String aMp = "remoteCardsValue";
    public static final String aMq = "remoteUserReady";
    public static final String aMr = "localUserReady";
    public static final String aMs = "readyButtonEnabled";
    public static final String[] ce = new String[]{"exchangeId", "localCardExchange", "remoteCardExchange", "localCardCount", "remoteCardCount", "localCardsValue", "remoteCardsValue", "localUserReady", "remoteUserReady", "readyButtonEnabled"};
    private r_0 aMt = null;
    private boolean aMu;
    private boolean aMv = true;

    public CG(adq_2 adq_22, adq_2 adq_23, boolean bl2) {
        super(adq_22.getId());
        this.a(adq_22, adq_23);
        this.aMu = bl2;
    }

    public void a(r_0 r_02) {
        this.aMt = r_02;
    }

    public r_0 Kw() {
        return this.aMt;
    }

    public boolean Kx() {
        return this.aMu;
    }

    protected boolean sg() {
        return true;
    }

    protected boolean sh() {
        return true;
    }

    protected boolean sj() {
        return true;
    }

    protected boolean si() {
        return false;
    }

    protected void sk() {
        ky_2 ky_22;
        sj_1 sj_12 = apN.aDK().Ln();
        byte by = this.h(sj_12);
        if (this.Of[0] != null) {
            for (wy_2 wy_22 : this.Of[0].values()) {
                if (by == 0) {
                    ky_22 = sj_12.aQn();
                    ky_22.f(((wy_2)ky_22.bW(wy_22.jf())).je(), -wy_22.hG());
                    continue;
                }
                try {
                    sj_12.aQn().f(wy_22.CQ());
                }
                catch (xR xR2) {
                    xR2.printStackTrace();
                }
                catch (gg gg2) {
                    gg2.printStackTrace();
                }
            }
        }
        if (this.Of[1] != null) {
            for (wy_2 wy_22 : this.Of[1].values()) {
                if (by == 1) {
                    ky_22 = sj_12.aQn();
                    ky_22.f(((wy_2)ky_22.bW(wy_22.jf())).je(), -wy_22.hG());
                    continue;
                }
                try {
                    sj_12.aQn().f(wy_22.CQ());
                }
                catch (xR xR3) {
                    xR3.printStackTrace();
                }
                catch (gg gg3) {
                    gg3.printStackTrace();
                }
            }
        }
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        int n2;
        if (string.equals(aMj)) {
            return this.getId();
        }
        if (string.equals(aMr)) {
            int n3 = this.aMu ? 0 : 1;
            return this.Oe[n3];
        }
        if (string.equals(aMq)) {
            int n4 = this.aMu ? 1 : 0;
            return this.Oe[n4];
        }
        if (string.equals(aMk)) {
            int n5 = n2 = this.aMu ? 0 : 1;
            if (this.Of[n2] != null) {
                ArrayList<wy_2> arrayList = new ArrayList<wy_2>();
                for (wy_2 wy_22 : this.Of[n2].values()) {
                    if (wy_22.hG() <= 0) continue;
                    arrayList.add(wy_22);
                }
                return arrayList.toArray();
            }
        }
        if (string.equals(aMl)) {
            int n6 = n2 = this.aMu ? 1 : 0;
            if (this.Of[n2] != null) {
                ArrayList<wy_2> arrayList = new ArrayList<wy_2>();
                for (wy_2 wy_23 : this.Of[n2].values()) {
                    if (wy_23.hG() <= 0) continue;
                    arrayList.add(wy_23);
                }
                return arrayList.toArray();
            }
        }
        if (string.equals(aMm)) {
            n2 = this.aMu ? 0 : 1;
            int n7 = 0;
            if (this.Of[n2] != null) {
                for (wy_2 wy_24 : this.Of[n2].values()) {
                    ++n7;
                }
            }
            return n7;
        }
        if (string.equals(aMn)) {
            n2 = this.aMu ? 1 : 0;
            int n8 = 0;
            if (this.Of[n2] != null) {
                for (wy_2 wy_25 : this.Of[n2].values()) {
                    ++n8;
                }
            }
            return n8;
        }
        if (string.equals(aMo)) {
            int n9;
            n2 = 0;
            int n10 = n9 = this.aMu ? 0 : 1;
            if (this.Of[n9] != null) {
                for (wy_2 wy_26 : this.Of[n9].values()) {
                    n2 += ((xj)wy_26.NR()).getValue() * wy_26.hG();
                }
            }
            return n2;
        }
        if (string.equals(aMp)) {
            int n11;
            n2 = 0;
            int n12 = n11 = this.aMu ? 1 : 0;
            if (this.Of[n11] != null) {
                for (wy_2 wy_27 : this.Of[n11].values()) {
                    n2 += ((xj)wy_27.NR()).getValue() * wy_27.hG();
                }
            }
            return n2;
        }
        if (string.equals(aMs)) {
            return this.aMv;
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

    public void Ky() {
        azs_0.aLV().a((aho_0)this, aMk);
        azs_0.aLV().a((aho_0)this, aMm);
        azs_0.aLV().a((aho_0)this, aMo);
    }

    public void Kz() {
        azs_0.aLV().a((aho_0)this, aMl);
        azs_0.aLV().a((aho_0)this, aMn);
        azs_0.aLV().a((aho_0)this, aMp);
    }

    public void KA() {
        azs_0.aLV().a((aho_0)this, aMr);
    }

    public void KB() {
        azs_0.aLV().a((aho_0)this, aMq);
    }

    public void bd(boolean bl2) {
        this.aMv = bl2;
    }

    public boolean bw(long l2) {
        int n2 = this.aMu ? 0 : 1;
        return this.Of[n2] == null || this.Of[n2].containsKey(l2) || this.Of[n2].keySet().size() < 5;
    }
}

