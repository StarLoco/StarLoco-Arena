/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ajT
 */
public class ajt_1
implements aho_0 {
    private jg_0 cCq = new jg_0();
    private int cCr = 0;
    private int cCs = 0;
    private abe_1 cCt = null;
    private boolean cCu = false;
    private String cCv = aon_0.aYc().getString("fusionHelp");
    public static final String aMk = "localCardExchange";
    public static final String cCw = "fusionCard";
    public static final String cCx = "localCardsPrice";
    public static final String cCy = "resultCard";
    public static final String cCz = "slotCount";
    public static final String cCA = "labPower";
    public static final String cCB = "kardsPower";
    public static final String cCC = "quality";
    public static final String cCD = "canFusion";
    public static final String cCE = "fusionFailed";
    public static final String cCF = "help";
    public static final String[] ce = new String[]{"localCardExchange", "fusionCard", "localCardsPrice", "resultCard", "slotCount", "labPower", "kardsPower", "quality", "canFusion", "fusionFailed", "help"};

    public ajt_1(abe_1 abe_12) {
        this.cCt = abe_12;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(aMk)) {
            xj[] xjArray = new xj[this.cCq.size()];
            for (int j = 0; j < this.cCq.size(); ++j) {
                xjArray[j] = (xj)la_0.XJ().pj(this.cCq.get(j));
            }
            return xjArray;
        }
        if (string.equals(cCw)) {
            return la_0.XJ().pj(this.cCr);
        }
        if (string.equals(cCx)) {
            int n2 = 0;
            for (int j = 0; j < this.cCq.size(); ++j) {
                n2 += ((xj)la_0.XJ().pj(this.cCq.get(j))).getValue();
            }
            return n2;
        }
        if (string.equals(cCy)) {
            return la_0.XJ().pj(this.azu());
        }
        if (string.equals(cCz)) {
            return this.cCt.azi() - 1;
        }
        if (string.equals(cCA)) {
            return this.cCt.tz();
        }
        if (string.equals(cCB)) {
            int n3 = 0;
            for (int j = 0; j < this.cCq.size(); ++j) {
                xj xj2 = (xj)la_0.XJ().pj(this.cCq.get(j));
                n3 += xj2.tr();
            }
            if (this.cCr != 0) {
                n3 -= ((xj)la_0.XJ().pj(this.cCr)).tz();
            }
            return n3;
        }
        if (string.equals(cCC)) {
            return this.cCt.tA();
        }
        if (string.equals(cCD)) {
            return this.cCq.size() >= 2;
        }
        if (string.equals(cCE)) {
            return this.cCu;
        }
        if (string.equals(cCF)) {
            return this.cCv;
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

    public int azu() {
        return this.cCs;
    }

    public void lc(int n2) {
        this.cCs = n2;
    }

    public void ld(int n2) {
        this.cCq.add(n2);
    }

    public void le(int n2) {
        this.cCq.bv(this.cCq.indexOf(n2));
    }

    public void lf(int n2) {
        this.cCr = n2;
    }

    public int azv() {
        return this.cCr;
    }

    public void clear() {
        this.cCq.clear();
        this.cCr = 0;
    }

    public jg_0 azw() {
        return this.cCq;
    }

    public abe_1 azx() {
        return this.cCt;
    }

    public void dE(boolean bl2) {
        this.cCu = bl2;
    }

    public void iu(String string) {
        this.cCv = string;
    }
}

