/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from wy
 */
public class wy_2
extends eb_1
implements aho_0,
Comparable {
    public static final String auH = "uniqueId";
    public static final String asT = "quantity";
    public static final String auI = "globalQuantity";
    public static final String auJ = "tomeStyle";
    public static final String auK = "barrierStyle";
    public static final String[] ce = new String[]{"id", "name", "set", "description", "halfSetEffects", "fullSetEffects", "isHalfComplete", "hasHalfSetEffect", "isComplete", "hasFullSetEffect", "iconUrl", "illustrationUrl", "typeIconUrl", "cardSetName", "cardSetSize", "cardIndexInSet", "requiredLevel", "cardType", "value", "tokenValue", "isInTome", "consumableTypeIcon", "rarity", "uniqueId", "quantity", "globalQuantity", "tomeStyle", "barrierStyle"};

    public wy_2() {
        super(la_0.XJ());
    }

    public wy_2(int n2) {
        this();
        this.aRL = this.aRM.pj(n2);
        this.aFL = 0L;
    }

    public void aS(long l2) {
        this.aFL = l2;
    }

    public String getName() {
        return ((xj)this.NR()).getName();
    }

    public aMK tj() {
        return ((xj)this.NR()).tj();
    }

    public String getDescription() {
        return ((xj)this.NR()).getDescription();
    }

    public String CM() {
        return ((xj)this.NR()).CM();
    }

    public int CN() {
        if (((xj)this.NR()).tn() != null) {
            return ((xj)this.NR()).tn().size();
        }
        return 0;
    }

    public int CO() {
        if (((xj)this.NR()).tn() != null) {
            return ((xj)this.NR()).tn().c(this.NR()) + 1;
        }
        return 0;
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        if (string.equals(auH)) {
            return this.je();
        }
        if (string.equals(asT)) {
            return this.hG();
        }
        if (string.equals(auI)) {
            wy_2 wy_22;
            int n2 = 0;
            ky_2 ky_22 = apN.aDK().Ln().yD();
            if (ky_22.bU(Math.abs(this.jf()))) {
                n2 += this.hG();
            }
            if ((wy_22 = (wy_2)ky_22.pI().ac(-Math.abs(this.jf()))) != null) {
                n2 += wy_22.hG();
            }
            return n2;
        }
        if (string.equals(auJ)) {
            wy_2 wy_23;
            sj_1 sj_12 = apN.aDK().Ln();
            ky_2 ky_23 = sj_12.yD();
            asc asc2 = sj_12.aQm();
            int n3 = this.jf();
            int n4 = 0;
            if (ky_23.bU(Math.abs(this.jf()))) {
                n4 += this.hG();
            }
            if ((wy_23 = (wy_2)ky_23.pI().ac(-Math.abs(this.jf()))) != null) {
                n4 += wy_23.hG();
            }
            if (asc2.contains(n3)) {
                if (n4 > 0) {
                    return "coachCardAura";
                }
                return "coachCardShadow";
            }
            if (n4 > 0) {
                return "";
            }
            return "BackZaapCoachCard";
        }
        if (string.equals(auK)) {
            wy_2 wy_24;
            int n5 = 0;
            ky_2 ky_24 = apN.aDK().Ln().yD();
            wy_2 wy_25 = (wy_2)ky_24.pI().ac(Math.abs(this.jf()));
            if (ky_24.bU(Math.abs(this.jf()))) {
                n5 += wy_25.hG();
            }
            if ((wy_24 = (wy_2)ky_24.pI().ac(-Math.abs(this.jf()))) != null) {
                n5 += wy_24.hG();
            }
            if (n5 > 0) {
                return "";
            }
            return "BackZaapCoachCard";
        }
        return ((xj)this.NR()).getFieldValue(string);
    }

    public String[] getFields() {
        return ce;
    }

    public boolean l(String string) {
        return false;
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
    }

    public void release() {
    }

    public void cF(String string) {
        azs_0.aLV().a((aho_0)this, string);
    }

    public void CP() {
        azs_0.aLV().a((aho_0)this, ce);
    }

    public wy_2 aQ(boolean bl2) {
        wy_2 wy_22 = new wy_2();
        wy_22.aRL = this.aRL;
        wy_22.q(this.hG());
        wy_22.aFL = uq_1.ahR();
        return wy_22;
    }

    public wy_2 CQ() {
        wy_2 wy_22 = new wy_2();
        wy_22.aRL = this.aRL;
        wy_22.q(this.hG());
        wy_22.aFL = this.aFL;
        return wy_22;
    }

    public int Bp() {
        return 13;
    }

    public int h(wy_2 wy_22) {
        return this.getName().compareTo(wy_22.getName());
    }

    public String toString() {
        return this.getName();
    }
}

