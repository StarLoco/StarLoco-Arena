/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from TR
 */
public abstract class tr_1
implements abr_1,
aii_0,
ea_0 {
    private anw_2 bON;
    private kc_2 bOO;
    private aav_2 bOP;
    private List bOQ = new ArrayList(1);
    private long bOR = -1L;

    public tr_1(kc_2 kc_22) {
        this.bOO = kc_22;
        this.bOQ.add(kc_22);
    }

    public abr_1 gW() {
        return this;
    }

    public aii_0 gT() {
        return this;
    }

    public aav_2 gR() {
        return this.bOP;
    }

    public void a(aav_2 aav_22) {
        this.bOP = aav_22;
    }

    public void a(anw_2 anw_22) {
        this.bON = anw_22;
    }

    public anw_2 gS() {
        return this.bON;
    }

    public cn_0 gU() {
        return null;
    }

    public he_1 gX() {
        return null;
    }

    public kc_2 cL(long l2) {
        if (l2 == this.bOO.getId()) {
            return this.bOO;
        }
        return null;
    }

    public long al(byte by) {
        return this.bOR++;
    }

    public Iterator agn() {
        return this.bOQ.iterator();
    }

    public List v(ry ry2) {
        return this.D(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public List D(int n2, int n3, int n4) {
        ArrayList<kc_2> arrayList = new ArrayList<kc_2>();
        Iterator iterator = this.agn();
        while (iterator.hasNext()) {
            kc_2 kc_22 = (kc_2)iterator.next();
            if (amd.a(kc_22, n2, n3) != 0) continue;
            arrayList.add(kc_22);
        }
        return arrayList;
    }

    public byte getType() {
        return 0;
    }
}

