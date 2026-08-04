/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.util.ArrayList;

/*
 * Renamed from aBA
 */
public class aba_2
extends rk_2 {
    public static final int drP = 16;
    public static final float drQ = 0.0625f;
    public static final double drR = 86.0;
    public static final double drS = 43.0;
    public static final double drT = 10.0;
    protected double drU = 86.0;
    protected double drV = 43.0;
    protected double drW = 10.0;
    protected final ArrayList drX;
    protected final ArrayList csb;
    protected final qa_2 drY;
    protected final ArrayList drZ;
    protected ari_0 dsa;

    public aba_2() {
        this.vm();
        this.drX = new ArrayList(2048);
        this.drY = new qa_2(2048);
        this.csb = new ArrayList(1024);
        this.drZ = new ArrayList(3072);
    }

    public void b(Entity entity, boolean bl2) {
        if (bl2) {
            this.drX.add(entity);
        } else {
            this.csb.add(entity);
        }
    }

    public final void c(Entity entity, boolean bl2) {
        if (bl2) {
            this.drX.remove(entity);
        } else {
            this.csb.remove(entity);
        }
    }

    public double aNy() {
        return this.drU;
    }

    public void L(double d) {
        this.drU = d;
    }

    public double aNz() {
        return this.drV;
    }

    public void M(double d) {
        this.drV = d;
    }

    public double aNA() {
        return this.drW;
    }

    public void N(double d) {
        this.drW = d;
    }

    public final ari_0 aNB() {
        return this.dsa;
    }

    public yg_1 vC() {
        return (yg_1)this.dsa;
    }

    public Du aNC() {
        return this.vC().Fx();
    }

    public void d(Du du) {
        this.vC().c(du);
    }

    public void vs() {
        this.vC().Fr();
    }

    public double Ft() {
        if (this.dsa != null) {
            return this.vC().Ft();
        }
        return 1.0;
    }

    public void k(double d) {
        if (this.dsa != null) {
            this.vC().k(d);
        }
    }

    protected void vm() {
        this.dsa = new yg_1(this);
    }

    public double i(double d, double d2) {
        return (d - d2) * (this.drU / 2.0);
    }

    public double j(double d, double d2) {
        return -(d + d2) * (this.drV / 2.0);
    }

    public double i(double d, double d2, double d3) {
        return this.j(d, d2) + d3 * this.drW;
    }

    public double k(double d, double d2) {
        return d / this.drU - d2 / this.drV;
    }

    public double j(double d, double d2, double d3) {
        return d / this.drU - (d2 - d3 * this.drW) / this.drV;
    }

    public double l(double d, double d2) {
        return -(d / this.drU + d2 / this.drV);
    }

    public double k(double d, double d2, double d3) {
        return -(d / this.drU + (d2 - d3 * this.drW) / this.drV);
    }

    public long b(int n2, int n3, float f, float f2) {
        return aba_2.a(n2, n3, f + f2);
    }

    public static long a(long l2, long l3, float f) {
        l3 += 131071L;
        assert ((l2 += 131071L) < 262144L);
        assert (l3 < 262144L);
        return (l3 & 0x3FFFFL) << 32 | (l2 & 0x3FFFFL) << 14 | (long)aba_2.bC(f);
    }

    private static int bC(float f) {
        int n2 = (int)(f * 16.0f) + 8191;
        assert (n2 < 16384);
        return n2 & 0x3FFF;
    }

    public final boolean a(agf_0 agf_02) {
        float f = 1.0f / this.dsa.aEK();
        float f2 = this.bIz * 0.5f * f;
        float f3 = this.bIA * 0.5f * f;
        return !((float)agf_02.aST() < -f3 || (float)agf_02.aSS() > f3 || (float)agf_02.aSR() < -f2 || (float)agf_02.aSQ() > f2);
    }

    public ArrayList aND() {
        return this.drX;
    }

    public boolean a(xw_0 xw_02, Entity entity, int n2, int n3, float f, float f2) {
        entity.dPx = this.b(n2, n3, f, f2);
        return true;
    }
}

