/*
 * Decompiled with CFR 0.152.
 */
import java.util.BitSet;

/*
 * Renamed from er
 */
public class er_1
extends yl_1
implements zj_2 {
    private int it;
    private String oz;
    private String oA;
    private gq_2 oB;

    private er_1() {
    }

    public er_1(int n2, agf_2 agf_22, BitSet bitSet, BitSet bitSet2, int n3, int n4, int n5, float[] fArray, int n6, String string, String string2, boolean bl2) {
        super(n2, agf_22, bitSet, bitSet2, n3, n5, fArray, n6, bl2);
        this.it = n4;
        this.oA = string;
        this.oz = string2;
    }

    protected ack_1 hr() {
        return new er_1();
    }

    public er_1 a(akh_0 akh_02) {
        er_1 er_12 = (er_1)super.a(akh_02);
        er_12.a(this.cjy);
        er_12.cjv = this.cjv;
        er_12.cjw = this.cjw;
        er_12.it = this.it;
        er_12.rd = this.rd;
        er_12.cjx = this.cjx;
        er_12.ahI = this.ahI;
        er_12.cjB = this.cjB;
        er_12.ahJ = this.ahJ;
        er_12.aDQ = this.aDQ;
        er_12.oA = this.oA;
        er_12.oz = this.oz;
        er_12.nD = akh_02.getId();
        er_12.m(akh_02.getX(), akh_02.getY(), akh_02.wk());
        er_12.bdv = akh_02.Np();
        er_12.cjz = akh_02.Nq();
        return er_12;
    }

    public int eA() {
        return this.it;
    }

    public void a(aOf aOf2) {
        super.a(aOf2);
        this.bdv.gX().g(this);
    }

    public void b(aOf aOf2) {
        super.b(aOf2);
    }

    public void hs() {
        super.Qc();
        vt_0.aiU().w(this.baN);
    }

    public void d(int n2, int n3, short s) {
    }

    public boolean ht() {
        return this.oz != null && !this.oz.equals("");
    }

    public String hu() {
        return this.oz;
    }

    public boolean hv() {
        return this.oA != null && !this.oA.equals("");
    }

    public String hw() {
        return this.oA;
    }

    public void a(gq_2 gq_22) {
        this.oB = gq_22;
        try {
            this.oB.b(this.L());
            this.setAnimation("AnimStatique");
        }
        catch (Exception exception) {
            a.error((Object)exception);
        }
    }

    public void setAnimation(String string) {
        this.oB.aY(string);
    }
}

