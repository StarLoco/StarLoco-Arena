/*
 * Decompiled with CFR 0.152.
 */
import java.net.URL;
import java.util.List;

/*
 * Renamed from Yp
 */
public class yp_2
extends fv
implements aho_0,
Comparable {
    public static final String arR = "id";
    public static final String xX = "name";
    public static final String aog = "description";
    public static final String arS = "backgroundDescription";
    public static final String aoh = "iconUrl";
    public static final String aoi = "illustrationUrl";
    public static final String aoj = "cardType";
    public static final String Oj = "value";
    public static final String arV = "actionPoints";
    public static final String arY = "range";
    public static final String arW = "aoe";
    public static final String cax = "aoeSize";
    public static final String arZ = "duration";
    public static final String arX = "target";
    public static final String cay = "cooldown";
    public static final String[] ce = new String[]{"id", "name", "description", "backgroundDescription", "iconUrl", "illustrationUrl", "cardType", "value", "actionPoints", "range", "aoe", "aoeSize", "duration", "target", "cooldown"};
    private final int it;
    private String fM = null;
    private String asb = null;
    private final boolean bJf;

    public yp_2(int n2, int n3, byte by, byte by2, byte by3, byte by4, byte by5, byte by6, boolean bl2, boolean bl3, byte by7, byte by8, int n4, int n5, boolean bl4, int n6, List list, boolean bl5, boolean bl6, long[] lArray, boolean bl7, fv fv2) {
        super(n2, n3, by, by2, by3, by4, by5, by6, bl2, bl3, by7, by8, n4, n5, bl4, list, bl6, lArray, bl7, fv2);
        this.it = n6;
        this.bJf = bl5;
    }

    public String getName() {
        return aon_0.aYc().a(3, this.getId(), new Object[0]);
    }

    public void setDescription(String string) {
        this.fM = string;
    }

    public void cA(String string) {
        this.asb = string;
    }

    public String getDescription() {
        if (this.fM == null) {
            this.setDescription(asf_0.a(this.getId(), this.adW(), this.iK(), null, this.iY(), this.iZ(), false, this.iX(), this.ja(), this.iS(), this.iU(), 20, 4));
        }
        return this.fM;
    }

    public String Bn() {
        if (this.asb == null) {
            this.cA(asf_0.bU(this.getId(), 4));
        }
        return this.asb;
    }

    public int eA() {
        return this.it;
    }

    public boolean amu() {
        for (long l2 : this.rs) {
            if ((l2 & 1L) != 0L) {
                return false;
            }
            if ((l2 & 0xFFF07BEL) == 0L) continue;
            return true;
        }
        return false;
    }

    public boolean adW() {
        return this.bJf;
    }

    public URL amv() {
        try {
            return new URL(String.format(mu_1.rM().getString("spellsIconsPath"), this.getId()));
        }
        catch (Exception exception) {
            return null;
        }
    }

    public int getValue() {
        je_2 je_22 = jk_1.mf().mg();
        int n2 = je_22.mZ().get(this.getId());
        return super.getValue() + n2;
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        Object object;
        if (string.equals(arR)) {
            return this.getId();
        }
        if (string.equals(xX)) {
            return this.getName();
        }
        if (string.equals(aog)) {
            return this.getDescription();
        }
        if (string.equals(arS)) {
            return this.Bn();
        }
        if (string.equals(aoh)) {
            try {
                return String.format(mu_1.rM().getString("spellsIconsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aoi)) {
            try {
                return String.format(mu_1.rM().getString("spellsIllustrationsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aoj)) {
            return "spell";
        }
        if (string.equals(arV)) {
            return this.iR();
        }
        if (string.equals(Oj)) {
            object = jk_1.mf().mg();
            int n2 = ((je_2)object).mZ().get(this.getId());
            return this.getValue() + n2;
        }
        if (string.equals(arY)) {
            if (this.iY() != this.iZ()) {
                return this.iY() + "-" + this.iZ();
            }
            return this.iZ();
        }
        if (string.equals(arW) && this.iK() != null) {
            object = ((xj_0)this.iK().get(0)).alM().fj();
            if (object == zg_1.cdF || object == zg_1.cdv) {
                return "aoeIconPOINT";
            }
            if (object == zg_1.cdw || object == zg_1.cdx || object == zg_1.cdz) {
                return "aoeIconCROSS";
            }
            if (object == zg_1.cdy || object == zg_1.cdD) {
                return "aoeIconLINE";
            }
            return "aoeIcon" + object;
        }
        if (string.equals(cax) && this.iK() != null) {
            object = ((xj_0)this.iK().get(0)).alM();
            zg_1 zg_12 = ((agf_2)object).fj();
            switch (zg_12) {
                case cdw: {
                    return ((nw_0)object).getRadius();
                }
                case cdx: {
                    return ((qv)object).vD();
                }
                case cdz: {
                    return ((cX)object).fk();
                }
                case cdD: {
                    return ((arG)object).yr() + 1;
                }
            }
            return 1;
        }
        if (string.equals(arZ)) {
            object = this.iK();
            if (this.iK() != null) {
                int[] nArray;
                xj_0 xj_02 = (xj_0)((acy)object).get(0);
                if (xj_02.di(1L)) {
                    xj_02 = (xj_0)((acy)object).get(1);
                }
                int n3 = 0;
                if (xj_02 != null && (nArray = xj_02.aln()).length > 0) {
                    n3 = nArray[0];
                    if (nArray[1] == 1 && nArray[0] != 0) {
                        --n3;
                    }
                }
                return n3;
            }
        }
        if (string.equals(arX)) {
            object = "cast.targetFree";
            for (xj_0 xj_03 : this.iK()) {
                long[] lArray;
                if (this.iZ() == 0) {
                    object = "cast.targetCaster";
                    continue;
                }
                for (long l2 : lArray = ((aLc)xj_03.alI()).aWk()) {
                    if ((4L & l2) != 0L) {
                        object = "cast.targetAlly";
                        continue;
                    }
                    if ((8L & l2) != 0L) {
                        object = "cast.targetEnemy";
                        continue;
                    }
                    if ((0x20L & l2) == 0L) continue;
                    object = "cast.targetSummon";
                }
            }
            if (this.iX()) {
                object = "cast.targetFreeCell";
            }
            return aon_0.aYc().getString((String)object);
        }
        if (string.equals(cay)) {
            byte by = this.et();
            String string2 = by > 0 ? "(" + by + ")" : "";
            return this.ja() < 63 ? this.ja() + string2 : "-";
        }
        return null;
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

    public int compareTo(Object object) {
        if (object instanceof yp_2) {
            return this.getName().compareTo(((yp_2)object).getName());
        }
        throw new RuntimeException("attempting to compare a " + object.getClass().getName() + " to a " + this.getClass().getName());
    }

    public int Bp() {
        return 4;
    }
}

