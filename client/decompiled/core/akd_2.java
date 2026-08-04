/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from aKd
 */
public class akd_2
implements Iterator {
    private EV dTl;
    private EV cmC;
    private jg_0 dTm = new jg_0();
    private int bmF = -1;

    public akd_2(EV eV) {
        this.dTl = eV;
        this.cmC = null;
    }

    public boolean hasNext() {
        return this.dTl != null;
    }

    public EV aVy() {
        EV eV = this.dTl;
        this.dTl = null;
        if (eV.hasChildren() && eV.OL()) {
            ++this.bmF;
            this.cmC = eV;
            this.dTm.add(0);
            this.dTl = (EV)eV.getChildren().get(0);
        } else if (this.cmC != null) {
            while (this.cmC != null) {
                ArrayList arrayList = this.cmC.getChildren();
                int n2 = this.dTm.get(this.bmF) + 1;
                if (n2 == arrayList.size()) {
                    this.cmC = this.cmC.OK();
                    this.dTm.bv(this.bmF);
                    --this.bmF;
                    continue;
                }
                this.dTl = (EV)arrayList.get(n2);
                this.dTm.set(this.bmF, n2);
                break;
            }
        }
        return eV;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] stringArray) {
        ads ads2 = new ads("n1");
        ads ads3 = new ads("n1A");
        ads3.a(new ads("n1Aa"));
        ads3.a(new ads("n1Ab"));
        ads3.bo(true);
        ads2.a(ads3);
        ads ads4 = new ads("n1B");
        ads ads5 = new ads("n1Ba");
        ads ads6 = new ads("n1Bb");
        ads4.a(ads5);
        ads4.a(ads6);
        ads4.bo(true);
        ads2.a(ads4);
        ads ads7 = new ads("n1C");
        ads2.a(ads7);
        ads2.bo(true);
        akd_2 akd_22 = new akd_2(ads2);
        while (akd_22.hasNext()) {
            EV eV = akd_22.aVy();
            for (int j = 0; j < eV.getDepth(); ++j) {
                System.out.print("\t");
            }
            System.out.println((String)eV.getValue());
        }
    }
}

