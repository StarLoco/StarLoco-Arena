/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from agf
 */
public abstract class agf_2
implements eU {
    private gf_0 ctH;
    private short ctI = 0;

    public abstract void a(int[] var1);

    public Iterable a(int n2, int n3, short s, int n4, int n5, short s2, Iterator iterator) {
        return this.a(n2, n3, s, n4, n5, s2, qc_0.bEQ, iterator);
    }

    public Iterable a(int n2, int n3, short s, int n4, int n5, short s2, ye_0 ye_02, Iterator iterator) {
        ArrayList<aOf> arrayList = new ArrayList<aOf>();
        while (iterator.hasNext()) {
            aOf aOf2 = (aOf)iterator.next();
            if (!this.a(n2, n3, s, n4, n5, s2, ye_02, aOf2.gn(), aOf2.go(), aOf2.gp())) continue;
            arrayList.add(aOf2);
        }
        return arrayList;
    }

    public short awe() {
        return this.ctI;
    }

    public void bG(short s) {
        this.ctI = s;
    }

    public boolean a(int n2, int n3, short s, int n4, int n5, short s2, ye_0 ye_02, int n6, int n7, short s3) {
        return this.awf().a(n2, n3, s, n4, n5, s2, ye_02, n6, n7, s3);
    }

    public boolean b(int n2, int n3, short s, int n4, int n5, short s2, ye_0 ye_02, int n6, int n7, short s3, byte by) {
        return this.awf().a(n2, n3, s, n4, n5, s2, ye_02, n6, n7, s3, by);
    }

    public boolean a(int n2, int n3, short s, int n4, int n5, short s2, int n6, int n7, short s3) {
        return this.a(n2, n3, s, n4, n5, s2, qc_0.bEQ, n6, n7, s3);
    }

    protected gf_0 awf() {
        if (this.ctH == null) {
            this.ctH = new gf_0(this.fg(), this.fi());
        }
        return this.ctH;
    }

    public Iterable b(int n2, int n3, short s, int n4, int n5, short s2, ye_0 ye_02) {
        return this.awf().a(n2, n3, s, n4, n5, s2, ye_02);
    }

    public Iterable a(int n2, int n3, short s, int n4, int n5, short s2) {
        return this.b(n2, n3, s, n4, n5, s2, qc_0.bEQ);
    }

    protected abstract boolean fi();

    public abstract zg_1 fj();

    public abstract List fg();

    public abstract String fh();

    public abstract ArrayList fm();

    public abstract xq_2 fn();
}

