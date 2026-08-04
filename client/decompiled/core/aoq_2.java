/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aOq
 */
public abstract class aoq_2 {
    protected final ArrayList gl = new ArrayList();
    private int cgp;
    private int cgq;
    private int elN = 0;

    protected aoq_2() {
        this.aYe();
    }

    public final void cu(int n2, int n3) {
        this.cgp = n2;
        this.cgq = n3;
    }

    public final void aYe() {
        this.cu(1000, 1000);
    }

    public void pJ(int n2) {
        this.elN = n2;
    }

    private void aYf() {
        this.gl.clear();
        mk_1 mk_12 = this.TL();
        this.gl.add(mk_12.YP());
        ry_2[] ry_2Array = mk_12.YQ();
        for (int j = 0; j < ry_2Array.length; ++j) {
            if (ry_2Array[j] == null || !ry_2Array[j].aeC() || !ry_2Array[j].aeD()) continue;
            this.gl.add(ry_2Array[j]);
        }
    }

    protected abstract mk_1 TL();

    protected abstract void TN();

    protected abstract void TM();

    public final void fq(boolean bl2) {
        if (bl2) {
            this.a(this.aYg(), this.elN);
        }
        this.aYf();
        this.b(this.cgp, this.cgq, this.gl);
    }

    private NE aYg() {
        return new aEm(this);
    }

    private void a(NE nE, int n2) {
        yb_2 yb_22 = yb_2.amk();
        yb_22.a(nE);
        if (n2 > 0) {
            long l2 = System.currentTimeMillis();
            yb_22.a(new aEo(this, l2, n2, yb_22));
        }
    }

    private void b(int n2, int n3, ArrayList arrayList) {
        yb_2 yb_22 = yb_2.amk();
        yb_22.jc(n2);
        yb_22.jb(n3);
        yb_22.a(new aEq(this, arrayList, yb_22));
        yb_22.a(new aEs(this, yb_22, arrayList));
    }
}

