/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from IZ
 */
public class iz_1
implements aho_0 {
    public static final byte biL = 1;
    public static final byte biM = 2;
    public static final byte biN = 3;
    public static final byte biO = 4;
    public static final byte biP = 5;
    public static final byte biQ = 6;
    public static final byte biR = 7;
    public static final byte biS = 8;
    public static final String biT = "infosList";
    public static final String atB = "tournamentsList";
    public static final String[] ce = new String[]{"infosList", "tournamentsList"};
    private final ArrayList biU = new ArrayList();
    private static final iz_1 biV = new iz_1();

    public iz_1() {
        azs_0.aLV().g("infosManager", this);
    }

    public static iz_1 Vg() {
        return biV;
    }

    public boolean bQ(long l2) {
        boolean bl2 = false;
        for (int j = this.biU.size() - 1; 0 <= j && !bl2; --j) {
            bl2 = ((aan_1)this.biU.get(j)).bQ(l2);
        }
        return bl2;
    }

    public void b(aan_1 aan_12) {
        if (!(aan_12 instanceof td_0) || !this.bQ(((td_0)aan_12).fx())) {
            this.biU.add(aan_12);
        }
    }

    public void c(aan_1 aan_12) {
        this.biU.remove(aan_12);
        azs_0.aLV().a((aho_0)iz_1.Vg(), ce);
    }

    public void bR(long l2) {
        aan_1 aan_12 = null;
        for (int j = 0; j < this.biU.size(); ++j) {
            aan_1 aan_13 = (aan_1)this.biU.get(j);
            if (!(aan_13 instanceof td_0) || ((td_0)aan_13).fx() != l2) continue;
            aan_12 = aan_13;
            break;
        }
        this.biU.remove(aan_12);
    }

    public ArrayList Vh() {
        return this.biU;
    }

    public void Vi() {
        this.biU.clear();
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(biT)) {
            return this.biU;
        }
        if (string.equals(atB)) {
            ArrayList<td_0> arrayList = new ArrayList<td_0>();
            for (int j = 0; j < this.biU.size(); ++j) {
                if (((aan_1)this.biU.get(j)).getType() != 1) continue;
                arrayList.add((td_0)this.biU.get(j));
            }
            return arrayList;
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

